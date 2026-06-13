package com.jobhorizon.backend.postulante;

import com.jobhorizon.backend.departamento.Departamento;
import com.jobhorizon.backend.departamento.DepartamentoRepository;
import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.distrito.DistritoRepository;
import com.jobhorizon.backend.genero.Genero;
import com.jobhorizon.backend.genero.GeneroRepository;
import com.jobhorizon.backend.habilidad.Habilidad;
import com.jobhorizon.backend.habilidad.HabilidadRepository;
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
import com.jobhorizon.backend.postulante.certificacion.Certificacion;
import com.jobhorizon.backend.postulante.certificacion.CertificacionId;
import com.jobhorizon.backend.postulante.certificacion.CertificacionRepository;
import com.jobhorizon.backend.postulante.certificacion.dto.CertificacionRequest;
import com.jobhorizon.backend.postulante.certificacion.dto.CertificacionResponse;
import com.jobhorizon.backend.postulante.dto.ActualizarDatosPersonalesRequest;
import com.jobhorizon.backend.postulante.dto.DatosPersonalesResponse;
import com.jobhorizon.backend.postulante.dto.PostulantePerfilResponse;
import com.jobhorizon.backend.postulante.evento.Evento;
import com.jobhorizon.backend.postulante.evento.EventoId;
import com.jobhorizon.backend.postulante.evento.EventoRepository;
import com.jobhorizon.backend.postulante.evento.dto.EventoRequest;
import com.jobhorizon.backend.postulante.evento.dto.EventoResponse;
import com.jobhorizon.backend.postulante.examen.Examen;
import com.jobhorizon.backend.postulante.examen.ExamenId;
import com.jobhorizon.backend.postulante.examen.ExamenRepository;
import com.jobhorizon.backend.postulante.examen.dto.ExamenRequest;
import com.jobhorizon.backend.postulante.examen.dto.ExamenResponse;
import com.jobhorizon.backend.postulante.experiencia.ExperienciaLaboral;
import com.jobhorizon.backend.postulante.experiencia.ExperienciaLaboralId;
import com.jobhorizon.backend.postulante.experiencia.ExperienciaLaboralRepository;
import com.jobhorizon.backend.postulante.experiencia.dto.ExperienciaLaboralRequest;
import com.jobhorizon.backend.postulante.experiencia.dto.ExperienciaLaboralResponse;
import com.jobhorizon.backend.postulante.formacion.FormacionAcademica;
import com.jobhorizon.backend.postulante.formacion.FormacionAcademicaId;
import com.jobhorizon.backend.postulante.formacion.FormacionAcademicaRepository;
import com.jobhorizon.backend.postulante.formacion.dto.FormacionAcademicaRequest;
import com.jobhorizon.backend.postulante.formacion.dto.FormacionAcademicaResponse;
import com.jobhorizon.backend.postulante.habilidad.PostulanteHabilidad;
import com.jobhorizon.backend.postulante.habilidad.PostulanteHabilidadId;
import com.jobhorizon.backend.postulante.habilidad.PostulanteHabilidadRepository;
import com.jobhorizon.backend.postulante.habilidad.dto.PostulanteHabilidadRequest;
import com.jobhorizon.backend.postulante.habilidad.dto.PostulanteHabilidadResponse;
import com.jobhorizon.backend.postulante.idioma.PostulanteIdioma;
import com.jobhorizon.backend.postulante.idioma.PostulanteIdiomaId;
import com.jobhorizon.backend.postulante.idioma.PostulanteIdiomaRepository;
import com.jobhorizon.backend.postulante.idioma.dto.PostulanteIdiomaRequest;
import com.jobhorizon.backend.postulante.idioma.dto.PostulanteIdiomaResponse;
import com.jobhorizon.backend.postulante.logro.Logro;
import com.jobhorizon.backend.postulante.logro.LogroId;
import com.jobhorizon.backend.postulante.logro.LogroRepository;
import com.jobhorizon.backend.postulante.logro.dto.LogroRequest;
import com.jobhorizon.backend.postulante.logro.dto.LogroResponse;
import com.jobhorizon.backend.postulante.publicacion.Publicacion;
import com.jobhorizon.backend.postulante.publicacion.PublicacionId;
import com.jobhorizon.backend.postulante.publicacion.PublicacionRepository;
import com.jobhorizon.backend.postulante.publicacion.dto.PublicacionRequest;
import com.jobhorizon.backend.postulante.publicacion.dto.PublicacionResponse;
import com.jobhorizon.backend.postulante.recomendacion.Recomendacion;
import com.jobhorizon.backend.postulante.recomendacion.RecomendacionId;
import com.jobhorizon.backend.postulante.recomendacion.RecomendacionRepository;
import com.jobhorizon.backend.postulante.recomendacion.dto.RecomendacionRequest;
import com.jobhorizon.backend.postulante.recomendacion.dto.RecomendacionResponse;
import com.jobhorizon.backend.postulante.redsocial.PostulanteRedSocial;
import com.jobhorizon.backend.postulante.redsocial.PostulanteRedSocialId;
import com.jobhorizon.backend.postulante.redsocial.PostulanteRedSocialRepository;
import com.jobhorizon.backend.postulante.redsocial.dto.RedSocialRequest;
import com.jobhorizon.backend.postulante.redsocial.dto.RedSocialResponse;
import com.jobhorizon.backend.postulante.telefono.PostulanteTelefono;
import com.jobhorizon.backend.postulante.telefono.PostulanteTelefonoId;
import com.jobhorizon.backend.postulante.telefono.PostulanteTelefonoRepository;
import com.jobhorizon.backend.postulante.telefono.dto.TelefonoRequest;
import com.jobhorizon.backend.seguridad.exception.RecursoNoEncontradoException;
import com.jobhorizon.backend.seguridad.usuario.Usuario;
import com.jobhorizon.backend.seguridad.usuario.UsuarioRepository;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostulanteService {

    private final PostulanteRepository postulanteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PostulanteTelefonoRepository telefonoRepository;
    private final PostulanteRedSocialRepository redSocialRepository;
    private final ExperienciaLaboralRepository experienciaRepository;
    private final FormacionAcademicaRepository formacionRepository;
    private final CertificacionRepository certificacionRepository;
    private final LogroRepository logroRepository;
    private final RecomendacionRepository recomendacionRepository;
    private final EventoRepository eventoRepository;
    private final PublicacionRepository publicacionRepository;
    private final ExamenRepository examenRepository;
    private final PostulanteHabilidadRepository habilidadRepository;
    private final PostulanteIdiomaRepository idiomaRepository;

    // Repositorios de catálogos
    private final GeneroRepository generoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DistritoRepository distritoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final TipoRedSocialRepository tipoRedSocialRepository;
    private final NivelEducativoRepository nivelEducativoRepository;
    private final TipoCertificacionRepository tipoCertificacionRepository;
    private final TipoRecomendacionRepository tipoRecomendacionRepository;
    private final TipoParticipacionRepository tipoParticipacionRepository;
    private final PaisRepository paisRepository;
    private final HabilidadRepository rawHabilidadRepository;
    private final NivelHabilidadRepository nivelHabilidadRepository;
    private final IdiomaRepository rawIdiomaRepository;
    private final NivelIdiomaRepository nivelIdiomaRepository;

    private Postulante buscarPostulante(Integer idUsuario) {
        return postulanteRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Postulante no encontrado con ID: " + idUsuario));
    }

    @Transactional(readOnly = true)
    public Integer obtenerIdUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(Usuario::getId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con correo: " + correo));
    }

    // --- DATOS PERSONALES ---

    @Transactional(readOnly = true)
    public DatosPersonalesResponse obtenerDatosPersonales(Integer idUsuario) {
        return mapearADatosPersonalesResponse(buscarPostulante(idUsuario));
    }

    @Transactional
    public DatosPersonalesResponse actualizarDatosPersonales(Integer idUsuario, ActualizarDatosPersonalesRequest request) {
        Postulante p = buscarPostulante(idUsuario);

        Genero g = generoRepository.findById(request.getIdGenero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Género no encontrado con ID: " + request.getIdGenero()));
        TipoDocumento td = tipoDocumentoRepository.findById(request.getIdTipoDocumento())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de documento no encontrado con ID: " + request.getIdTipoDocumento()));
        Distrito d = distritoRepository.findById(request.getIdDistrito())
                .orElseThrow(() -> new RecursoNoEncontradoException("Distrito no encontrado con ID: " + request.getIdDistrito()));

        p.setNombres(request.getNombres());
        p.setApellidos(request.getApellidos());
        p.setFechaNacimiento(request.getFechaNacimiento());
        p.setNumDocumento(request.getNumDocumento());
        p.setNup(request.getNup());
        p.setNit(request.getNit());
        p.setDireccion(request.getDireccion());
        p.setFotoUrl(request.getFotoUrl());
        p.setGenero(g);
        p.setTipoDocumento(td);
        p.setDistrito(d);

        postulanteRepository.save(p);
        return mapearADatosPersonalesResponse(p);
    }

    private DatosPersonalesResponse mapearADatosPersonalesResponse(Postulante p) {
        String deptoNombre = null;
        if (p.getDistrito() != null && p.getDistrito().getIdDepartamento() != null) {
            deptoNombre = departamentoRepository.findById(p.getDistrito().getIdDepartamento())
                    .map(Departamento::getNombre)
                    .orElse(null);
        }

        return new DatosPersonalesResponse(
                p.getId(),
                p.getUsuario() != null ? p.getUsuario().getCorreo() : null,
                p.getNombres(),
                p.getApellidos(),
                p.getFechaNacimiento(),
                p.getNumDocumento(),
                p.getNup(),
                p.getNit(),
                p.getDireccion(),
                p.getFotoUrl(),
                p.getGenero() != null ? p.getGenero().getId() : null,
                p.getGenero() != null ? p.getGenero().getNombre() : null,
                p.getTipoDocumento() != null ? p.getTipoDocumento().getId() : null,
                p.getTipoDocumento() != null ? p.getTipoDocumento().getNombre() : null,
                p.getDistrito() != null ? p.getDistrito().getId() : null,
                p.getDistrito() != null ? p.getDistrito().getNombre() : null,
                p.getDistrito() != null ? p.getDistrito().getIdDepartamento() : null,
                deptoNombre
        );
    }

    // --- TELEFONOS ---

    @Transactional(readOnly = true)
    public List<String> obtenerTelefonos(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return telefonoRepository.findByIdUsuario(idUsuario).stream()
                .map(PostulanteTelefono::getTelefono)
                .collect(Collectors.toList());
    }

    @Transactional
    public void agregarTelefono(Integer idUsuario, TelefonoRequest request) {
        buscarPostulante(idUsuario);
        PostulanteTelefonoId id = new PostulanteTelefonoId(idUsuario, request.getTelefono());
        if (telefonoRepository.existsById(id)) {
            return; // Ya existe, no hacer nada
        }
        PostulanteTelefono pt = PostulanteTelefono.builder()
                .idUsuario(idUsuario)
                .telefono(request.getTelefono())
                .build();
        telefonoRepository.save(pt);
    }

    @Transactional
    public void eliminarTelefono(Integer idUsuario, String telefono) {
        buscarPostulante(idUsuario);
        PostulanteTelefonoId id = new PostulanteTelefonoId(idUsuario, telefono);
        if (telefonoRepository.existsById(id)) {
            telefonoRepository.deleteById(id);
        }
    }

    // --- REDES SOCIALES ---

    @Transactional(readOnly = true)
    public List<RedSocialResponse> obtenerRedesSociales(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return redSocialRepository.findByIdUsuario(idUsuario).stream()
                .map(r -> new RedSocialResponse(
                        r.getIdTipoRedSocial(),
                        r.getTipoRedSocial() != null ? r.getTipoRedSocial().getNombre() : null,
                        r.getUrl()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void agregarOActualizarRedSocial(Integer idUsuario, RedSocialRequest request) {
        buscarPostulante(idUsuario);
        TipoRedSocial trs = tipoRedSocialRepository.findById(request.getIdTipoRedSocial())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de red social no encontrado con ID: " + request.getIdTipoRedSocial()));

        PostulanteRedSocialId id = new PostulanteRedSocialId(idUsuario, request.getIdTipoRedSocial());
        PostulanteRedSocial prs = redSocialRepository.findById(id)
                .orElseGet(() -> PostulanteRedSocial.builder()
                        .idUsuario(idUsuario)
                        .idTipoRedSocial(request.getIdTipoRedSocial())
                        .build());
        prs.setUrl(request.getUrl());
        redSocialRepository.save(prs);
    }

    @Transactional
    public void eliminarRedSocial(Integer idUsuario, Integer idTipoRedSocial) {
        buscarPostulante(idUsuario);
        PostulanteRedSocialId id = new PostulanteRedSocialId(idUsuario, idTipoRedSocial);
        if (redSocialRepository.existsById(id)) {
            redSocialRepository.deleteById(id);
        }
    }

    // --- EXPERIENCIA LABORAL ---

    @Transactional(readOnly = true)
    public List<ExperienciaLaboralResponse> obtenerExperiencias(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return experienciaRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAExperienciaResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExperienciaLaboralResponse agregarExperiencia(Integer idUsuario, ExperienciaLaboralRequest request) {
        buscarPostulante(idUsuario);

        int nextNum = experienciaRepository.findByIdUsuario(idUsuario).stream()
                .mapToInt(ExperienciaLaboral::getNumExp)
                .max()
                .orElse(0) + 1;

        ExperienciaLaboral el = ExperienciaLaboral.builder()
                .numExp(nextNum)
                .idUsuario(idUsuario)
                .nombreEmpresa(request.getNombreEmpresa())
                .puesto(request.getPuesto())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .trabajoActual(request.getTrabajoActual() != null && request.getTrabajoActual())
                .funciones(request.getFunciones())
                .telefonoContacto(request.getTelefonoContacto())
                .correoContacto(request.getCorreoContacto())
                .build();

        ExperienciaLaboral guardada = experienciaRepository.save(el);
        return mapearAExperienciaResponse(guardada);
    }

    @Transactional
    public void actualizarExperiencia(Integer idUsuario, Integer numExp, ExperienciaLaboralRequest request) {
        buscarPostulante(idUsuario);
        ExperienciaLaboralId id = new ExperienciaLaboralId(numExp, idUsuario);
        ExperienciaLaboral el = experienciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Experiencia laboral no encontrada con numExp: " + numExp));

        el.setNombreEmpresa(request.getNombreEmpresa());
        el.setPuesto(request.getPuesto());
        el.setFechaInicio(request.getFechaInicio());
        el.setFechaFin(request.getFechaFin());
        el.setTrabajoActual(request.getTrabajoActual() != null && request.getTrabajoActual());
        el.setFunciones(request.getFunciones());
        el.setTelefonoContacto(request.getTelefonoContacto());
        el.setCorreoContacto(request.getCorreoContacto());

        experienciaRepository.save(el);
    }

    @Transactional
    public void eliminarExperiencia(Integer idUsuario, Integer numExp) {
        buscarPostulante(idUsuario);
        ExperienciaLaboralId id = new ExperienciaLaboralId(numExp, idUsuario);
        if (experienciaRepository.existsById(id)) {
            experienciaRepository.deleteById(id);
        }
    }

    private ExperienciaLaboralResponse mapearAExperienciaResponse(ExperienciaLaboral el) {
        return new ExperienciaLaboralResponse(
                el.getNumExp(),
                el.getNombreEmpresa(),
                el.getPuesto(),
                el.getFechaInicio(),
                el.getFechaFin(),
                el.getTrabajoActual(),
                el.getFunciones(),
                el.getTelefonoContacto(),
                el.getCorreoContacto()
        );
    }

    // --- FORMACION ACADEMICA ---

    @Transactional(readOnly = true)
    public List<FormacionAcademicaResponse> obtenerFormaciones(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return formacionRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAFormacionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public FormacionAcademicaResponse agregarFormacion(Integer idUsuario, FormacionAcademicaRequest request) {
        buscarPostulante(idUsuario);
        NivelEducativo ne = nivelEducativoRepository.findById(request.getIdNivelEducativo())
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel educativo no encontrado con ID: " + request.getIdNivelEducativo()));

        int nextNum = formacionRepository.findByIdUsuario(idUsuario).stream()
                .mapToInt(FormacionAcademica::getNumFormacion)
                .max()
                .orElse(0) + 1;

        FormacionAcademica fa = FormacionAcademica.builder()
                .numFormacion(nextNum)
                .idUsuario(idUsuario)
                .institucion(request.getInstitucion())
                .titulo(request.getTitulo())
                .nivelEducativo(ne)
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .enCurso(request.getEnCurso() != null && request.getEnCurso())
                .build();

        FormacionAcademica guardada = formacionRepository.save(fa);
        return mapearAFormacionResponse(guardada);
    }

    @Transactional
    public void actualizarFormacion(Integer idUsuario, Integer numFormacion, FormacionAcademicaRequest request) {
        buscarPostulante(idUsuario);
        NivelEducativo ne = nivelEducativoRepository.findById(request.getIdNivelEducativo())
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel educativo no encontrado con ID: " + request.getIdNivelEducativo()));

        FormacionAcademicaId id = new FormacionAcademicaId(numFormacion, idUsuario);
        FormacionAcademica fa = formacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Formación académica no encontrada con numFormacion: " + numFormacion));

        fa.setInstitucion(request.getInstitucion());
        fa.setTitulo(request.getTitulo());
        fa.setNivelEducativo(ne);
        fa.setFechaInicio(request.getFechaInicio());
        fa.setFechaFin(request.getFechaFin());
        fa.setEnCurso(request.getEnCurso() != null && request.getEnCurso());

        formacionRepository.save(fa);
    }

    @Transactional
    public void eliminarFormacion(Integer idUsuario, Integer numFormacion) {
        buscarPostulante(idUsuario);
        FormacionAcademicaId id = new FormacionAcademicaId(numFormacion, idUsuario);
        if (formacionRepository.existsById(id)) {
            formacionRepository.deleteById(id);
        }
    }

    private FormacionAcademicaResponse mapearAFormacionResponse(FormacionAcademica fa) {
        return new FormacionAcademicaResponse(
                fa.getNumFormacion(),
                fa.getInstitucion(),
                fa.getTitulo(),
                fa.getNivelEducativo() != null ? fa.getNivelEducativo().getId() : null,
                fa.getNivelEducativo() != null ? fa.getNivelEducativo().getNombre() : null,
                fa.getFechaInicio(),
                fa.getFechaFin(),
                fa.getEnCurso()
        );
    }

    // --- CERTIFICACIONES ---

    @Transactional(readOnly = true)
    public List<CertificacionResponse> obtenerCertificaciones(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return certificacionRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearACertificacionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CertificacionResponse agregarCertificacion(Integer idUsuario, CertificacionRequest request) {
        buscarPostulante(idUsuario);
        TipoCertificacion tc = tipoCertificacionRepository.findById(request.getIdTipoCertificacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de certificación no encontrado con ID: " + request.getIdTipoCertificacion()));

        int nextNum = certificacionRepository.findByIdUsuario(idUsuario).stream()
                .mapToInt(Certificacion::getCodCert)
                .max()
                .orElse(0) + 1;

        Certificacion c = Certificacion.builder()
                .codCert(nextNum)
                .idUsuario(idUsuario)
                .codigoCertificacion(request.getCodigoCertificacion())
                .nombre(request.getNombre())
                .tipoCertificacion(tc)
                .institucion(request.getInstitucion())
                .fechaObtencion(request.getFechaObtencion())
                .archivoUrl(request.getArchivoUrl())
                .build();

        Certificacion guardada = certificacionRepository.save(c);
        return mapearACertificacionResponse(guardada);
    }

    @Transactional
    public void actualizarCertificacion(Integer idUsuario, Integer codCert, CertificacionRequest request) {
        buscarPostulante(idUsuario);
        TipoCertificacion tc = tipoCertificacionRepository.findById(request.getIdTipoCertificacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de certificación no encontrado con ID: " + request.getIdTipoCertificacion()));

        CertificacionId id = new CertificacionId(codCert, idUsuario);
        Certificacion c = certificacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Certificación no encontrada con codCert: " + codCert));

        c.setCodigoCertificacion(request.getCodigoCertificacion());
        c.setNombre(request.getNombre());
        c.setTipoCertificacion(tc);
        c.setInstitucion(request.getInstitucion());
        c.setFechaObtencion(request.getFechaObtencion());
        c.setArchivoUrl(request.getArchivoUrl());

        certificacionRepository.save(c);
    }

    @Transactional
    public void eliminarCertificacion(Integer idUsuario, Integer codCert) {
        buscarPostulante(idUsuario);
        CertificacionId id = new CertificacionId(codCert, idUsuario);
        if (certificacionRepository.existsById(id)) {
            certificacionRepository.deleteById(id);
        }
    }

    private CertificacionResponse mapearACertificacionResponse(Certificacion c) {
        return new CertificacionResponse(
                c.getCodCert(),
                c.getCodigoCertificacion(),
                c.getNombre(),
                c.getTipoCertificacion() != null ? c.getTipoCertificacion().getId() : null,
                c.getTipoCertificacion() != null ? c.getTipoCertificacion().getNombre() : null,
                c.getInstitucion(),
                c.getFechaObtencion(),
                c.getArchivoUrl()
        );
    }

    // --- LOGROS ---

    @Transactional(readOnly = true)
    public List<LogroResponse> obtenerLogros(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return logroRepository.findByIdUsuario(idUsuario).stream()
                .map(l -> new LogroResponse(l.getNumLogro(), l.getDescripcion(), l.getFecha()))
                .collect(Collectors.toList());
    }

    @Transactional
    public LogroResponse agregarLogro(Integer idUsuario, LogroRequest request) {
        buscarPostulante(idUsuario);

        int nextNum = logroRepository.findByIdUsuario(idUsuario).stream()
                .mapToInt(Logro::getNumLogro)
                .max()
                .orElse(0) + 1;

        Logro l = Logro.builder()
                .numLogro(nextNum)
                .idUsuario(idUsuario)
                .descripcion(request.getDescripcion())
                .fecha(request.getFecha())
                .build();

        Logro guardado = logroRepository.save(l);
        return new LogroResponse(guardado.getNumLogro(), guardado.getDescripcion(), guardado.getFecha());
    }

    @Transactional
    public void actualizarLogro(Integer idUsuario, Integer numLogro, LogroRequest request) {
        buscarPostulante(idUsuario);
        LogroId id = new LogroId(numLogro, idUsuario);
        Logro l = logroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Logro no encontrado con numLogro: " + numLogro));

        l.setDescripcion(request.getDescripcion());
        l.setFecha(request.getFecha());

        logroRepository.save(l);
    }

    @Transactional
    public void eliminarLogro(Integer idUsuario, Integer numLogro) {
        buscarPostulante(idUsuario);
        LogroId id = new LogroId(numLogro, idUsuario);
        if (logroRepository.existsById(id)) {
            logroRepository.deleteById(id);
        }
    }

    // --- RECOMENDACIONES ---

    @Transactional(readOnly = true)
    public List<RecomendacionResponse> obtenerRecomendaciones(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return recomendacionRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearARecomendacionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecomendacionResponse agregarRecomendacion(Integer idUsuario, RecomendacionRequest request) {
        buscarPostulante(idUsuario);
        TipoRecomendacion tr = tipoRecomendacionRepository.findById(request.getIdTipoRecomendacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de recomendación no encontrado con ID: " + request.getIdTipoRecomendacion()));

        int nextNum = recomendacionRepository.findByIdUsuario(idUsuario).stream()
                .mapToInt(Recomendacion::getNumRecomendacion)
                .max()
                .orElse(0) + 1;

        Recomendacion r = Recomendacion.builder()
                .numRecomendacion(nextNum)
                .idUsuario(idUsuario)
                .nombreContacto(request.getNombreContacto())
                .telefonoContacto(request.getTelefonoContacto())
                .tipoRecomendacion(tr)
                .build();

        Recomendacion guardada = recomendacionRepository.save(r);
        return mapearARecomendacionResponse(guardada);
    }

    @Transactional
    public void actualizarRecomendacion(Integer idUsuario, Integer numRecomendacion, RecomendacionRequest request) {
        buscarPostulante(idUsuario);
        TipoRecomendacion tr = tipoRecomendacionRepository.findById(request.getIdTipoRecomendacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de recomendación no encontrado con ID: " + request.getIdTipoRecomendacion()));

        RecomendacionId id = new RecomendacionId(numRecomendacion, idUsuario);
        Recomendacion r = recomendacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Recomendación no encontrada con numRecomendacion: " + numRecomendacion));

        r.setNombreContacto(request.getNombreContacto());
        r.setTelefonoContacto(request.getTelefonoContacto());
        r.setTipoRecomendacion(tr);

        recomendacionRepository.save(r);
    }

    @Transactional
    public void eliminarRecomendacion(Integer idUsuario, Integer numRecomendacion) {
        buscarPostulante(idUsuario);
        RecomendacionId id = new RecomendacionId(numRecomendacion, idUsuario);
        if (recomendacionRepository.existsById(id)) {
            recomendacionRepository.deleteById(id);
        }
    }

    private RecomendacionResponse mapearARecomendacionResponse(Recomendacion r) {
        return new RecomendacionResponse(
                r.getNumRecomendacion(),
                r.getNombreContacto(),
                r.getTelefonoContacto(),
                r.getTipoRecomendacion() != null ? r.getTipoRecomendacion().getId() : null,
                r.getTipoRecomendacion() != null ? r.getTipoRecomendacion().getNombre() : null
        );
    }

    // --- EVENTOS ---

    @Transactional(readOnly = true)
    public List<EventoResponse> obtenerEventos(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return eventoRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAEventoResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EventoResponse agregarEvento(Integer idUsuario, EventoRequest request) {
        buscarPostulante(idUsuario);
        TipoParticipacion tp = tipoParticipacionRepository.findById(request.getIdTipoParticipacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de participación no encontrado con ID: " + request.getIdTipoParticipacion()));
        Pais pais = paisRepository.findById(request.getIdPais())
                .orElseThrow(() -> new RecursoNoEncontradoException("País no encontrado con ID: " + request.getIdPais()));

        int nextNum = eventoRepository.findByIdUsuario(idUsuario).stream()
                .mapToInt(Evento::getNumEvento)
                .max()
                .orElse(0) + 1;

        Evento e = Evento.builder()
                .numEvento(nextNum)
                .idUsuario(idUsuario)
                .nombreEvento(request.getNombreEvento())
                .lugar(request.getLugar())
                .anfitrion(request.getAnfitrion())
                .fecha(request.getFecha())
                .tipoParticipacion(tp)
                .pais(pais)
                .build();

        Evento guardado = eventoRepository.save(e);
        return mapearAEventoResponse(guardado);
    }

    @Transactional
    public void actualizarEvento(Integer idUsuario, Integer numEvento, EventoRequest request) {
        buscarPostulante(idUsuario);
        TipoParticipacion tp = tipoParticipacionRepository.findById(request.getIdTipoParticipacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de participación no encontrado con ID: " + request.getIdTipoParticipacion()));
        Pais pais = paisRepository.findById(request.getIdPais())
                .orElseThrow(() -> new RecursoNoEncontradoException("País no encontrado con ID: " + request.getIdPais()));

        EventoId id = new EventoId(numEvento, idUsuario);
        Evento e = eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado con numEvento: " + numEvento));

        e.setNombreEvento(request.getNombreEvento());
        e.setLugar(request.getLugar());
        e.setAnfitrion(request.getAnfitrion());
        e.setFecha(request.getFecha());
        e.setTipoParticipacion(tp);
        e.setPais(pais);

        eventoRepository.save(e);
    }

    @Transactional
    public void eliminarEvento(Integer idUsuario, Integer numEvento) {
        buscarPostulante(idUsuario);
        EventoId id = new EventoId(numEvento, idUsuario);
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
        }
    }

    private EventoResponse mapearAEventoResponse(Evento e) {
        return new EventoResponse(
                e.getNumEvento(),
                e.getNombreEvento(),
                e.getLugar(),
                e.getAnfitrion(),
                e.getFecha(),
                e.getTipoParticipacion() != null ? e.getTipoParticipacion().getId() : null,
                e.getTipoParticipacion() != null ? e.getTipoParticipacion().getNombre() : null,
                e.getPais() != null ? e.getPais().getId() : null,
                e.getPais() != null ? e.getPais().getNombre() : null
        );
    }

    // --- PUBLICACIONES ---

    @Transactional(readOnly = true)
    public List<PublicacionResponse> obtenerPublicaciones(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return publicacionRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAPublicacionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PublicacionResponse agregarPublicacion(Integer idUsuario, PublicacionRequest request) {
        buscarPostulante(idUsuario);

        int nextNum = publicacionRepository.findByIdUsuario(idUsuario).stream()
                .mapToInt(Publicacion::getNumPublicacion)
                .max()
                .orElse(0) + 1;

        Publicacion p = Publicacion.builder()
                .numPublicacion(nextNum)
                .idUsuario(idUsuario)
                .titulo(request.getTitulo())
                .lugarPublicacion(request.getLugarPublicacion())
                .fecha(request.getFecha())
                .isbn(request.getIsbn())
                .edicion(request.getEdicion())
                .build();

        Publicacion guardada = publicacionRepository.save(p);
        return mapearAPublicacionResponse(guardada);
    }

    @Transactional
    public void actualizarPublicacion(Integer idUsuario, Integer numPublicacion, PublicacionRequest request) {
        buscarPostulante(idUsuario);
        PublicacionId id = new PublicacionId(numPublicacion, idUsuario);
        Publicacion p = publicacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Publicación no encontrada con numPublicacion: " + numPublicacion));

        p.setTitulo(request.getTitulo());
        p.setLugarPublicacion(request.getLugarPublicacion());
        p.setFecha(request.getFecha());
        p.setIsbn(request.getIsbn());
        p.setEdicion(request.getEdicion());

        publicacionRepository.save(p);
    }

    @Transactional
    public void eliminarPublicacion(Integer idUsuario, Integer numPublicacion) {
        buscarPostulante(idUsuario);
        PublicacionId id = new PublicacionId(numPublicacion, idUsuario);
        if (publicacionRepository.existsById(id)) {
            publicacionRepository.deleteById(id);
        }
    }

    private PublicacionResponse mapearAPublicacionResponse(Publicacion p) {
        return new PublicacionResponse(
                p.getNumPublicacion(),
                p.getTitulo(),
                p.getLugarPublicacion(),
                p.getFecha(),
                p.getIsbn(),
                p.getEdicion()
        );
    }

    // --- EXAMENES ---

    @Transactional(readOnly = true)
    public List<ExamenResponse> obtenerExamenes(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return examenRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAExamenResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExamenResponse agregarExamen(Integer idUsuario, ExamenRequest request) {
        buscarPostulante(idUsuario);

        int nextNum = examenRepository.findByIdUsuario(idUsuario).stream()
                .mapToInt(Examen::getNumExamen)
                .max()
                .orElse(0) + 1;

        Examen e = Examen.builder()
                .numExamen(nextNum)
                .idUsuario(idUsuario)
                .tipo(request.getTipo())
                .resultado(request.getResultado())
                .fecha(request.getFecha())
                .archivoUrl(request.getArchivoUrl())
                .build();

        Examen guardado = examenRepository.save(e);
        return mapearAExamenResponse(guardado);
    }

    @Transactional
    public void actualizarExamen(Integer idUsuario, Integer numExamen, ExamenRequest request) {
        buscarPostulante(idUsuario);
        ExamenId id = new ExamenId(numExamen, idUsuario);
        Examen e = examenRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Examen no encontrado con numExamen: " + numExamen));

        e.setTipo(request.getTipo());
        e.setResultado(request.getResultado());
        e.setFecha(request.getFecha());
        e.setArchivoUrl(request.getArchivoUrl());

        examenRepository.save(e);
    }

    @Transactional
    public void eliminarExamen(Integer idUsuario, Integer numExamen) {
        buscarPostulante(idUsuario);
        ExamenId id = new ExamenId(numExamen, idUsuario);
        if (examenRepository.existsById(id)) {
            examenRepository.deleteById(id);
        }
    }

    private ExamenResponse mapearAExamenResponse(Examen e) {
        return new ExamenResponse(
                e.getNumExamen(),
                e.getTipo(),
                e.getResultado(),
                e.getFecha(),
                e.getArchivoUrl()
        );
    }

    // --- HABILIDADES ---

    @Transactional(readOnly = true)
    public List<PostulanteHabilidadResponse> obtenerHabilidades(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return habilidadRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAHabilidadResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void agregarOActualizarHabilidad(Integer idUsuario, PostulanteHabilidadRequest request) {
        buscarPostulante(idUsuario);
        Habilidad h = rawHabilidadRepository.findById(request.getIdHabilidad())
                .orElseThrow(() -> new RecursoNoEncontradoException("Habilidad no encontrada con ID: " + request.getIdHabilidad()));
        NivelHabilidad nh = nivelHabilidadRepository.findById(request.getIdNivelHabilidad())
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel de habilidad no encontrado con ID: " + request.getIdNivelHabilidad()));

        PostulanteHabilidadId id = new PostulanteHabilidadId(idUsuario, request.getIdHabilidad());
        PostulanteHabilidad ph = habilidadRepository.findById(id)
                .orElseGet(() -> PostulanteHabilidad.builder()
                        .idUsuario(idUsuario)
                        .idHabilidad(request.getIdHabilidad())
                        .build());
        ph.setNivelHabilidad(nh);
        habilidadRepository.save(ph);
    }

    @Transactional
    public void eliminarHabilidad(Integer idUsuario, Integer idHabilidad) {
        buscarPostulante(idUsuario);
        PostulanteHabilidadId id = new PostulanteHabilidadId(idUsuario, idHabilidad);
        if (habilidadRepository.existsById(id)) {
            habilidadRepository.deleteById(id);
        }
    }

    private PostulanteHabilidadResponse mapearAHabilidadResponse(PostulanteHabilidad ph) {
        return new PostulanteHabilidadResponse(
                ph.getIdHabilidad(),
                ph.getHabilidad() != null ? ph.getHabilidad().getNombre() : null,
                ph.getNivelHabilidad() != null ? ph.getNivelHabilidad().getId() : null,
                ph.getNivelHabilidad() != null ? ph.getNivelHabilidad().getNombre() : null
        );
    }

    // --- IDIOMAS ---

    @Transactional(readOnly = true)
    public List<PostulanteIdiomaResponse> obtenerIdiomas(Integer idUsuario) {
        buscarPostulante(idUsuario);
        return idiomaRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAIdiomaResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void agregarOActualizarIdioma(Integer idUsuario, PostulanteIdiomaRequest request) {
        buscarPostulante(idUsuario);
        Idioma i = rawIdiomaRepository.findById(request.getIdIdioma())
                .orElseThrow(() -> new RecursoNoEncontradoException("Idioma no encontrado con ID: " + request.getIdIdioma()));
        NivelIdioma nl = nivelIdiomaRepository.findById(request.getIdNivelLectura())
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel de lectura no encontrado con ID: " + request.getIdNivelLectura()));
        NivelIdioma ne = nivelIdiomaRepository.findById(request.getIdNivelEscritura())
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel de escritura no encontrado con ID: " + request.getIdNivelEscritura()));
        NivelIdioma nc = nivelIdiomaRepository.findById(request.getIdNivelConversacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel de conversación no encontrado con ID: " + request.getIdNivelConversacion()));
        NivelIdioma nes = nivelIdiomaRepository.findById(request.getIdNivelEscucha())
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel de escucha no encontrado con ID: " + request.getIdNivelEscucha()));

        PostulanteIdiomaId id = new PostulanteIdiomaId(idUsuario, request.getIdIdioma());
        PostulanteIdioma pi = idiomaRepository.findById(id)
                .orElseGet(() -> PostulanteIdioma.builder()
                        .idUsuario(idUsuario)
                        .idIdioma(request.getIdIdioma())
                        .build());
        pi.setNivelLectura(nl);
        pi.setNivelEscritura(ne);
        pi.setNivelConversacion(nc);
        pi.setNivelEscucha(nes);
        idiomaRepository.save(pi);
    }

    @Transactional
    public void eliminarIdioma(Integer idUsuario, Integer idIdioma) {
        buscarPostulante(idUsuario);
        PostulanteIdiomaId id = new PostulanteIdiomaId(idUsuario, idIdioma);
        if (idiomaRepository.existsById(id)) {
            idiomaRepository.deleteById(id);
        }
    }

    private PostulanteIdiomaResponse mapearAIdiomaResponse(PostulanteIdioma pi) {
        return new PostulanteIdiomaResponse(
                pi.getIdIdioma(),
                pi.getIdioma() != null ? pi.getIdioma().getNombre() : null,
                pi.getNivelLectura() != null ? pi.getNivelLectura().getId() : null,
                pi.getNivelLectura() != null ? pi.getNivelLectura().getNombre() : null,
                pi.getNivelEscritura() != null ? pi.getNivelEscritura().getId() : null,
                pi.getNivelEscritura() != null ? pi.getNivelEscritura().getNombre() : null,
                pi.getNivelConversacion() != null ? pi.getNivelConversacion().getId() : null,
                pi.getNivelConversacion() != null ? pi.getNivelConversacion().getNombre() : null,
                pi.getNivelEscucha() != null ? pi.getNivelEscucha().getId() : null,
                pi.getNivelEscucha() != null ? pi.getNivelEscucha().getNombre() : null
        );
    }

    // --- CONSOLIDADO PERFIL COMPLETO (Opción C) ---

    @Transactional(readOnly = true)
    public PostulantePerfilResponse obtenerPerfilCompleto(Integer idUsuario) {
        Postulante p = buscarPostulante(idUsuario);

        DatosPersonalesResponse datosPersonales = mapearADatosPersonalesResponse(p);

        List<String> telefonos = telefonoRepository.findByIdUsuario(idUsuario).stream()
                .map(PostulanteTelefono::getTelefono)
                .collect(Collectors.toList());

        List<RedSocialResponse> redesSociales = redSocialRepository.findByIdUsuario(idUsuario).stream()
                .map(r -> new RedSocialResponse(
                        r.getIdTipoRedSocial(),
                        r.getTipoRedSocial() != null ? r.getTipoRedSocial().getNombre() : null,
                        r.getUrl()
                ))
                .collect(Collectors.toList());

        List<ExperienciaLaboralResponse> experiencias = experienciaRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAExperienciaResponse)
                .collect(Collectors.toList());

        List<FormacionAcademicaResponse> formaciones = formacionRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAFormacionResponse)
                .collect(Collectors.toList());

        List<CertificacionResponse> certificaciones = certificacionRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearACertificacionResponse)
                .collect(Collectors.toList());

        List<LogroResponse> logros = logroRepository.findByIdUsuario(idUsuario).stream()
                .map(l -> new LogroResponse(l.getNumLogro(), l.getDescripcion(), l.getFecha()))
                .collect(Collectors.toList());

        List<RecomendacionResponse> recomendaciones = recomendacionRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearARecomendacionResponse)
                .collect(Collectors.toList());

        List<EventoResponse> eventos = eventoRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAEventoResponse)
                .collect(Collectors.toList());

        List<PublicacionResponse> publicaciones = publicacionRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAPublicacionResponse)
                .collect(Collectors.toList());

        List<ExamenResponse> examenes = examenRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAExamenResponse)
                .collect(Collectors.toList());

        List<PostulanteHabilidadResponse> habilidades = habilidadRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAHabilidadResponse)
                .collect(Collectors.toList());

        List<PostulanteIdiomaResponse> idiomas = idiomaRepository.findByIdUsuario(idUsuario).stream()
                .map(this::mapearAIdiomaResponse)
                .collect(Collectors.toList());

        return new PostulantePerfilResponse(
                datosPersonales,
                telefonos,
                redesSociales,
                experiencias,
                formaciones,
                certificaciones,
                logros,
                recomendaciones,
                eventos,
                publicaciones,
                examenes,
                habilidades,
                idiomas
        );
    }
}
