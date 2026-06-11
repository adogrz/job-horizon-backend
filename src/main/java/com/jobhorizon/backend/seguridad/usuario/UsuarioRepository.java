package com.jobhorizon.backend.seguridad.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, CustomUsuarioRepository {
    
    @EntityGraph(attributePaths = {"estadoUsuario", "roles"})
    Optional<Usuario> findByCorreo(String correo);
    
    boolean existsByCorreo(String correo);

    @Override
    @EntityGraph(attributePaths = {"estadoUsuario", "roles"})
    Page<Usuario> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"estadoUsuario", "roles"})
    Optional<Usuario> findById(Integer id);
}
