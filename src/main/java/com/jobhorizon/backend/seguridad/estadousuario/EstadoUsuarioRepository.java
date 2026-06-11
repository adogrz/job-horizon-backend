package com.jobhorizon.backend.seguridad.estadousuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoUsuarioRepository extends JpaRepository<EstadoUsuario, Integer> {
    Optional<EstadoUsuario> findByNombre(String nombre);
}
