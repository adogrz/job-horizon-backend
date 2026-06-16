package com.jobhorizon.backend.ofertatrabajo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulanteOfertaRepository extends JpaRepository<PostulanteOferta, PostulanteOfertaId> {
    List<PostulanteOferta> findByOfertaId(Integer idOferta);
    Page<PostulanteOferta> findByOfertaId(Integer idOferta, Pageable pageable);
    Page<PostulanteOferta> findByPostulanteId(Integer idUsuario, Pageable pageable);
    boolean existsByOfertaId(Integer idOferta);
}
