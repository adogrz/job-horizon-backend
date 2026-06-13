package com.jobhorizon.backend.postulante.recomendacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecomendacionRepository extends JpaRepository<Recomendacion, RecomendacionId> {
    List<Recomendacion> findByIdUsuario(Integer idUsuario);
}
