package com.jobhorizon.backend.postulante.idioma;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulanteIdiomaRepository extends JpaRepository<PostulanteIdioma, PostulanteIdiomaId> {
    List<PostulanteIdioma> findByIdUsuario(Integer idUsuario);
}
