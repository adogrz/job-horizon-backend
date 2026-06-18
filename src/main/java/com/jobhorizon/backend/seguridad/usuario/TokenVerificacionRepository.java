package com.jobhorizon.backend.seguridad.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad TokenVerificacion.
 */
@Repository
public interface TokenVerificacionRepository extends JpaRepository<TokenVerificacion, Integer> {
    
    Optional<TokenVerificacion> findByToken(String token);
    
    void deleteByUsuario(Usuario usuario);
}
