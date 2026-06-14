package com.jobhorizon.backend.ofertatrabajo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfertaTrabajoRepository extends JpaRepository<OfertaTrabajo, Integer>, JpaSpecificationExecutor<OfertaTrabajo> {
    List<OfertaTrabajo> findByEmpresaId(Integer idEmpresa);
}
