package com.jobhorizon.backend.seguridad.usuario;

import com.jobhorizon.backend.seguridad.estadousuario.EstadoUsuario;
import com.jobhorizon.backend.seguridad.estadousuario.EstadoUsuarioRepository;
import com.jobhorizon.backend.seguridad.exception.RecursoNoEncontradoException;
import com.jobhorizon.backend.seguridad.rol.Rol;
import com.jobhorizon.backend.seguridad.rol.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EstadoUsuarioRepository estadoUsuarioRepository;
    private final RolRepository rolRepository;

    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario obtenerUsuario(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public void darDeBaja(Integer id) {
        Usuario usuario = obtenerUsuario(id);
        EstadoUsuario estadoInactivo = estadoUsuarioRepository.findByNombre("INACTIVO")
                .orElseThrow(() -> new RuntimeException("Estado INACTIVO no encontrado"));
        usuario.setEstadoUsuario(estadoInactivo);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void activar(Integer id) {
        Usuario usuario = obtenerUsuario(id);
        EstadoUsuario estadoActivo = estadoUsuarioRepository.findByNombre("ACTIVO")
                .orElseThrow(() -> new RuntimeException("Estado ACTIVO no encontrado"));
        usuario.setEstadoUsuario(estadoActivo);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void asignarRoles(Integer id, Set<Integer> roleIds) {
        Usuario usuario = obtenerUsuario(id);
        Set<Rol> nuevosRoles = new HashSet<>();
        for (Integer roleId : roleIds) {
            Rol rol = rolRepository.findById(roleId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + roleId));
            nuevosRoles.add(rol);
        }
        
        Set<Rol> rolesActuales = usuario.getRoles();
        if (rolesActuales == null) {
            rolesActuales = new HashSet<>();
            usuario.setRoles(rolesActuales);
        }

        // Determinar cuáles quitar y cuáles agregar para que Hibernate no haga delete completo
        Set<Rol> aQuitar = new HashSet<>(rolesActuales);
        aQuitar.removeAll(nuevosRoles);

        Set<Rol> aAgregar = new HashSet<>(nuevosRoles);
        aAgregar.removeAll(rolesActuales);

        rolesActuales.removeAll(aQuitar);
        rolesActuales.addAll(aAgregar);

        usuarioRepository.save(usuario);
    }

    @Transactional
    public void revocarRol(Integer id, Integer rolId) {
        Usuario usuario = obtenerUsuario(id);
        Rol rolARevocar = rolRepository.findById(rolId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + rolId));
        
        Set<Rol> roles = usuario.getRoles();
        if (roles != null && roles.contains(rolARevocar)) {
            roles.remove(rolARevocar);
            usuarioRepository.save(usuario);
        }
    }
}
