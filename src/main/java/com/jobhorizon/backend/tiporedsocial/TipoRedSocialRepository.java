package com.jobhorizon.backend.tiporedsocial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRedSocialRepository extends JpaRepository<TipoRedSocial, Integer> {
}
