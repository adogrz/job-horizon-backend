package com.jobhorizon.backend.tipoparticipacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoParticipacionRepository extends JpaRepository<TipoParticipacion, Integer> {
}
