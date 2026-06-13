package com.jobhorizon.backend.postulante.dto;

import com.jobhorizon.backend.postulante.certificacion.dto.CertificacionResponse;
import com.jobhorizon.backend.postulante.evento.dto.EventoResponse;
import com.jobhorizon.backend.postulante.experiencia.dto.ExperienciaLaboralResponse;
import com.jobhorizon.backend.postulante.formacion.dto.FormacionAcademicaResponse;
import com.jobhorizon.backend.postulante.habilidad.dto.PostulanteHabilidadResponse;
import com.jobhorizon.backend.postulante.idioma.dto.PostulanteIdiomaResponse;
import com.jobhorizon.backend.postulante.logro.dto.LogroResponse;
import com.jobhorizon.backend.postulante.publicacion.dto.PublicacionResponse;
import com.jobhorizon.backend.postulante.examen.dto.ExamenResponse;
import com.jobhorizon.backend.postulante.recomendacion.dto.RecomendacionResponse;
import com.jobhorizon.backend.postulante.redsocial.dto.RedSocialResponse;

import java.util.List;

/**
 * DTO que consolida todo el perfil del postulante (Opción C).
 */
public record PostulantePerfilResponse(
    DatosPersonalesResponse datosPersonales,
    List<String> telefonos,
    List<RedSocialResponse> redesSociales,
    List<ExperienciaLaboralResponse> experiencias,
    List<FormacionAcademicaResponse> formaciones,
    List<CertificacionResponse> certificaciones,
    List<LogroResponse> logros,
    List<RecomendacionResponse> recomendaciones,
    List<EventoResponse> eventos,
    List<PublicacionResponse> publicaciones,
    List<ExamenResponse> examenes,
    List<PostulanteHabilidadResponse> habilidades,
    List<PostulanteIdiomaResponse> idiomas
) {}
