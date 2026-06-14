package com.jobhorizon.backend.ofertatrabajo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoAplicacionRepository extends JpaRepository<EstadoAplicacion, Integer> {
    Optional<EstadoAplicacion> findByNombre(String nombre);
}
