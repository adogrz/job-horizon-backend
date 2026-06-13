package com.jobhorizon.backend.postulante.examen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, ExamenId> {
    List<Examen> findByIdUsuario(Integer idUsuario);
}
