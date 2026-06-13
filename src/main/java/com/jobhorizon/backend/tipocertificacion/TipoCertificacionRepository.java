package com.jobhorizon.backend.tipocertificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCertificacionRepository extends JpaRepository<TipoCertificacion, Integer> {
}
