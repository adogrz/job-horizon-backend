package com.jobhorizon.backend.empresa;

import com.jobhorizon.backend.catalogo.CatalogoService;
import com.jobhorizon.backend.departamento.Departamento;
import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.distrito.DistritoRepository;
import com.jobhorizon.backend.empresa.dto.EmpresaPerfilRequest;
import com.jobhorizon.backend.empresa.dto.EmpresaPerfilResponse;
import com.jobhorizon.backend.empresa.telefono.EmpresaTelefono;
import com.jobhorizon.backend.empresa.telefono.EmpresaTelefonoId;
import com.jobhorizon.backend.empresa.telefono.EmpresaTelefonoRepository;
import com.jobhorizon.backend.empresa.telefono.dto.EmpresaTelefonoRequest;
import com.jobhorizon.backend.seguridad.exception.CorreoDuplicadoException;
import com.jobhorizon.backend.seguridad.exception.RecursoNoEncontradoException;
import com.jobhorizon.backend.seguridad.usuario.Usuario;
import com.jobhorizon.backend.seguridad.usuario.UsuarioRepository;
import com.jobhorizon.backend.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaTelefonoRepository telefonoRepository;
    private final DistritoRepository distritoRepository;
    private final CatalogoService catalogoService;
    private final StorageService storageService;

    private Empresa buscarEmpresa(Integer idUsuario) {
        return empresaRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada con ID: " + idUsuario));
    }

    @Transactional(readOnly = true)
    public Integer obtenerIdUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(Usuario::getId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con correo: " + correo));
    }

    @Transactional(readOnly = true)
    public EmpresaPerfilResponse obtenerPerfil(Integer idUsuario) {
        Empresa empresa = buscarEmpresa(idUsuario);
        return mapearAEmpresaPerfilResponse(empresa);
    }

    @Transactional
    public EmpresaPerfilResponse actualizarPerfil(Integer idUsuario, EmpresaPerfilRequest request) {
        Empresa empresa = buscarEmpresa(idUsuario);

        // Validar si el NIT ya está registrado por otra empresa
        empresaRepository.findByNit(request.getNit())
                .ifPresent(existente -> {
                    if (!existente.getId().equals(idUsuario)) {
                        throw new CorreoDuplicadoException("El NIT ya está registrado por otra empresa");
                    }
                });

        Distrito distrito = distritoRepository.findById(request.getIdDistrito())
                .orElseThrow(() -> new RecursoNoEncontradoException("Distrito no encontrado con ID: " + request.getIdDistrito()));

        empresa.setNombreComercial(request.getNombreComercial());
        empresa.setRazonSocial(request.getRazonSocial());
        empresa.setNit(request.getNit());
        empresa.setSitioWeb(request.getSitioWeb());
        empresa.setDescripcion(request.getDescripcion());

        // Sincronización de logo
        if ((request.getLogoUrl() == null || request.getLogoUrl().isBlank()) && empresa.getLogoUrl() != null && !empresa.getLogoUrl().isBlank()) {
            storageService.deleteFile(storageService.extraerObjectKey(empresa.getLogoUrl()));
        }
        empresa.setLogoUrl(request.getLogoUrl());

        empresa.setDistrito(distrito);

        empresaRepository.save(empresa);
        return mapearAEmpresaPerfilResponse(empresa);
    }

    @Transactional
    public void actualizarLogo(Integer idUsuario, String logoUrl) {
        Empresa empresa = buscarEmpresa(idUsuario);
        String oldLogoUrl = empresa.getLogoUrl();
        if ((logoUrl == null || logoUrl.isBlank()) && oldLogoUrl != null && !oldLogoUrl.isBlank()) {
            storageService.deleteFile(storageService.extraerObjectKey(oldLogoUrl));
        }
        empresa.setLogoUrl(logoUrl);
        empresaRepository.save(empresa);
    }

    @Transactional(readOnly = true)
    public List<String> obtenerTelefonos(Integer idUsuario) {
        buscarEmpresa(idUsuario);
        return telefonoRepository.findByIdUsuario(idUsuario).stream()
                .map(EmpresaTelefono::getTelefono)
                .collect(Collectors.toList());
    }

    @Transactional
    public void agregarTelefono(Integer idUsuario, EmpresaTelefonoRequest request) {
        buscarEmpresa(idUsuario);
        EmpresaTelefonoId id = new EmpresaTelefonoId(idUsuario, request.getTelefono());
        if (telefonoRepository.existsById(id)) {
            return; // Ya existe
        }
        EmpresaTelefono telefono = EmpresaTelefono.builder()
                .idUsuario(idUsuario)
                .telefono(request.getTelefono())
                .build();
        telefonoRepository.save(telefono);
    }

    @Transactional
    public void eliminarTelefono(Integer idUsuario, String telefono) {
        buscarEmpresa(idUsuario);
        EmpresaTelefonoId id = new EmpresaTelefonoId(idUsuario, telefono);
        if (telefonoRepository.existsById(id)) {
            telefonoRepository.deleteById(id);
        }
    }

    private EmpresaPerfilResponse mapearAEmpresaPerfilResponse(Empresa e) {
        String deptoNombre = null;
        Integer idDepto = null;
        if (e.getDistrito() != null && e.getDistrito().getIdDepartamento() != null) {
            idDepto = e.getDistrito().getIdDepartamento();
            deptoNombre = catalogoService.findById(Departamento.class, idDepto)
                    .map(Departamento::getNombre)
                    .orElse(null);
        }

        List<String> telefonos = telefonoRepository.findByIdUsuario(e.getId()).stream()
                .map(EmpresaTelefono::getTelefono)
                .collect(Collectors.toList());

        return new EmpresaPerfilResponse(
                e.getId(),
                e.getUsuario() != null ? e.getUsuario().getCorreo() : null,
                e.getNombreComercial(),
                e.getRazonSocial(),
                e.getNit(),
                e.getSitioWeb(),
                e.getDescripcion(),
                e.getLogoUrl(),
                e.getDistrito() != null ? e.getDistrito().getId() : null,
                e.getDistrito() != null ? e.getDistrito().getNombre() : null,
                idDepto,
                deptoNombre,
                telefonos
        );
    }

    @Transactional
    public void eliminarPerfilYUsuario(Integer idUsuario) {
        Empresa empresa = buscarEmpresa(idUsuario);
        
        // 1. Eliminar logo de R2
        if (empresa.getLogoUrl() != null && !empresa.getLogoUrl().isBlank()) {
            String logoKey = storageService.extraerObjectKey(empresa.getLogoUrl());
            storageService.deleteFile(logoKey);
        }

        // 2. Eliminar el usuario (cascada elimina la empresa y sus teléfonos en BD)
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));
        usuarioRepository.delete(usuario);
    }
}
