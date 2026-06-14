package com.jobhorizon.backend.empresa.telefono;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaTelefonoRepository extends JpaRepository<EmpresaTelefono, EmpresaTelefonoId> {
    List<EmpresaTelefono> findByIdUsuario(Integer idUsuario);
}
