package com.jobhorizon.backend.postulante;

import com.jobhorizon.backend.postulante.certificacion.dto.CertificacionRequest;
import com.jobhorizon.backend.postulante.certificacion.dto.CertificacionResponse;
import com.jobhorizon.backend.postulante.dto.ActualizarDatosPersonalesRequest;
import com.jobhorizon.backend.postulante.dto.DatosPersonalesResponse;
import com.jobhorizon.backend.postulante.dto.PostulantePerfilResponse;
import com.jobhorizon.backend.postulante.dto.FotoRequest;
import com.jobhorizon.backend.postulante.dto.CvRequest;
import com.jobhorizon.backend.postulante.evento.dto.EventoRequest;
import com.jobhorizon.backend.postulante.evento.dto.EventoResponse;
import com.jobhorizon.backend.postulante.examen.dto.ExamenRequest;
import com.jobhorizon.backend.postulante.examen.dto.ExamenResponse;
import com.jobhorizon.backend.postulante.experiencia.dto.ExperienciaLaboralRequest;
import com.jobhorizon.backend.postulante.experiencia.dto.ExperienciaLaboralResponse;
import com.jobhorizon.backend.postulante.formacion.dto.FormacionAcademicaRequest;
import com.jobhorizon.backend.postulante.formacion.dto.FormacionAcademicaResponse;
import com.jobhorizon.backend.postulante.habilidad.dto.PostulanteHabilidadRequest;
import com.jobhorizon.backend.postulante.habilidad.dto.PostulanteHabilidadResponse;
import com.jobhorizon.backend.postulante.idioma.dto.PostulanteIdiomaRequest;
import com.jobhorizon.backend.postulante.idioma.dto.PostulanteIdiomaResponse;
import com.jobhorizon.backend.postulante.logro.dto.LogroRequest;
import com.jobhorizon.backend.postulante.logro.dto.LogroResponse;
import com.jobhorizon.backend.postulante.publicacion.dto.PublicacionRequest;
import com.jobhorizon.backend.postulante.publicacion.dto.PublicacionResponse;
import com.jobhorizon.backend.postulante.recomendacion.dto.RecomendacionRequest;
import com.jobhorizon.backend.postulante.recomendacion.dto.RecomendacionResponse;
import com.jobhorizon.backend.postulante.redsocial.dto.RedSocialRequest;
import com.jobhorizon.backend.postulante.redsocial.dto.RedSocialResponse;
import com.jobhorizon.backend.postulante.telefono.dto.TelefonoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller para la gestión del perfil completo de un postulante.
 *
 * <p>Todos los endpoints requieren autenticación JWT con el privilegio {@code GESTIONAR_PERFIL}.
 * El postulante se identifica automáticamente a través del token JWT, no es necesario enviar el ID del usuario.</p>
 */
@RestController
@RequestMapping("/postulante/perfil")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTIONAR_PERFIL')")
@Tag(
        name = "Perfil del Postulante",
        description = """
                CRUD completo del perfil de un postulante. Requiere autenticación JWT con el privilegio **GESTIONAR_PERFIL**.
                
                El postulante autenticado se identifica automáticamente por su token JWT — no se necesita enviar ningún ID de usuario.
                
                El perfil está organizado en las siguientes secciones:
                - Datos Personales
                - Teléfonos
                - Redes Sociales
                - Experiencia Laboral
                - Formación Académica
                - Certificaciones
                - Logros
                - Recomendaciones
                - Eventos
                - Publicaciones
                - Exámenes
                - Habilidades
                - Idiomas
                """
)
public class PostulanteController {

    private final PostulanteService postulanteService;

    // =========================================================================
    // PERFIL COMPLETO
    // =========================================================================

    @Operation(
            summary = "Obtener perfil completo",
            description = "Retorna el perfil completo del postulante autenticado con todas sus secciones: datos personales, teléfonos, redes sociales, experiencias, formaciones, certificaciones, logros, recomendaciones, eventos, publicaciones, exámenes, habilidades e idiomas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil completo obtenido con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "No tiene el privilegio GESTIONAR_PERFIL.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<PostulantePerfilResponse>> obtenerPerfilCompleto(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        PostulantePerfilResponse perfil = postulanteService.obtenerPerfilCompleto(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Perfil completo obtenido con éxito", perfil));
    }

    // =========================================================================
    // DATOS PERSONALES
    // =========================================================================

    @Operation(summary = "Obtener datos personales", description = "Retorna únicamente la sección de datos personales del postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Datos personales obtenidos con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/datos-personales")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<DatosPersonalesResponse>> obtenerDatosPersonales(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        DatosPersonalesResponse datos = postulanteService.obtenerDatosPersonales(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Datos personales obtenidos con éxito", datos));
    }

    @Operation(summary = "Actualizar datos personales", description = "Actualiza los datos personales del postulante autenticado. Para los campos `idGenero`, `idTipoDocumento` e `idDistrito`, usar los IDs del catálogo correspondiente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Datos personales actualizados con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/datos-personales")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<DatosPersonalesResponse>> actualizarDatosPersonales(
            Principal principal,
            @Valid @RequestBody ActualizarDatosPersonalesRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        DatosPersonalesResponse datos = postulanteService.actualizarDatosPersonales(idUsuario, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Datos personales actualizados con éxito", datos));
    }

    @Operation(summary = "Actualizar foto de perfil", description = "Actualiza únicamente la URL de la foto de perfil del postulante. Si se envía null o cadena vacía, se elimina físicamente de R2.")
    @PatchMapping("/foto")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarFoto(
            Principal principal,
            @RequestBody FotoRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarFoto(idUsuario, request.fotoUrl());
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Foto de perfil actualizada con éxito"));
    }

    @Operation(summary = "Actualizar CV en PDF", description = "Actualiza únicamente la URL del CV en formato PDF del postulante. Si se envía null o cadena vacía, se elimina físicamente de R2.")
    @PatchMapping("/cv")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarCv(
            Principal principal,
            @RequestBody CvRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarCv(idUsuario, request.cvUrl());
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "CV actualizado con éxito"));
    }

    // =========================================================================
    // TELÉFONOS
    // =========================================================================

    @Operation(summary = "Obtener teléfonos", description = "Retorna la lista de números de teléfono registrados por el postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de teléfonos obtenida con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/telefonos")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<String>>> obtenerTelefonos(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<String> telefonos = postulanteService.obtenerTelefonos(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Teléfonos obtenidos con éxito", telefonos));
    }

    @Operation(summary = "Agregar teléfono", description = "Agrega un nuevo número de teléfono al perfil del postulante autenticado. Máximo 15 caracteres.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Teléfono agregado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/telefonos")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> agregarTelefono(
            Principal principal,
            @Valid @RequestBody TelefonoRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.agregarTelefono(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Teléfono agregado con éxito"));
    }

    @Operation(summary = "Eliminar teléfono", description = "Elimina un número de teléfono del perfil. El teléfono se pasa directamente en la URL (ej. `/telefonos/987654321`).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teléfono eliminado con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el teléfono.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/telefonos/{telefono}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarTelefono(
            Principal principal,
            @Parameter(description = "Número de teléfono a eliminar", example = "987654321")
            @PathVariable String telefono) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarTelefono(idUsuario, telefono);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Teléfono eliminado con éxito"));
    }

    // =========================================================================
    // REDES SOCIALES
    // =========================================================================

    @Operation(summary = "Obtener redes sociales", description = "Retorna la lista de redes sociales registradas por el postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Redes sociales obtenidas con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/redes-sociales")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<RedSocialResponse>>> obtenerRedesSociales(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<RedSocialResponse> redes = postulanteService.obtenerRedesSociales(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Redes sociales obtenidas con éxito", redes));
    }

    @Operation(summary = "Agregar o actualizar red social", description = "Agrega una nueva red social al perfil. Si ya existe una red social del mismo tipo, la actualiza. Usar los IDs de `/catalogos/tipos-red-social` para el campo `idTipoRedSocial`.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Red social guardada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/redes-sociales")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> agregarOActualizarRedSocial(
            Principal principal,
            @Valid @RequestBody RedSocialRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.agregarOActualizarRedSocial(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Red social guardada con éxito"));
    }

    @Operation(summary = "Actualizar red social por tipo", description = "Actualiza la URL de una red social específica. El `idTipoRedSocial` se toma del path y sobreescribe el del cuerpo si se envía.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Red social actualizada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la red social.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/redes-sociales/{idTipoRedSocial}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarRedSocial(
            Principal principal,
            @Parameter(description = "ID del tipo de red social a actualizar", example = "1")
            @PathVariable Integer idTipoRedSocial,
            @Valid @RequestBody RedSocialRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        request.setIdTipoRedSocial(idTipoRedSocial);
        postulanteService.agregarOActualizarRedSocial(idUsuario, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Red social actualizada con éxito"));
    }

    @Operation(summary = "Eliminar red social", description = "Elimina una red social del perfil por su tipo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Red social eliminada con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la red social.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/redes-sociales/{idTipoRedSocial}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarRedSocial(
            Principal principal,
            @Parameter(description = "ID del tipo de red social a eliminar", example = "1")
            @PathVariable Integer idTipoRedSocial) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarRedSocial(idUsuario, idTipoRedSocial);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Red social eliminada con éxito"));
    }

    // =========================================================================
    // EXPERIENCIA LABORAL
    // =========================================================================

    @Operation(summary = "Obtener experiencias laborales", description = "Retorna la lista de experiencias laborales del postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Experiencias laborales obtenidas con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/experiencias")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<ExperienciaLaboralResponse>>> obtenerExperiencias(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<ExperienciaLaboralResponse> lista = postulanteService.obtenerExperiencias(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Experiencias laborales obtenidas con éxito", lista));
    }

    @Operation(summary = "Agregar experiencia laboral", description = "Agrega una nueva experiencia laboral al perfil. Si `trabajoActual` es `true`, el campo `fechaFin` puede ser nulo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Experiencia laboral agregada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/experiencias")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<ExperienciaLaboralResponse>> agregarExperiencia(
            Principal principal,
            @Valid @RequestBody ExperienciaLaboralRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        ExperienciaLaboralResponse resp = postulanteService.agregarExperiencia(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Experiencia laboral agregada con éxito", resp));
    }

    @Operation(summary = "Actualizar experiencia laboral", description = "Actualiza una experiencia laboral existente. El `numExp` es el número de experiencia retornado en el campo `numExp` de la respuesta al crear o listar experiencias.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Experiencia laboral actualizada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la experiencia.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/experiencias/{numExp}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarExperiencia(
            Principal principal,
            @Parameter(description = "Número de experiencia laboral a actualizar", example = "1")
            @PathVariable Integer numExp,
            @Valid @RequestBody ExperienciaLaboralRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarExperiencia(idUsuario, numExp, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Experiencia laboral actualizada con éxito"));
    }

    @Operation(summary = "Eliminar experiencia laboral", description = "Elimina una experiencia laboral del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Experiencia laboral eliminada con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la experiencia.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/experiencias/{numExp}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarExperiencia(
            Principal principal,
            @Parameter(description = "Número de experiencia laboral a eliminar", example = "1")
            @PathVariable Integer numExp) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarExperiencia(idUsuario, numExp);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Experiencia laboral eliminada con éxito"));
    }

    // =========================================================================
    // FORMACIÓN ACADÉMICA
    // =========================================================================

    @Operation(summary = "Obtener formaciones académicas", description = "Retorna la lista de formaciones académicas del postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Formaciones académicas obtenidas con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/formaciones")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<FormacionAcademicaResponse>>> obtenerFormaciones(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<FormacionAcademicaResponse> lista = postulanteService.obtenerFormaciones(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Formaciones académicas obtenidas con éxito", lista));
    }

    @Operation(summary = "Agregar formación académica", description = "Agrega una nueva formación académica al perfil. Si `enCurso` es `true`, el campo `fechaFin` puede ser nulo. Para `idNivelEducativo`, usar los IDs de `/catalogos/niveles-educativos`.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Formación académica agregada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/formaciones")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<FormacionAcademicaResponse>> agregarFormacion(
            Principal principal,
            @Valid @RequestBody FormacionAcademicaRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        FormacionAcademicaResponse resp = postulanteService.agregarFormacion(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Formación académica agregada con éxito", resp));
    }

    @Operation(summary = "Actualizar formación académica", description = "Actualiza una formación académica existente. El `numFormacion` es el número retornado al crear o listar formaciones.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Formación académica actualizada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la formación.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/formaciones/{numFormacion}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarFormacion(
            Principal principal,
            @Parameter(description = "Número de formación académica a actualizar", example = "1")
            @PathVariable Integer numFormacion,
            @Valid @RequestBody FormacionAcademicaRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarFormacion(idUsuario, numFormacion, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Formación académica actualizada con éxito"));
    }

    @Operation(summary = "Eliminar formación académica", description = "Elimina una formación académica del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Formación académica eliminada con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la formación.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/formaciones/{numFormacion}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarFormacion(
            Principal principal,
            @Parameter(description = "Número de formación académica a eliminar", example = "1")
            @PathVariable Integer numFormacion) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarFormacion(idUsuario, numFormacion);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Formación académica eliminada con éxito"));
    }

    // =========================================================================
    // CERTIFICACIONES
    // =========================================================================

    @Operation(summary = "Obtener certificaciones", description = "Retorna la lista de certificaciones del postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Certificaciones obtenidas con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/certificaciones")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<CertificacionResponse>>> obtenerCertificaciones(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<CertificacionResponse> lista = postulanteService.obtenerCertificaciones(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Certificaciones obtenidas con éxito", lista));
    }

    @Operation(summary = "Agregar certificación", description = "Agrega una nueva certificación al perfil. Para `idTipoCertificacion`, usar los IDs de `/catalogos/tipos-certificacion`. El campo `archivoUrl` es opcional.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Certificación agregada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/certificaciones")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<CertificacionResponse>> agregarCertificacion(
            Principal principal,
            @Valid @RequestBody CertificacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        CertificacionResponse resp = postulanteService.agregarCertificacion(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Certificación agregada con éxito", resp));
    }

    @Operation(summary = "Actualizar certificación", description = "Actualiza una certificación existente. El `codCert` es el código retornado al crear o listar certificaciones.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Certificación actualizada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la certificación.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/certificaciones/{codCert}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarCertificacion(
            Principal principal,
            @Parameter(description = "Código de la certificación a actualizar", example = "1")
            @PathVariable Integer codCert,
            @Valid @RequestBody CertificacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarCertificacion(idUsuario, codCert, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Certificación actualizada con éxito"));
    }

    @Operation(summary = "Eliminar certificación", description = "Elimina una certificación del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Certificación eliminada con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la certificación.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/certificaciones/{codCert}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarCertificacion(
            Principal principal,
            @Parameter(description = "Código de la certificación a eliminar", example = "1")
            @PathVariable Integer codCert) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarCertificacion(idUsuario, codCert);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Certificación eliminada con éxito"));
    }

    // =========================================================================
    // LOGROS
    // =========================================================================

    @Operation(summary = "Obtener logros", description = "Retorna la lista de logros del postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logros obtenidos con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/logros")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<LogroResponse>>> obtenerLogros(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<LogroResponse> lista = postulanteService.obtenerLogros(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Logros obtenidos con éxito", lista));
    }

    @Operation(summary = "Agregar logro", description = "Agrega un nuevo logro al perfil del postulante.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Logro agregado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/logros")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<LogroResponse>> agregarLogro(
            Principal principal,
            @Valid @RequestBody LogroRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        LogroResponse resp = postulanteService.agregarLogro(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Logro agregado con éxito", resp));
    }

    @Operation(summary = "Actualizar logro", description = "Actualiza un logro existente. El `numLogro` es el número retornado al crear o listar logros.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logro actualizado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el logro.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/logros/{numLogro}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarLogro(
            Principal principal,
            @Parameter(description = "Número del logro a actualizar", example = "1")
            @PathVariable Integer numLogro,
            @Valid @RequestBody LogroRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarLogro(idUsuario, numLogro, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Logro actualizado con éxito"));
    }

    @Operation(summary = "Eliminar logro", description = "Elimina un logro del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logro eliminado con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el logro.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/logros/{numLogro}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarLogro(
            Principal principal,
            @Parameter(description = "Número del logro a eliminar", example = "1")
            @PathVariable Integer numLogro) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarLogro(idUsuario, numLogro);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Logro eliminado con éxito"));
    }

    // =========================================================================
    // RECOMENDACIONES
    // =========================================================================

    @Operation(summary = "Obtener recomendaciones", description = "Retorna la lista de recomendaciones del postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recomendaciones obtenidas con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/recomendaciones")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<RecomendacionResponse>>> obtenerRecomendaciones(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<RecomendacionResponse> lista = postulanteService.obtenerRecomendaciones(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Recomendaciones obtenidas con éxito", lista));
    }

    @Operation(summary = "Agregar recomendación", description = "Agrega una nueva recomendación laboral al perfil. Para `idTipoRecomendacion`, usar los IDs de `/catalogos/tipos-recomendacion`.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recomendación agregada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/recomendaciones")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<RecomendacionResponse>> agregarRecomendacion(
            Principal principal,
            @Valid @RequestBody RecomendacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        RecomendacionResponse resp = postulanteService.agregarRecomendacion(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Recomendación agregada con éxito", resp));
    }

    @Operation(summary = "Actualizar recomendación", description = "Actualiza una recomendación existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recomendación actualizada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la recomendación.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/recomendaciones/{numRecomendacion}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarRecomendacion(
            Principal principal,
            @Parameter(description = "Número de la recomendación a actualizar", example = "1")
            @PathVariable Integer numRecomendacion,
            @Valid @RequestBody RecomendacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarRecomendacion(idUsuario, numRecomendacion, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Recomendación actualizada con éxito"));
    }

    @Operation(summary = "Eliminar recomendación", description = "Elimina una recomendación del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recomendación eliminada con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la recomendación.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/recomendaciones/{numRecomendacion}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarRecomendacion(
            Principal principal,
            @Parameter(description = "Número de la recomendación a eliminar", example = "1")
            @PathVariable Integer numRecomendacion) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarRecomendacion(idUsuario, numRecomendacion);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Recomendación eliminada con éxito"));
    }

    // =========================================================================
    // EVENTOS
    // =========================================================================

    @Operation(summary = "Obtener eventos", description = "Retorna la lista de eventos en los que ha participado el postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Eventos obtenidos con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/eventos")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<EventoResponse>>> obtenerEventos(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<EventoResponse> lista = postulanteService.obtenerEventos(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Eventos obtenidos con éxito", lista));
    }

    @Operation(summary = "Agregar evento", description = "Agrega un evento al perfil. Para `idTipoParticipacion` usar `/catalogos/tipos-participacion`. Para `idPais` usar `/catalogos/paises`.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento agregado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/eventos")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<EventoResponse>> agregarEvento(
            Principal principal,
            @Valid @RequestBody EventoRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        EventoResponse resp = postulanteService.agregarEvento(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Evento agregado con éxito", resp));
    }

    @Operation(summary = "Actualizar evento", description = "Actualiza un evento existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento actualizado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el evento.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/eventos/{numEvento}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarEvento(
            Principal principal,
            @Parameter(description = "Número del evento a actualizar", example = "1")
            @PathVariable Integer numEvento,
            @Valid @RequestBody EventoRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarEvento(idUsuario, numEvento, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Evento actualizado con éxito"));
    }

    @Operation(summary = "Eliminar evento", description = "Elimina un evento del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento eliminado con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el evento.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/eventos/{numEvento}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarEvento(
            Principal principal,
            @Parameter(description = "Número del evento a eliminar", example = "1")
            @PathVariable Integer numEvento) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarEvento(idUsuario, numEvento);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Evento eliminado con éxito"));
    }

    // =========================================================================
    // PUBLICACIONES
    // =========================================================================

    @Operation(summary = "Obtener publicaciones", description = "Retorna la lista de publicaciones académicas o científicas del postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publicaciones obtenidas con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/publicaciones")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<PublicacionResponse>>> obtenerPublicaciones(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<PublicacionResponse> lista = postulanteService.obtenerPublicaciones(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Publicaciones obtenidas con éxito", lista));
    }

    @Operation(summary = "Agregar publicación", description = "Agrega una publicación (artículo, libro, paper, etc.) al perfil. Los campos `isbn` y `edicion` son opcionales.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Publicación agregada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/publicaciones")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<PublicacionResponse>> agregarPublicacion(
            Principal principal,
            @Valid @RequestBody PublicacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        PublicacionResponse resp = postulanteService.agregarPublicacion(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Publicación agregada con éxito", resp));
    }

    @Operation(summary = "Actualizar publicación", description = "Actualiza una publicación existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publicación actualizada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la publicación.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/publicaciones/{numPublicacion}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarPublicacion(
            Principal principal,
            @Parameter(description = "Número de la publicación a actualizar", example = "1")
            @PathVariable Integer numPublicacion,
            @Valid @RequestBody PublicacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarPublicacion(idUsuario, numPublicacion, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Publicación actualizada con éxito"));
    }

    @Operation(summary = "Eliminar publicación", description = "Elimina una publicación del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publicación eliminada con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la publicación.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/publicaciones/{numPublicacion}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarPublicacion(
            Principal principal,
            @Parameter(description = "Número de la publicación a eliminar", example = "1")
            @PathVariable Integer numPublicacion) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarPublicacion(idUsuario, numPublicacion);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Publicación eliminada con éxito"));
    }

    // =========================================================================
    // EXÁMENES
    // =========================================================================

    @Operation(summary = "Obtener exámenes", description = "Retorna la lista de exámenes o pruebas registradas por el postulante autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exámenes obtenidos con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/examenes")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<ExamenResponse>>> obtenerExamenes(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<ExamenResponse> lista = postulanteService.obtenerExamenes(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Exámenes obtenidos con éxito", lista));
    }

    @Operation(summary = "Agregar examen", description = "Agrega un examen o prueba al perfil. El campo `archivoUrl` es opcional (puede ser un link al certificado).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Examen agregado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/examenes")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<ExamenResponse>> agregarExamen(
            Principal principal,
            @Valid @RequestBody ExamenRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        ExamenResponse resp = postulanteService.agregarExamen(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Examen agregado con éxito", resp));
    }

    @Operation(summary = "Actualizar examen", description = "Actualiza un examen existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Examen actualizado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el examen.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/examenes/{numExamen}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarExamen(
            Principal principal,
            @Parameter(description = "Número del examen a actualizar", example = "1")
            @PathVariable Integer numExamen,
            @Valid @RequestBody ExamenRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarExamen(idUsuario, numExamen, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Examen actualizado con éxito"));
    }

    @Operation(summary = "Eliminar examen", description = "Elimina un examen del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Examen eliminado con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el examen.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/examenes/{numExamen}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarExamen(
            Principal principal,
            @Parameter(description = "Número del examen a eliminar", example = "1")
            @PathVariable Integer numExamen) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarExamen(idUsuario, numExamen);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Examen eliminado con éxito"));
    }

    // =========================================================================
    // HABILIDADES
    // =========================================================================

    @Operation(summary = "Obtener habilidades", description = "Retorna la lista de habilidades del postulante con su nivel de dominio.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habilidades obtenidas con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/habilidades")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<PostulanteHabilidadResponse>>> obtenerHabilidades(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<PostulanteHabilidadResponse> habilidades = postulanteService.obtenerHabilidades(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Habilidades obtenidas con éxito", habilidades));
    }

    @Operation(summary = "Agregar o actualizar habilidad", description = "Agrega una habilidad al perfil o actualiza su nivel si ya existe. Para `idHabilidad` usar `/catalogos/categorias-habilidad/{id}/habilidades`. Para `idNivelHabilidad` usar `/catalogos/niveles-habilidad`.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Habilidad guardada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/habilidades")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> agregarOActualizarHabilidad(
            Principal principal,
            @Valid @RequestBody PostulanteHabilidadRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.agregarOActualizarHabilidad(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Habilidad guardada con éxito"));
    }

    @Operation(summary = "Actualizar nivel de habilidad", description = "Actualiza el nivel de una habilidad existente en el perfil. El `idHabilidad` en el path sobreescribe el del cuerpo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habilidad actualizada con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la habilidad.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/habilidades/{idHabilidad}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarHabilidad(
            Principal principal,
            @Parameter(description = "ID de la habilidad a actualizar", example = "5")
            @PathVariable Integer idHabilidad,
            @Valid @RequestBody PostulanteHabilidadRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        request.setIdHabilidad(idHabilidad);
        postulanteService.agregarOActualizarHabilidad(idUsuario, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Habilidad actualizada con éxito"));
    }

    @Operation(summary = "Eliminar habilidad", description = "Elimina una habilidad del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habilidad eliminada con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la habilidad.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/habilidades/{idHabilidad}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarHabilidad(
            Principal principal,
            @Parameter(description = "ID de la habilidad a eliminar", example = "5")
            @PathVariable Integer idHabilidad) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarHabilidad(idUsuario, idHabilidad);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Habilidad eliminada con éxito"));
    }

    // =========================================================================
    // IDIOMAS
    // =========================================================================

    @Operation(summary = "Obtener idiomas", description = "Retorna la lista de idiomas del postulante con sus niveles de lectura, escritura, conversación y escucha.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Idiomas obtenidos con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/idiomas")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<List<PostulanteIdiomaResponse>>> obtenerIdiomas(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<PostulanteIdiomaResponse> idiomas = postulanteService.obtenerIdiomas(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Idiomas obtenidos con éxito", idiomas));
    }

    @Operation(summary = "Agregar o actualizar idioma", description = "Agrega un idioma al perfil o actualiza sus niveles si ya existe. Para `idIdioma` usar `/catalogos/idiomas`. Para los cuatro niveles de idioma (`idNivelLectura`, `idNivelEscritura`, `idNivelConversacion`, `idNivelEscucha`) usar `/catalogos/niveles-idioma`.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Idioma guardado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/idiomas")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> agregarOActualizarIdioma(
            Principal principal,
            @Valid @RequestBody PostulanteIdiomaRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.agregarOActualizarIdioma(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Idioma guardado con éxito"));
    }

    @Operation(summary = "Actualizar niveles de idioma", description = "Actualiza los niveles de un idioma ya registrado en el perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Idioma actualizado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el idioma.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/idiomas/{idIdioma}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarIdioma(
            Principal principal,
            @Parameter(description = "ID del idioma a actualizar", example = "1")
            @PathVariable Integer idIdioma,
            @Valid @RequestBody PostulanteIdiomaRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        request.setIdIdioma(idIdioma);
        postulanteService.agregarOActualizarIdioma(idUsuario, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Idioma actualizado con éxito"));
    }

    @Operation(summary = "Eliminar idioma", description = "Elimina un idioma del perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Idioma eliminado con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el idioma.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/idiomas/{idIdioma}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarIdioma(
            Principal principal,
            @Parameter(description = "ID del idioma a eliminar", example = "1")
            @PathVariable Integer idIdioma) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarIdioma(idUsuario, idIdioma);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Idioma eliminado con éxito"));
    }

    @Operation(summary = "Eliminar cuenta de postulante", description = "Elimina permanentemente el perfil del postulante y su usuario asociado, incluyendo sus archivos subidos a R2.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta eliminada con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarCuenta(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarPerfilYUsuario(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Cuenta de postulante eliminada con éxito"));
    }
}
