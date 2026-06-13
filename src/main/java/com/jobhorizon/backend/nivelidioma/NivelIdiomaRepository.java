package com.jobhorizon.backend.nivelidioma;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelIdiomaRepository extends JpaRepository<NivelIdioma, Integer> {
}
