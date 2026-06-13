package com.jobhorizon.backend.postulante.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, EventoId> {
    List<Evento> findByIdUsuario(Integer idUsuario);
}
