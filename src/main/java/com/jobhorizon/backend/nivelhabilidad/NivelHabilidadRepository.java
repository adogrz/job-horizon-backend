package com.jobhorizon.backend.nivelhabilidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelHabilidadRepository extends JpaRepository<NivelHabilidad, Integer> {
}
