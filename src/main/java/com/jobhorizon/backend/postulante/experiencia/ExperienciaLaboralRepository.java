package com.jobhorizon.backend.postulante.experiencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienciaLaboralRepository extends JpaRepository<ExperienciaLaboral, ExperienciaLaboralId> {
    List<ExperienciaLaboral> findByIdUsuario(Integer idUsuario);
}
