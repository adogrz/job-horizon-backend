package com.jobhorizon.backend.ofertatrabajo.matching;

import com.jobhorizon.backend.ofertatrabajo.OfertaTrabajoRepository;
import com.jobhorizon.backend.seguridad.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * Servicio para el motor de búsqueda y matching de aspirantes.
 *
 * <p>Orquesta la ejecución del stored procedure {@code sp_ObtenerAspirantes} y
 * aplica sobre su resultado los filtros dinámicos y la paginación en memoria.</p>
 *
 * <h2>Flujo de trabajo</h2>
 * <ol>
 *   <li>Valida que la oferta exista y pertenezca a la empresa autenticada.</li>
 *   <li>Delega la ejecución del SP al repositorio; el filtro de departamento va
 *       directo al SP como parámetro SQL.</li>
 *   <li>Aplica filtros adicionales en Java (nombre, puntaje mínimo).</li>
 *   <li>Aplica el ordenamiento solicitado.</li>
 *   <li>Pagina la lista resultante y devuelve un {@link Page}.</li>
 * </ol>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingService {

    /** Tamaño máximo de página para evitar cargas excesivas en memoria. */
    private static final int MAX_PAGE_SIZE = 100;

    private final MatchingRepository matchingRepository;
    private final OfertaTrabajoRepository ofertaTrabajoRepository;

    /**
     * Busca aspirantes que hacen match con una oferta de trabajo.
     *
     * @param idOferta       ID de la oferta
     * @param idEmpresa      ID de la empresa autenticada (para validar propiedad)
     * @param idDepartamento filtro de departamento enviado al SP; {@code null} = todos
     * @param nombre         búsqueda parcial case-insensitive en nombre/apellido; {@code null} = todos
     * @param puntajeMin     puntaje mínimo de matching (0–100); {@code null} = sin mínimo
     * @param sortBy         campo por el que ordenar (ver {@link SortField})
     * @param sortDir        dirección de ordenamiento: {@code "asc"} o {@code "desc"}
     * @param page           número de página (0-indexed)
     * @param size           tamaño de página (máximo {@value MAX_PAGE_SIZE})
     * @return página de aspirantes con su puntaje de matching
     */
    public Page<AspiranteMatchResponse> buscarAspirantesParaOferta(
            Integer idOferta,
            Integer idEmpresa,
            Integer idDepartamento,
            String nombre,
            BigDecimal puntajeMin,
            String sortBy,
            String sortDir,
            int page,
            int size) {

        validarOfertaYPropiedad(idOferta, idEmpresa);

        int pageSize = Math.min(size, MAX_PAGE_SIZE);

        List<AspiranteMatchResponse> aspirantes =
                matchingRepository.obtenerAspirantesParaOferta(idOferta, idDepartamento);

        List<AspiranteMatchResponse> filtrados = aplicarFiltros(aspirantes, nombre, puntajeMin);
        List<AspiranteMatchResponse> ordenados = aplicarOrden(filtrados, sortBy, sortDir);

        log.debug("oferta={} total_sp={} tras_filtros={}", idOferta, aspirantes.size(), ordenados.size());

        return paginar(ordenados, page, pageSize);
    }

    // -------------------------------------------------------------------------
    // Validación
    // -------------------------------------------------------------------------

    private void validarOfertaYPropiedad(Integer idOferta, Integer idEmpresa) {
        var oferta = ofertaTrabajoRepository.findById(idOferta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Oferta de trabajo no encontrada"));

        if (!oferta.getEmpresa().getId().equals(idEmpresa)) {
            throw new AccessDeniedException(
                    "No tiene permisos para ver los aspirantes de esta oferta");
        }
    }

    // -------------------------------------------------------------------------
    // Filtros en Java
    // -------------------------------------------------------------------------

    private List<AspiranteMatchResponse> aplicarFiltros(
            List<AspiranteMatchResponse> aspirantes,
            String nombre,
            BigDecimal puntajeMin) {

        var stream = aspirantes.stream();

        if (nombre != null && !nombre.isBlank()) {
            String termino = nombre.strip().toLowerCase();
            stream = stream.filter(a ->
                    a.nombreCompleto().toLowerCase().contains(termino));
        }

        // Si no se define un puntaje mínimo, por defecto excluimos coincidencia exacta de 0.00 (requiere >= 0.01)
        BigDecimal min = puntajeMin != null ? puntajeMin : BigDecimal.valueOf(0.01);
        stream = stream.filter(a ->
                a.puntajeMatching().compareTo(min) >= 0);

        return stream.toList();
    }

    // -------------------------------------------------------------------------
    // Ordenamiento
    // -------------------------------------------------------------------------

    private List<AspiranteMatchResponse> aplicarOrden(
            List<AspiranteMatchResponse> aspirantes,
            String sortBy,
            String sortDir) {

        Comparator<AspiranteMatchResponse> comparator = resolverComparador(sortBy);

        // "desc" es la dirección de ordenamiento por defecto, por lo que revertimos el comparador base (ascendente)
        if (!"asc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        return aspirantes.stream()
                .sorted(comparator)
                .toList();
    }

    /**
     * Resuelve el {@link Comparator} base (en orden ascendente) correspondiente al campo de ordenamiento.
     *
     * <p>Campos soportados:
     * <ul>
     *   <li>{@code puntajeMatching} — puntaje de matching (default)</li>
     *   <li>{@code nombre}          — nombre completo alfabético</li>
     *   <li>{@code departamento}    — nombre del departamento</li>
     *   <li>{@code habilidades}     — habilidades coincidentes</li>
     * </ul>
     * </p>
     */
    private Comparator<AspiranteMatchResponse> resolverComparador(String sortBy) {
        if (sortBy == null) {
            return Comparator.comparing(AspiranteMatchResponse::puntajeMatching);
        }
        return switch (sortBy.toLowerCase()) {
            case "nombre"       -> Comparator.comparing(AspiranteMatchResponse::nombreCompleto, String.CASE_INSENSITIVE_ORDER);
            case "departamento" -> Comparator.comparing(AspiranteMatchResponse::departamento, String.CASE_INSENSITIVE_ORDER);
            case "habilidades"  -> Comparator.comparingInt(AspiranteMatchResponse::habilidadesCoinciden);
            default             -> Comparator.comparing(AspiranteMatchResponse::puntajeMatching);
        };
    }

    // -------------------------------------------------------------------------
    // Paginación en memoria
    // -------------------------------------------------------------------------

    private Page<AspiranteMatchResponse> paginar(
            List<AspiranteMatchResponse> lista,
            int page,
            int size) {

        int total = lista.size();
        int fromIndex = Math.min(page * size, total);
        int toIndex   = Math.min(fromIndex + size, total);

        List<AspiranteMatchResponse> subLista = lista.subList(fromIndex, toIndex);
        return new PageImpl<>(subLista, PageRequest.of(page, size), total);
    }
}
