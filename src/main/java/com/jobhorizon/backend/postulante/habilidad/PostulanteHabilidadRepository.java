package com.jobhorizon.backend.postulante.habilidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulanteHabilidadRepository extends JpaRepository<PostulanteHabilidad, PostulanteHabilidadId> {
    List<PostulanteHabilidad> findByIdUsuario(Integer idUsuario);
}
