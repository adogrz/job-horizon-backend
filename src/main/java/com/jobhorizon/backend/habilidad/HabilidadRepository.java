package com.jobhorizon.backend.habilidad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabilidadRepository extends JpaRepository<Habilidad, Integer> {
    List<Habilidad> findByCategoriaHabilidadId(Integer idCategoriaHabilidad);
}
