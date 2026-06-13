package com.jobhorizon.backend.tiporecomendacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRecomendacionRepository extends JpaRepository<TipoRecomendacion, Integer> {
}
