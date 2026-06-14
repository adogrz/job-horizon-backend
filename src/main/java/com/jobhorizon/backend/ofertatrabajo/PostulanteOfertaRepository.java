package com.jobhorizon.backend.ofertatrabajo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulanteOfertaRepository extends JpaRepository<PostulanteOferta, PostulanteOfertaId> {
    List<PostulanteOferta> findByOfertaId(Integer idOferta);
    boolean existsByOfertaId(Integer idOferta);
}
