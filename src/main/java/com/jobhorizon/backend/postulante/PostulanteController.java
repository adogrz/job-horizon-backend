package com.jobhorizon.backend.postulante;

import com.jobhorizon.backend.config.ApiResponse;
import com.jobhorizon.backend.postulante.certificacion.dto.CertificacionRequest;
import com.jobhorizon.backend.postulante.certificacion.dto.CertificacionResponse;
import com.jobhorizon.backend.postulante.dto.ActualizarDatosPersonalesRequest;
import com.jobhorizon.backend.postulante.dto.DatosPersonalesResponse;
import com.jobhorizon.backend.postulante.dto.PostulantePerfilResponse;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/postulante/perfil")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTIONAR_PERFIL')")
public class PostulanteController {

    private final PostulanteService postulanteService;

    // --- PERFIL COMPLETO ---

    @GetMapping
    public ResponseEntity<ApiResponse<PostulantePerfilResponse>> obtenerPerfilCompleto(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        PostulantePerfilResponse perfil = postulanteService.obtenerPerfilCompleto(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Perfil completo obtenido con éxito", perfil));
    }

    // --- DATOS PERSONALES ---

    @GetMapping("/datos-personales")
    public ResponseEntity<ApiResponse<DatosPersonalesResponse>> obtenerDatosPersonales(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        DatosPersonalesResponse datos = postulanteService.obtenerDatosPersonales(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Datos personales obtenidos con éxito", datos));
    }

    @PutMapping("/datos-personales")
    public ResponseEntity<ApiResponse<DatosPersonalesResponse>> actualizarDatosPersonales(
            Principal principal,
            @Valid @RequestBody ActualizarDatosPersonalesRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        DatosPersonalesResponse datos = postulanteService.actualizarDatosPersonales(idUsuario, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Datos personales actualizados con éxito", datos));
    }

    // --- TELEFONOS ---

    @GetMapping("/telefonos")
    public ResponseEntity<ApiResponse<List<String>>> obtenerTelefonos(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<String> telefonos = postulanteService.obtenerTelefonos(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Teléfonos obtenidos con éxito", telefonos));
    }

    @PostMapping("/telefonos")
    public ResponseEntity<ApiResponse<Void>> agregarTelefono(
            Principal principal,
            @Valid @RequestBody TelefonoRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.agregarTelefono(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Teléfono agregado con éxito"));
    }

    @DeleteMapping("/telefonos/{telefono}")
    public ResponseEntity<ApiResponse<Void>> eliminarTelefono(
            Principal principal,
            @PathVariable String telefono) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarTelefono(idUsuario, telefono);
        return ResponseEntity.ok(new ApiResponse<>(true, "Teléfono eliminado con éxito"));
    }

    // --- REDES SOCIALES ---

    @GetMapping("/redes-sociales")
    public ResponseEntity<ApiResponse<List<RedSocialResponse>>> obtenerRedesSociales(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<RedSocialResponse> redes = postulanteService.obtenerRedesSociales(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Redes sociales obtenidas con éxito", redes));
    }

    @PostMapping("/redes-sociales")
    public ResponseEntity<ApiResponse<Void>> agregarOActualizarRedSocial(
            Principal principal,
            @Valid @RequestBody RedSocialRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.agregarOActualizarRedSocial(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Red social guardada con éxito"));
    }

    @PutMapping("/redes-sociales/{idTipoRedSocial}")
    public ResponseEntity<ApiResponse<Void>> actualizarRedSocial(
            Principal principal,
            @PathVariable Integer idTipoRedSocial,
            @Valid @RequestBody RedSocialRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        request.setIdTipoRedSocial(idTipoRedSocial);
        postulanteService.agregarOActualizarRedSocial(idUsuario, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Red social actualizada con éxito"));
    }

    @DeleteMapping("/redes-sociales/{idTipoRedSocial}")
    public ResponseEntity<ApiResponse<Void>> eliminarRedSocial(
            Principal principal,
            @PathVariable Integer idTipoRedSocial) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarRedSocial(idUsuario, idTipoRedSocial);
        return ResponseEntity.ok(new ApiResponse<>(true, "Red social eliminada con éxito"));
    }

    // --- EXPERIENCIA LABORAL ---

    @GetMapping("/experiencias")
    public ResponseEntity<ApiResponse<List<ExperienciaLaboralResponse>>> obtenerExperiencias(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<ExperienciaLaboralResponse> lista = postulanteService.obtenerExperiencias(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Experiencias laborales obtenidas con éxito", lista));
    }

    @PostMapping("/experiencias")
    public ResponseEntity<ApiResponse<ExperienciaLaboralResponse>> agregarExperiencia(
            Principal principal,
            @Valid @RequestBody ExperienciaLaboralRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        ExperienciaLaboralResponse resp = postulanteService.agregarExperiencia(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Experiencia laboral agregada con éxito", resp));
    }

    @PutMapping("/experiencias/{numExp}")
    public ResponseEntity<ApiResponse<Void>> actualizarExperiencia(
            Principal principal,
            @PathVariable Integer numExp,
            @Valid @RequestBody ExperienciaLaboralRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarExperiencia(idUsuario, numExp, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Experiencia laboral actualizada con éxito"));
    }

    @DeleteMapping("/experiencias/{numExp}")
    public ResponseEntity<ApiResponse<Void>> eliminarExperiencia(
            Principal principal,
            @PathVariable Integer numExp) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarExperiencia(idUsuario, numExp);
        return ResponseEntity.ok(new ApiResponse<>(true, "Experiencia laboral eliminada con éxito"));
    }

    // --- FORMACION ACADEMICA ---

    @GetMapping("/formaciones")
    public ResponseEntity<ApiResponse<List<FormacionAcademicaResponse>>> obtenerFormaciones(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<FormacionAcademicaResponse> lista = postulanteService.obtenerFormaciones(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Formaciones académicas obtenidas con éxito", lista));
    }

    @PostMapping("/formaciones")
    public ResponseEntity<ApiResponse<FormacionAcademicaResponse>> agregarFormacion(
            Principal principal,
            @Valid @RequestBody FormacionAcademicaRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        FormacionAcademicaResponse resp = postulanteService.agregarFormacion(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Formación académica agregada con éxito", resp));
    }

    @PutMapping("/formaciones/{numFormacion}")
    public ResponseEntity<ApiResponse<Void>> actualizarFormacion(
            Principal principal,
            @PathVariable Integer numFormacion,
            @Valid @RequestBody FormacionAcademicaRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarFormacion(idUsuario, numFormacion, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Formación académica actualizada con éxito"));
    }

    @DeleteMapping("/formaciones/{numFormacion}")
    public ResponseEntity<ApiResponse<Void>> eliminarFormacion(
            Principal principal,
            @PathVariable Integer numFormacion) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarFormacion(idUsuario, numFormacion);
        return ResponseEntity.ok(new ApiResponse<>(true, "Formación académica eliminada con éxito"));
    }

    // --- CERTIFICACIONES ---

    @GetMapping("/certificaciones")
    public ResponseEntity<ApiResponse<List<CertificacionResponse>>> obtenerCertificaciones(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<CertificacionResponse> lista = postulanteService.obtenerCertificaciones(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Certificaciones obtenidas con éxito", lista));
    }

    @PostMapping("/certificaciones")
    public ResponseEntity<ApiResponse<CertificacionResponse>> agregarCertificacion(
            Principal principal,
            @Valid @RequestBody CertificacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        CertificacionResponse resp = postulanteService.agregarCertificacion(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Certificación agregada con éxito", resp));
    }

    @PutMapping("/certificaciones/{codCert}")
    public ResponseEntity<ApiResponse<Void>> actualizarCertificacion(
            Principal principal,
            @PathVariable Integer codCert,
            @Valid @RequestBody CertificacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarCertificacion(idUsuario, codCert, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Certificación actualizada con éxito"));
    }

    @DeleteMapping("/certificaciones/{codCert}")
    public ResponseEntity<ApiResponse<Void>> eliminarCertificacion(
            Principal principal,
            @PathVariable Integer codCert) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarCertificacion(idUsuario, codCert);
        return ResponseEntity.ok(new ApiResponse<>(true, "Certificación eliminada con éxito"));
    }

    // --- LOGROS ---

    @GetMapping("/logros")
    public ResponseEntity<ApiResponse<List<LogroResponse>>> obtenerLogros(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<LogroResponse> lista = postulanteService.obtenerLogros(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Logros obtenidos con éxito", lista));
    }

    @PostMapping("/logros")
    public ResponseEntity<ApiResponse<LogroResponse>> agregarLogro(
            Principal principal,
            @Valid @RequestBody LogroRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        LogroResponse resp = postulanteService.agregarLogro(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Logro agregado con éxito", resp));
    }

    @PutMapping("/logros/{numLogro}")
    public ResponseEntity<ApiResponse<Void>> actualizarLogro(
            Principal principal,
            @PathVariable Integer numLogro,
            @Valid @RequestBody LogroRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarLogro(idUsuario, numLogro, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Logro actualizado con éxito"));
    }

    @DeleteMapping("/logros/{numLogro}")
    public ResponseEntity<ApiResponse<Void>> eliminarLogro(
            Principal principal,
            @PathVariable Integer numLogro) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarLogro(idUsuario, numLogro);
        return ResponseEntity.ok(new ApiResponse<>(true, "Logro eliminado con éxito"));
    }

    // --- RECOMENDACIONES ---

    @GetMapping("/recomendaciones")
    public ResponseEntity<ApiResponse<List<RecomendacionResponse>>> obtenerRecomendaciones(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<RecomendacionResponse> lista = postulanteService.obtenerRecomendaciones(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recomendaciones obtenidas con éxito", lista));
    }

    @PostMapping("/recomendaciones")
    public ResponseEntity<ApiResponse<RecomendacionResponse>> agregarRecomendacion(
            Principal principal,
            @Valid @RequestBody RecomendacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        RecomendacionResponse resp = postulanteService.agregarRecomendacion(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Recomendación agregada con éxito", resp));
    }

    @PutMapping("/recomendaciones/{numRecomendacion}")
    public ResponseEntity<ApiResponse<Void>> actualizarRecomendacion(
            Principal principal,
            @PathVariable Integer numRecomendacion,
            @Valid @RequestBody RecomendacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarRecomendacion(idUsuario, numRecomendacion, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recomendación actualizada con éxito"));
    }

    @DeleteMapping("/recomendaciones/{numRecomendacion}")
    public ResponseEntity<ApiResponse<Void>> eliminarRecomendacion(
            Principal principal,
            @PathVariable Integer numRecomendacion) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarRecomendacion(idUsuario, numRecomendacion);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recomendación eliminada con éxito"));
    }

    // --- EVENTOS ---

    @GetMapping("/eventos")
    public ResponseEntity<ApiResponse<List<EventoResponse>>> obtenerEventos(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<EventoResponse> lista = postulanteService.obtenerEventos(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Eventos obtenidos con éxito", lista));
    }

    @PostMapping("/eventos")
    public ResponseEntity<ApiResponse<EventoResponse>> agregarEvento(
            Principal principal,
            @Valid @RequestBody EventoRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        EventoResponse resp = postulanteService.agregarEvento(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Evento agregado con éxito", resp));
    }

    @PutMapping("/eventos/{numEvento}")
    public ResponseEntity<ApiResponse<Void>> actualizarEvento(
            Principal principal,
            @PathVariable Integer numEvento,
            @Valid @RequestBody EventoRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarEvento(idUsuario, numEvento, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Evento actualizado con éxito"));
    }

    @DeleteMapping("/eventos/{numEvento}")
    public ResponseEntity<ApiResponse<Void>> eliminarEvento(
            Principal principal,
            @PathVariable Integer numEvento) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarEvento(idUsuario, numEvento);
        return ResponseEntity.ok(new ApiResponse<>(true, "Evento eliminado con éxito"));
    }

    // --- PUBLICACIONES ---

    @GetMapping("/publicaciones")
    public ResponseEntity<ApiResponse<List<PublicacionResponse>>> obtenerPublicaciones(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<PublicacionResponse> lista = postulanteService.obtenerPublicaciones(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Publicaciones obtenidas con éxito", lista));
    }

    @PostMapping("/publicaciones")
    public ResponseEntity<ApiResponse<PublicacionResponse>> agregarPublicacion(
            Principal principal,
            @Valid @RequestBody PublicacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        PublicacionResponse resp = postulanteService.agregarPublicacion(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Publicación agregada con éxito", resp));
    }

    @PutMapping("/publicaciones/{numPublicacion}")
    public ResponseEntity<ApiResponse<Void>> actualizarPublicacion(
            Principal principal,
            @PathVariable Integer numPublicacion,
            @Valid @RequestBody PublicacionRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarPublicacion(idUsuario, numPublicacion, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Publicación actualizada con éxito"));
    }

    @DeleteMapping("/publicaciones/{numPublicacion}")
    public ResponseEntity<ApiResponse<Void>> eliminarPublicacion(
            Principal principal,
            @PathVariable Integer numPublicacion) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarPublicacion(idUsuario, numPublicacion);
        return ResponseEntity.ok(new ApiResponse<>(true, "Publicación eliminada con éxito"));
    }

    // --- EXAMENES ---

    @GetMapping("/examenes")
    public ResponseEntity<ApiResponse<List<ExamenResponse>>> obtenerExamenes(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<ExamenResponse> lista = postulanteService.obtenerExamenes(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Exámenes obtenidos con éxito", lista));
    }

    @PostMapping("/examenes")
    public ResponseEntity<ApiResponse<ExamenResponse>> agregarExamen(
            Principal principal,
            @Valid @RequestBody ExamenRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        ExamenResponse resp = postulanteService.agregarExamen(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Examen agregado con éxito", resp));
    }

    @PutMapping("/examenes/{numExamen}")
    public ResponseEntity<ApiResponse<Void>> actualizarExamen(
            Principal principal,
            @PathVariable Integer numExamen,
            @Valid @RequestBody ExamenRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.actualizarExamen(idUsuario, numExamen, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Examen actualizado con éxito"));
    }

    @DeleteMapping("/examenes/{numExamen}")
    public ResponseEntity<ApiResponse<Void>> eliminarExamen(
            Principal principal,
            @PathVariable Integer numExamen) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarExamen(idUsuario, numExamen);
        return ResponseEntity.ok(new ApiResponse<>(true, "Examen eliminado con éxito"));
    }

    // --- HABILIDADES ---

    @GetMapping("/habilidades")
    public ResponseEntity<ApiResponse<List<PostulanteHabilidadResponse>>> obtenerHabilidades(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<PostulanteHabilidadResponse> habilidades = postulanteService.obtenerHabilidades(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Habilidades obtenidas con éxito", habilidades));
    }

    @PostMapping("/habilidades")
    public ResponseEntity<ApiResponse<Void>> agregarOActualizarHabilidad(
            Principal principal,
            @Valid @RequestBody PostulanteHabilidadRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.agregarOActualizarHabilidad(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Habilidad guardada con éxito"));
    }

    @PutMapping("/habilidades/{idHabilidad}")
    public ResponseEntity<ApiResponse<Void>> actualizarHabilidad(
            Principal principal,
            @PathVariable Integer idHabilidad,
            @Valid @RequestBody PostulanteHabilidadRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        request.setIdHabilidad(idHabilidad);
        postulanteService.agregarOActualizarHabilidad(idUsuario, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Habilidad actualizada con éxito"));
    }

    @DeleteMapping("/habilidades/{idHabilidad}")
    public ResponseEntity<ApiResponse<Void>> eliminarHabilidad(
            Principal principal,
            @PathVariable Integer idHabilidad) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarHabilidad(idUsuario, idHabilidad);
        return ResponseEntity.ok(new ApiResponse<>(true, "Habilidad eliminada con éxito"));
    }

    // --- IDIOMAS ---

    @GetMapping("/idiomas")
    public ResponseEntity<ApiResponse<List<PostulanteIdiomaResponse>>> obtenerIdiomas(Principal principal) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        List<PostulanteIdiomaResponse> idiomas = postulanteService.obtenerIdiomas(idUsuario);
        return ResponseEntity.ok(new ApiResponse<>(true, "Idiomas obtenidos con éxito", idiomas));
    }

    @PostMapping("/idiomas")
    public ResponseEntity<ApiResponse<Void>> agregarOActualizarIdioma(
            Principal principal,
            @Valid @RequestBody PostulanteIdiomaRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.agregarOActualizarIdioma(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Idioma guardado con éxito"));
    }

    @PutMapping("/idiomas/{idIdioma}")
    public ResponseEntity<ApiResponse<Void>> actualizarIdioma(
            Principal principal,
            @PathVariable Integer idIdioma,
            @Valid @RequestBody PostulanteIdiomaRequest request) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        request.setIdIdioma(idIdioma);
        postulanteService.agregarOActualizarIdioma(idUsuario, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Idioma actualizado con éxito"));
    }

    @DeleteMapping("/idiomas/{idIdioma}")
    public ResponseEntity<ApiResponse<Void>> eliminarIdioma(
            Principal principal,
            @PathVariable Integer idIdioma) {
        Integer idUsuario = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulanteService.eliminarIdioma(idUsuario, idIdioma);
        return ResponseEntity.ok(new ApiResponse<>(true, "Idioma eliminado con éxito"));
    }
}
