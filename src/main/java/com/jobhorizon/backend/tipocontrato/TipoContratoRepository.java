package com.jobhorizon.backend.tipocontrato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoContratoRepository extends JpaRepository<TipoContrato, Integer> {
}
