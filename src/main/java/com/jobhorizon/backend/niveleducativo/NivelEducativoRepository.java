package com.jobhorizon.backend.niveleducativo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelEducativoRepository extends JpaRepository<NivelEducativo, Integer> {
}
