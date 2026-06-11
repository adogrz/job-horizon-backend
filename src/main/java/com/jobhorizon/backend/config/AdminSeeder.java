package com.jobhorizon.backend.config;

import com.jobhorizon.backend.seguridad.administrador.Administrador;
import com.jobhorizon.backend.seguridad.estadousuario.EstadoUsuario;
import com.jobhorizon.backend.seguridad.estadousuario.EstadoUsuarioRepository;
import com.jobhorizon.backend.seguridad.rol.Rol;
import com.jobhorizon.backend.seguridad.rol.RolRepository;
import com.jobhorizon.backend.seguridad.usuario.Usuario;
import com.jobhorizon.backend.seguridad.usuario.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final EstadoUsuarioRepository estadoUsuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${ADMIN_EMAIL:#{null}}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:#{null}}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Verificando existencia de usuarios administradores...");

        // Check if any user has the ADMIN role
        Rol rolAdmin = rolRepository.findByNombre("ADMIN").orElse(null);
        if (rolAdmin == null) {
            log.warn("El rol ADMIN no existe en la base de datos. Saltando seeding del administrador.");
            return;
        }

        boolean existeAdmin = usuarioRepository.findAll().stream()
                .anyMatch(u -> u.getRoles() != null && u.getRoles().contains(rolAdmin));

        if (existeAdmin) {
            log.info("Ya existe al menos un usuario con el rol ADMIN. Saltando seeding del administrador.");
            return;
        }

        // Check environment variables
        if (adminEmail == null || adminEmail.trim().isEmpty() ||
            adminPassword == null || adminPassword.trim().isEmpty()) {
            log.warn("No se encontró ADMIN_EMAIL o ADMIN_PASSWORD en las variables de entorno. No se pudo crear el administrador inicial.");
            return;
        }

        EstadoUsuario estadoActivo = estadoUsuarioRepository.findByNombre("ACTIVO")
                .orElseThrow(() -> new RuntimeException("Estado ACTIVO no encontrado"));

        log.info("Creando usuario administrador inicial: {}", adminEmail);

        Usuario usuario = Usuario.builder()
                .correo(adminEmail)
                .passwordHash(passwordEncoder.encode(adminPassword))
                .intentosFallidos((byte) 0)
                .estadoUsuario(estadoActivo)
                .roles(Set.of(rolAdmin))
                .build();
        usuario = usuarioRepository.save(usuario);

        Administrador administrador = Administrador.builder()
                .usuario(usuario)
                .build();
        entityManager.persist(administrador);

        log.info("Administrador inicial creado exitosamente con ID: {}", usuario.getId());
    }
}
