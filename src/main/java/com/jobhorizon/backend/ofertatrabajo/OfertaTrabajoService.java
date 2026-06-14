package com.jobhorizon.backend.ofertatrabajo;

import com.jobhorizon.backend.correo.CorreoService;
import com.jobhorizon.backend.departamento.Departamento;
import com.jobhorizon.backend.departamento.DepartamentoRepository;
import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.distrito.DistritoRepository;
import com.jobhorizon.backend.empresa.Empresa;
import com.jobhorizon.backend.empresa.EmpresaRepository;
import com.jobhorizon.backend.estadooferta.EstadoOferta;
import com.jobhorizon.backend.estadooferta.EstadoOfertaRepository;
import com.jobhorizon.backend.habilidad.Habilidad;
import com.jobhorizon.backend.habilidad.HabilidadRepository;
import com.jobhorizon.backend.modalidad.Modalidad;
import com.jobhorizon.backend.modalidad.ModalidadRepository;
import com.jobhorizon.backend.niveleducativo.NivelEducativo;
import com.jobhorizon.backend.niveleducativo.NivelEducativoRepository;
import com.jobhorizon.backend.nivelhabilidad.NivelHabilidad;
import com.jobhorizon.backend.nivelhabilidad.NivelHabilidadRepository;
import com.jobhorizon.backend.nivelidioma.NivelIdioma;
import com.jobhorizon.backend.nivelidioma.NivelIdiomaRepository;
import com.jobhorizon.backend.idioma.Idioma;
import com.jobhorizon.backend.idioma.IdiomaRepository;
import com.jobhorizon.backend.tipocontrato.TipoContrato;
import com.jobhorizon.backend.tipocontrato.TipoContratoRepository;
import com.jobhorizon.backend.ofertatrabajo.dto.*;
import com.jobhorizon.backend.seguridad.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OfertaTrabajoService {

    private final OfertaTrabajoRepository ofertaTrabajoRepository;
    private final EmpresaRepository empresaRepository;
    private final TipoContratoRepository tipoContratoRepository;
    private final NivelEducativoRepository nivelEducativoRepository;
    private final ModalidadRepository modalidadRepository;
    private final EstadoOfertaRepository estadoOfertaRepository;
    private final DistritoRepository distritoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final HabilidadRepository habilidadRepository;
    private final NivelHabilidadRepository nivelHabilidadRepository;
    private final IdiomaRepository idiomaRepository;
    private final NivelIdiomaRepository nivelIdiomaRepository;
    private final PostulanteOfertaRepository postulanteOfertaRepository;
    private final CorreoService correoService;

    @Transactional
    public OfertaTrabajoResponse crearOferta(OfertaTrabajoRequest request, Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada"));

        validarDatosOferta(request);

        EstadoOferta estadoActiva = estadoOfertaRepository.findByNombre("ACTIVA")
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de oferta ACTIVA no configurado"));

        TipoContrato tipoContrato = tipoContratoRepository.findById(request.getIdTipoContrato())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de contrato no encontrado"));

        NivelEducativo nivelEducativo = nivelEducativoRepository.findById(request.getIdNivelEducativo())
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel educativo no encontrado"));

        Modalidad modalidad = modalidadRepository.findById(request.getIdModalidad())
                .orElseThrow(() -> new RecursoNoEncontradoException("Modalidad de trabajo no encontrada"));

        Distrito distrito = distritoRepository.findById(request.getIdDistrito())
                .orElseThrow(() -> new RecursoNoEncontradoException("Distrito no encontrado"));

        OfertaTrabajo oferta = OfertaTrabajo.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .salarioMin(request.getSalarioMin())
                .salarioMax(request.getSalarioMax())
                .numVacantes(request.getNumVacantes())
                .aniosExperienciaMinima(request.getAniosExperienciaMinima())
                .fechaPublicacion(LocalDateTime.now())
                .fechaVencimiento(request.getFechaVencimiento())
                .empresa(empresa)
                .tipoContrato(tipoContrato)
                .nivelEducativo(nivelEducativo)
                .modalidad(modalidad)
                .estadoOferta(estadoActiva)
                .distrito(distrito)
                .build();

        // Guardamos primero para tener el ID generado
        oferta = ofertaTrabajoRepository.save(oferta);

        // Habilidades e Idiomas
        oferta.setHabilidades(mapearHabilidades(request.getHabilidades(), oferta));
        oferta.setIdiomas(mapearIdiomas(request.getIdiomas(), oferta));

        oferta = ofertaTrabajoRepository.save(oferta);
        return mapToResponse(oferta);
    }

    @Transactional
    public OfertaTrabajoResponse actualizarOferta(Integer idOferta, OfertaTrabajoRequest request, Integer idEmpresa) {
        OfertaTrabajo oferta = ofertaTrabajoRepository.findById(idOferta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Oferta de trabajo no encontrada"));

        if (!oferta.getEmpresa().getId().equals(idEmpresa)) {
            throw new AccessDeniedException("No tiene permisos para modificar esta oferta");
        }

        validarDatosOferta(request);

        TipoContrato tipoContrato = tipoContratoRepository.findById(request.getIdTipoContrato())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de contrato no encontrado"));

        NivelEducativo nivelEducativo = nivelEducativoRepository.findById(request.getIdNivelEducativo())
                .orElseThrow(() -> new RecursoNoEncontradoException("Nivel educativo no encontrado"));

        Modalidad modalidad = modalidadRepository.findById(request.getIdModalidad())
                .orElseThrow(() -> new RecursoNoEncontradoException("Modalidad de trabajo no encontrada"));

        Distrito distrito = distritoRepository.findById(request.getIdDistrito())
                .orElseThrow(() -> new RecursoNoEncontradoException("Distrito no encontrado"));

        oferta.setTitulo(request.getTitulo());
        oferta.setDescripcion(request.getDescripcion());
        oferta.setSalarioMin(request.getSalarioMin());
        oferta.setSalarioMax(request.getSalarioMax());
        oferta.setNumVacantes(request.getNumVacantes());
        oferta.setAniosExperienciaMinima(request.getAniosExperienciaMinima());
        oferta.setFechaVencimiento(request.getFechaVencimiento());
        oferta.setTipoContrato(tipoContrato);
        oferta.setNivelEducativo(nivelEducativo);
        oferta.setModalidad(modalidad);
        oferta.setDistrito(distrito);

        // Limpiar habilidades e idiomas antiguos para que JPA los elimine de la base de datos (Orphan Removal)
        oferta.getHabilidades().clear();
        oferta.getIdiomas().clear();
        ofertaTrabajoRepository.saveAndFlush(oferta);

        // Habilidades e Idiomas nuevos
        oferta.getHabilidades().addAll(mapearHabilidades(request.getHabilidades(), oferta));
        oferta.getIdiomas().addAll(mapearIdiomas(request.getIdiomas(), oferta));

        oferta = ofertaTrabajoRepository.save(oferta);
        return mapToResponse(oferta);
    }

    @Transactional
    public OfertaTrabajoResponse cambiarEstado(Integer idOferta, Integer idEstado, Integer idEmpresa) {
        OfertaTrabajo oferta = ofertaTrabajoRepository.findById(idOferta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Oferta de trabajo no encontrada"));

        if (!oferta.getEmpresa().getId().equals(idEmpresa)) {
            throw new AccessDeniedException("No tiene permisos para modificar esta oferta");
        }

        EstadoOferta estado = estadoOfertaRepository.findById(idEstado)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de oferta no encontrado"));

        String estadoAnterior = oferta.getEstadoOferta().getNombre();
        oferta.setEstadoOferta(estado);
        oferta = ofertaTrabajoRepository.save(oferta);

        // Si cambia a CERRADA (o similar) y antes no lo estaba, notificamos a los postulantes actuales
        if (estado.getNombre().equalsIgnoreCase("CERRADA") && !estadoAnterior.equalsIgnoreCase("CERRADA")) {
            List<PostulanteOferta> postulaciones = postulanteOfertaRepository.findByOfertaId(idOferta);
            for (PostulanteOferta postulacion : postulaciones) {
                String correo = postulacion.getPostulante().getUsuario().getCorreo();
                correoService.enviarCorreoNotificacionOfertaCerrada(
                        correo,
                        oferta.getTitulo(),
                        oferta.getEmpresa().getNombreComercial()
                );
            }
        }

        return mapToResponse(oferta);
    }

    public OfertaTrabajoResponse obtenerPorId(Integer idOferta) {
        OfertaTrabajo oferta = ofertaTrabajoRepository.findById(idOferta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Oferta de trabajo no encontrada"));
        return mapToResponse(oferta);
    }

    public List<OfertaTrabajoResponse> listarPorEmpresa(Integer idEmpresa) {
        return ofertaTrabajoRepository.findByEmpresaId(idEmpresa).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Page<OfertaTrabajoResponse> buscarOfertas(
            String query,
            Integer idTipoContrato,
            Integer idModalidad,
            Integer idNivelEducativo,
            Integer idDistrito,
            BigDecimal salarioMin,
            Short aniosExperiencia,
            List<Integer> idHabilidades,
            List<Integer> idIdiomas,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaPublicacion").descending());

        Specification<OfertaTrabajo> spec = Specification.where(OfertaTrabajoSpecification.conEstado("ACTIVA"));

        if (query != null && !query.trim().isEmpty()) {
            spec = spec.and(OfertaTrabajoSpecification.conTituloODescripcion(query));
        }
        if (idTipoContrato != null) {
            spec = spec.and(OfertaTrabajoSpecification.conTipoContrato(idTipoContrato));
        }
        if (idModalidad != null) {
            spec = spec.and(OfertaTrabajoSpecification.conModalidad(idModalidad));
        }
        if (idNivelEducativo != null) {
            spec = spec.and(OfertaTrabajoSpecification.conNivelEducativo(idNivelEducativo));
        }
        if (idDistrito != null) {
            spec = spec.and(OfertaTrabajoSpecification.conDistrito(idDistrito));
        }
        if (salarioMin != null) {
            spec = spec.and(OfertaTrabajoSpecification.conSalarioMayorOIgualA(salarioMin));
        }
        if (aniosExperiencia != null) {
            spec = spec.and(OfertaTrabajoSpecification.conExperienciaMenorOIgualA(aniosExperiencia));
        }
        if (idHabilidades != null && !idHabilidades.isEmpty()) {
            spec = spec.and(OfertaTrabajoSpecification.conHabilidades(idHabilidades));
        }
        if (idIdiomas != null && !idIdiomas.isEmpty()) {
            spec = spec.and(OfertaTrabajoSpecification.conIdiomas(idIdiomas));
        }

        Page<OfertaTrabajo> ofertas = ofertaTrabajoRepository.findAll(spec, pageable);
        return ofertas.map(this::mapToResponse);
    }

    private void validarDatosOferta(OfertaTrabajoRequest request) {
        if (request.getSalarioMin() != null && request.getSalarioMax() != null) {
            if (request.getSalarioMax().compareTo(request.getSalarioMin()) < 0) {
                throw new IllegalArgumentException("El salario máximo no puede ser menor al salario mínimo");
            }
        }
        if (request.getFechaVencimiento() != null && !request.getFechaVencimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de vencimiento debe ser posterior al día de hoy");
        }
    }

    private OfertaTrabajoResponse mapToResponse(OfertaTrabajo oferta) {
        Set<OfertaHabilidadResponse> habilidades = oferta.getHabilidades().stream()
                .map(oh -> OfertaHabilidadResponse.builder()
                        .idHabilidad(oh.getHabilidad().getId())
                        .nombreHabilidad(oh.getHabilidad().getNombre())
                        .idNivelHabilidad(oh.getNivelHabilidad().getId())
                        .nombreNivelHabilidad(oh.getNivelHabilidad().getNombre())
                        .build())
                .collect(Collectors.toSet());

        Set<OfertaIdiomaResponse> idiomas = oferta.getIdiomas().stream()
                .map(oi -> OfertaIdiomaResponse.builder()
                        .idIdioma(oi.getIdioma().getId())
                        .nombreIdioma(oi.getIdioma().getNombre())
                        .idNivelIdioma(oi.getNivelIdioma().getId())
                        .nombreNivelIdioma(oi.getNivelIdioma().getNombre())
                        .build())
                .collect(Collectors.toSet());

        String dptoNombre = "";
        Distrito distrito = oferta.getDistrito();
        if (distrito != null && distrito.getIdDepartamento() != null) {
            dptoNombre = departamentoRepository.findById(distrito.getIdDepartamento())
                    .map(Departamento::getNombre)
                    .orElse("");
        }

        return OfertaTrabajoResponse.builder()
                .id(oferta.getId())
                .titulo(oferta.getTitulo())
                .descripcion(oferta.getDescripcion())
                .salarioMin(oferta.getSalarioMin())
                .salarioMax(oferta.getSalarioMax())
                .numVacantes(oferta.getNumVacantes())
                .aniosExperienciaMinima(oferta.getAniosExperienciaMinima())
                .fechaPublicacion(oferta.getFechaPublicacion())
                .fechaVencimiento(oferta.getFechaVencimiento())
                .idEmpresa(oferta.getEmpresa().getId())
                .nombreEmpresa(oferta.getEmpresa().getNombreComercial())
                .logoUrlEmpresa(oferta.getEmpresa().getLogoUrl())
                .idTipoContrato(oferta.getTipoContrato().getId())
                .nombreTipoContrato(oferta.getTipoContrato().getNombre())
                .idNivelEducativo(oferta.getNivelEducativo().getId())
                .nombreNivelEducativo(oferta.getNivelEducativo().getNombre())
                .idModalidad(oferta.getModalidad().getId())
                .nombreModalidad(oferta.getModalidad().getNombre())
                .idEstadoOferta(oferta.getEstadoOferta().getId())
                .nombreEstadoOferta(oferta.getEstadoOferta().getNombre())
                .idDistrito(oferta.getDistrito().getId())
                .nombreDistrito(oferta.getDistrito().getNombre())
                .idDepartamento(oferta.getDistrito().getIdDepartamento())
                .nombreDepartamento(dptoNombre)
                .habilidades(habilidades)
                .idiomas(idiomas)
                .build();
    }

    private Set<OfertaHabilidad> mapearHabilidades(Set<OfertaHabilidadRequest> requests, OfertaTrabajo oferta) {
        Set<OfertaHabilidad> habilidades = new HashSet<>();
        if (requests != null) {
            for (OfertaHabilidadRequest habRequest : requests) {
                Habilidad habilidad = habilidadRepository.findById(habRequest.getIdHabilidad())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Habilidad no encontrada: " + habRequest.getIdHabilidad()));
                NivelHabilidad nivelHabilidad = nivelHabilidadRepository.findById(habRequest.getIdNivelHabilidad())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Nivel de habilidad no encontrado: " + habRequest.getIdNivelHabilidad()));

                OfertaHabilidadId id = OfertaHabilidadId.builder()
                        .idOferta(oferta.getId())
                        .idHabilidad(habilidad.getId())
                        .build();

                habilidades.add(OfertaHabilidad.builder()
                        .id(id)
                        .oferta(oferta)
                        .habilidad(habilidad)
                        .nivelHabilidad(nivelHabilidad)
                        .build());
            }
        }
        return habilidades;
    }

    private Set<OfertaIdioma> mapearIdiomas(Set<OfertaIdiomaRequest> requests, OfertaTrabajo oferta) {
        Set<OfertaIdioma> idiomas = new HashSet<>();
        if (requests != null) {
            for (OfertaIdiomaRequest idRequest : requests) {
                Idioma idioma = idiomaRepository.findById(idRequest.getIdIdioma())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Idioma no encontrado: " + idRequest.getIdIdioma()));
                NivelIdioma nivelIdioma = nivelIdiomaRepository.findById(idRequest.getIdNivelIdioma())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Nivel de idioma no encontrado: " + idRequest.getIdNivelIdioma()));

                OfertaIdiomaId id = OfertaIdiomaId.builder()
                        .idOferta(oferta.getId())
                        .idIdioma(idioma.getId())
                        .build();

                idiomas.add(OfertaIdioma.builder()
                        .id(id)
                        .oferta(oferta)
                        .idioma(idioma)
                        .nivelIdioma(nivelIdioma)
                        .build());
            }
        }
        return idiomas;
    }
}
