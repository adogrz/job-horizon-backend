package com.jobhorizon.backend.postulante.logro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogroRepository extends JpaRepository<Logro, LogroId> {
    List<Logro> findByIdUsuario(Integer idUsuario);
}
