package com.jobhorizon.backend.catalogo;

import com.jobhorizon.backend.departamento.Departamento;
import com.jobhorizon.backend.departamento.DepartamentoRepository;
import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.distrito.DistritoRepository;
import com.jobhorizon.backend.genero.Genero;
import com.jobhorizon.backend.genero.GeneroRepository;
import com.jobhorizon.backend.habilidad.CategoriaHabilidad;
import com.jobhorizon.backend.habilidad.CategoriaHabilidadRepository;
import com.jobhorizon.backend.habilidad.Habilidad;
import com.jobhorizon.backend.habilidad.HabilidadRepository;
import com.jobhorizon.backend.habilidad.dto.HabilidadResponse;
import com.jobhorizon.backend.idioma.Idioma;
import com.jobhorizon.backend.idioma.IdiomaRepository;
import com.jobhorizon.backend.niveleducativo.NivelEducativo;
import com.jobhorizon.backend.niveleducativo.NivelEducativoRepository;
import com.jobhorizon.backend.nivelhabilidad.NivelHabilidad;
import com.jobhorizon.backend.nivelhabilidad.NivelHabilidadRepository;
import com.jobhorizon.backend.nivelidioma.NivelIdioma;
import com.jobhorizon.backend.nivelidioma.NivelIdiomaRepository;
import com.jobhorizon.backend.pais.Pais;
import com.jobhorizon.backend.pais.PaisRepository;
import com.jobhorizon.backend.tipocertificacion.TipoCertificacion;
import com.jobhorizon.backend.tipocertificacion.TipoCertificacionRepository;
import com.jobhorizon.backend.tipodocumento.TipoDocumento;
import com.jobhorizon.backend.tipodocumento.TipoDocumentoRepository;
import com.jobhorizon.backend.tipoparticipacion.TipoParticipacion;
import com.jobhorizon.backend.tipoparticipacion.TipoParticipacionRepository;
import com.jobhorizon.backend.tiporecomendacion.TipoRecomendacion;
import com.jobhorizon.backend.tiporecomendacion.TipoRecomendacionRepository;
import com.jobhorizon.backend.tiporedsocial.TipoRedSocial;
import com.jobhorizon.backend.tiporedsocial.TipoRedSocialRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller de acceso público que expone los catálogos del sistema.
 *
 * <p>Estos endpoints son utilizados para poblar selectores y formularios en el frontend,
 * como listas de géneros, departamentos, habilidades, idiomas, etc.</p>
 */
@RestController
@RequestMapping("/catalogos")
@RequiredArgsConstructor
@Tag(name = "Catálogos", description = "Endpoints públicos para obtener las listas de valores del sistema (géneros, departamentos, habilidades, idiomas, etc.). No requieren autenticación.")
@SecurityRequirements
public class CatalogoController {

    private final GeneroRepository generoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final DistritoRepository distritoRepository;
    private final NivelEducativoRepository nivelEducativoRepository;
    private final CategoriaHabilidadRepository categoriaHabilidadRepository;
    private final HabilidadRepository habilidadRepository;
    private final NivelHabilidadRepository nivelHabilidadRepository;
    private final IdiomaRepository idiomaRepository;
    private final NivelIdiomaRepository nivelIdiomaRepository;
    private final TipoCertificacionRepository tipoCertificacionRepository;
    private final TipoRecomendacionRepository tipoRecomendacionRepository;
    private final PaisRepository paisRepository;
    private final TipoParticipacionRepository tipoParticipacionRepository;
    private final TipoRedSocialRepository tipoRedSocialRepository;

    @Operation(summary = "Listar géneros", description = "Retorna todos los géneros disponibles (ej. Masculino, Femenino). Usar el `id` al registrar o actualizar datos personales.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de géneros obtenida con éxito.")
    })
    @GetMapping("/generos")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<Genero>>> listarGeneros() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Géneros obtenidos con éxito", generoRepository.findAll()));
    }

    @Operation(summary = "Listar tipos de documento", description = "Retorna todos los tipos de documento de identidad disponibles (ej. DNI, CE, Pasaporte). Usar el `id` al registrar o actualizar datos personales.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tipos de documento obtenida con éxito.")
    })
    @GetMapping("/tipos-documento")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<TipoDocumento>>> listarTiposDocumento() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Tipos de documento obtenidos con éxito", tipoDocumentoRepository.findAll()));
    }

    @Operation(summary = "Listar departamentos", description = "Retorna todos los departamentos del país. Para obtener los distritos de un departamento, usar el endpoint `/catalogos/departamentos/{id}/distritos`.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de departamentos obtenida con éxito.")
    })
    @GetMapping("/departamentos")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<Departamento>>> listarDepartamentos() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Departamentos obtenidos con éxito", departamentoRepository.findAll()));
    }

    @Operation(summary = "Listar distritos por departamento", description = "Retorna todos los distritos que pertenecen a un departamento específico. Usar el `id` del distrito al registrar o actualizar datos personales.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de distritos obtenida con éxito."),
            @ApiResponse(responseCode = "400", description = "El ID del departamento no es un número válido.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/departamentos/{id}/distritos")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<Distrito>>> listarDistritosPorDepartamento(
            @Parameter(description = "ID del departamento del que se desean obtener los distritos", example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Distritos obtenidos con éxito", distritoRepository.findByIdDepartamento(id)));
    }

    @Operation(summary = "Listar niveles educativos", description = "Retorna todos los niveles educativos disponibles (ej. Secundaria, Técnico, Universitario). Usar el `id` al registrar formación académica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de niveles educativos obtenida con éxito.")
    })
    @GetMapping("/niveles-educativos")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<NivelEducativo>>> listarNivelesEducativos() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Niveles educativos obtenidos con éxito", nivelEducativoRepository.findAll()));
    }

    @Operation(summary = "Listar categorías de habilidad", description = "Retorna todas las categorías de habilidad (ej. Tecnología, Idiomas, Diseño). Para obtener las habilidades de una categoría, usar el endpoint `/catalogos/categorias-habilidad/{id}/habilidades`.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorías de habilidad obtenida con éxito.")
    })
    @GetMapping("/categorias-habilidad")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<CategoriaHabilidad>>> listarCategoriasHabilidad() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Categorías de habilidad obtenidas con éxito", categoriaHabilidadRepository.findAll()));
    }

    @Operation(summary = "Listar habilidades por categoría", description = "Retorna todas las habilidades que pertenecen a una categoría específica. Usar el `id` de la habilidad al agregar habilidades al perfil del postulante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de habilidades obtenida con éxito."),
            @ApiResponse(responseCode = "400", description = "El ID de la categoría no es un número válido.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/categorias-habilidad/{id}/habilidades")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<HabilidadResponse>>> listarHabilidadesPorCategoria(
            @Parameter(description = "ID de la categoría de habilidad", example = "1")
            @PathVariable Integer id) {
        List<HabilidadResponse> habilidades = habilidadRepository.findByCategoriaHabilidadId(id).stream()
                .map(h -> new HabilidadResponse(h.getId(), h.getNombre(), h.getDescripcion()))
                .toList();
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Habilidades obtenidas con éxito", habilidades));
    }

    @Operation(summary = "Listar niveles de habilidad", description = "Retorna todos los niveles de dominio de habilidad disponibles (ej. Básico, Intermedio, Avanzado). Usar el `id` al agregar habilidades al perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de niveles de habilidad obtenida con éxito.")
    })
    @GetMapping("/niveles-habilidad")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<NivelHabilidad>>> listarNivelesHabilidad() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Niveles de habilidad obtenidos con éxito", nivelHabilidadRepository.findAll()));
    }

    @Operation(summary = "Listar idiomas", description = "Retorna todos los idiomas disponibles (ej. Español, Inglés, Francés). Usar el `id` al agregar idiomas al perfil del postulante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de idiomas obtenida con éxito.")
    })
    @GetMapping("/idiomas")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<Idioma>>> listarIdiomas() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Idiomas obtenidos con éxito", idiomaRepository.findAll()));
    }

    @Operation(summary = "Listar niveles de idioma", description = "Retorna todos los niveles de dominio de idioma (ej. Básico, Intermedio, Avanzado). Estos niveles aplican a lectura, escritura, conversación y escucha.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de niveles de idioma obtenida con éxito.")
    })
    @GetMapping("/niveles-idioma")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<NivelIdioma>>> listarNivelesIdioma() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Niveles de idioma obtenidos con éxito", nivelIdiomaRepository.findAll()));
    }

    @Operation(summary = "Listar tipos de certificación", description = "Retorna todos los tipos de certificación disponibles (ej. Técnica, Profesional, Académica). Usar el `id` al registrar certificaciones en el perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tipos de certificación obtenida con éxito.")
    })
    @GetMapping("/tipos-certificacion")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<TipoCertificacion>>> listarTiposCertificacion() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Tipos de certificación obtenidos con éxito", tipoCertificacionRepository.findAll()));
    }

    @Operation(summary = "Listar tipos de recomendación", description = "Retorna todos los tipos de recomendación disponibles (ej. Laboral, Académica, Personal). Usar el `id` al registrar recomendaciones en el perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tipos de recomendación obtenida con éxito.")
    })
    @GetMapping("/tipos-recomendacion")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<TipoRecomendacion>>> listarTiposRecomendacion() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Tipos de recomendación obtenidos con éxito", tipoRecomendacionRepository.findAll()));
    }

    @Operation(summary = "Listar países", description = "Retorna todos los países disponibles. Usar el `id` al registrar eventos en el perfil del postulante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de países obtenida con éxito.")
    })
    @GetMapping("/paises")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<Pais>>> listarPaises() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Países obtenidos con éxito", paisRepository.findAll()));
    }

    @Operation(summary = "Listar tipos de participación", description = "Retorna todos los tipos de participación en eventos (ej. Expositor, Asistente, Organizador). Usar el `id` al registrar eventos en el perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tipos de participación obtenida con éxito.")
    })
    @GetMapping("/tipos-participacion")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<TipoParticipacion>>> listarTiposParticipacion() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Tipos de participación obtenidos con éxito", tipoParticipacionRepository.findAll()));
    }

    @Operation(summary = "Listar tipos de red social", description = "Retorna todos los tipos de red social disponibles (ej. LinkedIn, GitHub, Twitter). Usar el `id` al agregar redes sociales al perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tipos de red social obtenida con éxito.")
    })
    @GetMapping("/tipos-red-social")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<TipoRedSocial>>> listarTiposRedSocial() {
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Tipos de red social obtenidos con éxito", tipoRedSocialRepository.findAll()));
    }
}
