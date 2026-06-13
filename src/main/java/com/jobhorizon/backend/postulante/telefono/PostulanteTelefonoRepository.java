package com.jobhorizon.backend.postulante.telefono;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulanteTelefonoRepository extends JpaRepository<PostulanteTelefono, PostulanteTelefonoId> {
    List<PostulanteTelefono> findByIdUsuario(Integer idUsuario);
}
