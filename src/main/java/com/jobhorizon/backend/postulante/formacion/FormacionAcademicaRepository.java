package com.jobhorizon.backend.postulante.formacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormacionAcademicaRepository extends JpaRepository<FormacionAcademica, FormacionAcademicaId> {
    List<FormacionAcademica> findByIdUsuario(Integer idUsuario);
}
