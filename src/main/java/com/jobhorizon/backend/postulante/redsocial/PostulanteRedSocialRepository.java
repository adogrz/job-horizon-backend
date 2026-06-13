package com.jobhorizon.backend.postulante.redsocial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulanteRedSocialRepository extends JpaRepository<PostulanteRedSocial, PostulanteRedSocialId> {
    List<PostulanteRedSocial> findByIdUsuario(Integer idUsuario);
}
