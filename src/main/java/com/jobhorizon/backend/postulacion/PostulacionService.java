package com.jobhorizon.backend.postulacion;

import com.jobhorizon.backend.correo.CorreoService;
import com.jobhorizon.backend.ofertatrabajo.EstadoAplicacion;
import com.jobhorizon.backend.ofertatrabajo.EstadoAplicacionRepository;
import com.jobhorizon.backend.ofertatrabajo.OfertaTrabajo;
import com.jobhorizon.backend.ofertatrabajo.OfertaTrabajoRepository;
import com.jobhorizon.backend.ofertatrabajo.PostulanteOferta;
import com.jobhorizon.backend.ofertatrabajo.PostulanteOfertaId;
import com.jobhorizon.backend.ofertatrabajo.PostulanteOfertaRepository;
import com.jobhorizon.backend.postulante.Postulante;
import com.jobhorizon.backend.postulante.PostulanteRepository;
import com.jobhorizon.backend.postulante.telefono.PostulanteTelefono;
import com.jobhorizon.backend.postulante.telefono.PostulanteTelefonoRepository;
import com.jobhorizon.backend.postulacion.dto.PostulacionPostulanteResponse;
import com.jobhorizon.backend.postulacion.dto.PostulacionEmpresaResponse;
import com.jobhorizon.backend.seguridad.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostulacionService {

    private final PostulanteOfertaRepository postulanteOfertaRepository;
    private final OfertaTrabajoRepository ofertaTrabajoRepository;
    private final PostulanteRepository postulanteRepository;
    private final EstadoAplicacionRepository estadoAplicacionRepository;
    private final PostulanteTelefonoRepository telefonoRepository;
    private final CorreoService correoService;

    /**
     * Permite a un postulante aplicar a una oferta de trabajo.
     * Valida que la oferta exista, esté activa y que no haya aplicado previamente.
     */
    @Transactional
    public void aplicarOferta(Integer idPostulante, Integer idOferta) {
        Postulante postulante = postulanteRepository.findById(idPostulante)
                .orElseThrow(() -> new RecursoNoEncontradoException("Postulante no encontrado"));

        OfertaTrabajo oferta = ofertaTrabajoRepository.findById(idOferta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Oferta de trabajo no encontrada"));

        // Validar que la oferta esté activa
        if (oferta.getEstadoOferta() == null || !oferta.getEstadoOferta().getNombre().equalsIgnoreCase("ACTIVA")) {
            throw new IllegalArgumentException("No se puede aplicar a una oferta de trabajo que no esté ACTIVA");
        }

        // Validar si ya se ha postulado
        PostulanteOfertaId id = new PostulanteOfertaId(idPostulante, idOferta);
        if (postulanteOfertaRepository.existsById(id)) {
            throw new IllegalArgumentException("Ya te has postulado a esta oferta de trabajo");
        }

        // Obtener estado PENDIENTE por defecto
        EstadoAplicacion estadoPendiente = estadoAplicacionRepository.findByNombre("PENDIENTE")
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de aplicación PENDIENTE no configurado"));

        PostulanteOferta postulacion = PostulanteOferta.builder()
                .id(id)
                .postulante(postulante)
                .oferta(oferta)
                .fechaAplicacion(LocalDateTime.now())
                .estadoAplicacion(estadoPendiente)
                .build();

        postulanteOfertaRepository.save(postulacion);

        // Enviar correo de confirmación de manera asíncrona / segura
        correoService.enviarCorreoConfirmacionPostulacion(
                postulante.getUsuario().getCorreo(),
                oferta.getTitulo(),
                oferta.getEmpresa().getNombreComercial()
        );
    }

    /**
     * Recupera el historial de postulaciones de un candidato de forma paginada.
     */
    public Page<PostulacionPostulanteResponse> listarHistorialPostulante(Integer idPostulante, Pageable pageable) {
        if (!postulanteRepository.existsById(idPostulante)) {
            throw new RecursoNoEncontradoException("Postulante no encontrado");
        }

        Page<PostulanteOferta> postulaciones = postulanteOfertaRepository.findByPostulanteId(idPostulante, pageable);
        return postulaciones.map(po -> new PostulacionPostulanteResponse(
                po.getOferta().getId(),
                po.getOferta().getTitulo(),
                po.getOferta().getEmpresa().getNombreComercial(),
                po.getFechaAplicacion(),
                po.getEstadoAplicacion().getId(),
                po.getEstadoAplicacion().getNombre()
        ));
    }

    /**
     * Lista los aspirantes a una oferta de trabajo. Valida que la empresa sea la dueña de la oferta.
     */
    public Page<PostulacionEmpresaResponse> listarAspirantesPorOferta(Integer idEmpresa, Integer idOferta, Pageable pageable) {
        OfertaTrabajo oferta = ofertaTrabajoRepository.findById(idOferta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Oferta de trabajo no encontrada"));

        // Validar propiedad de la oferta
        if (!oferta.getEmpresa().getId().equals(idEmpresa)) {
            throw new AccessDeniedException("No tiene permisos para ver los aspirantes de esta oferta");
        }

        Page<PostulanteOferta> aspirantes = postulanteOfertaRepository.findByOfertaId(idOferta, pageable);
        return aspirantes.map(po -> {
            String telefonoPrincipal = telefonoRepository.findByIdUsuario(po.getPostulante().getId()).stream()
                    .findFirst()
                    .map(PostulanteTelefono::getTelefono)
                    .orElse(null);

            String nombreCompleto = po.getPostulante().getNombres() + " " + po.getPostulante().getApellidos();

            return new PostulacionEmpresaResponse(
                    po.getPostulante().getId(),
                    nombreCompleto,
                    po.getPostulante().getUsuario().getCorreo(),
                    telefonoPrincipal,
                    po.getFechaAplicacion(),
                    po.getEstadoAplicacion().getId(),
                    po.getEstadoAplicacion().getNombre()
            );
        });
    }

    /**
     * Permite a la empresa actualizar el estado de una postulación (ej: PENDIENTE -> CONTACTADO).
     * Valida la propiedad de la oferta y notifica al postulante por correo.
     */
    @Transactional
    public void actualizarEstadoAplicacion(Integer idEmpresa, Integer idOferta, Integer idPostulante, Integer idEstadoNuevo) {
        OfertaTrabajo oferta = ofertaTrabajoRepository.findById(idOferta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Oferta de trabajo no encontrada"));

        // Validar propiedad de la oferta
        if (!oferta.getEmpresa().getId().equals(idEmpresa)) {
            throw new AccessDeniedException("No tiene permisos para modificar postulaciones de esta oferta");
        }

        PostulanteOfertaId id = new PostulanteOfertaId(idPostulante, idOferta);
        PostulanteOferta postulacion = postulanteOfertaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Postulación no encontrada"));

        EstadoAplicacion nuevoEstado = estadoAplicacionRepository.findById(idEstadoNuevo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de aplicación no encontrado"));

        postulacion.setEstadoAplicacion(nuevoEstado);
        postulanteOfertaRepository.save(postulacion);

        // Enviar correo de notificación
        correoService.enviarCorreoCambioEstadoPostulacion(
                postulacion.getPostulante().getUsuario().getCorreo(),
                oferta.getTitulo(),
                oferta.getEmpresa().getNombreComercial(),
                nuevoEstado.getNombre()
        );
    }
}
