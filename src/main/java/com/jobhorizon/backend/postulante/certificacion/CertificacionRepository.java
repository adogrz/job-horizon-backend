package com.jobhorizon.backend.postulante.certificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificacionRepository extends JpaRepository<Certificacion, CertificacionId> {
    List<Certificacion> findByIdUsuario(Integer idUsuario);
}
