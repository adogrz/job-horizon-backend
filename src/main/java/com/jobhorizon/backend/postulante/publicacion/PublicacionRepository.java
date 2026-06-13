package com.jobhorizon.backend.postulante.publicacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, PublicacionId> {
    List<Publicacion> findByIdUsuario(Integer idUsuario);
}
