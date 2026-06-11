package com.jobhorizon.backend.postulante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulanteRepository extends JpaRepository<Postulante, Integer> {
}
