package com.jobhorizon.backend.estadooferta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoOfertaRepository extends JpaRepository<EstadoOferta, Integer> {
    Optional<EstadoOferta> findByNombre(String nombre);
}
