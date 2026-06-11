package com.jobhorizon.backend.seguridad.usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UsuarioRepositoryImpl implements CustomUsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void registrarIntentoFallido(Integer idUsuario) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_RegistrarIntentoFallido");
        query.registerStoredProcedureParameter("IdUsuario", Integer.class, ParameterMode.IN);
        query.setParameter("IdUsuario", idUsuario);
        query.execute();
    }

    @Override
    @Transactional
    public void resetearIntentosFallidos(Integer idUsuario) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_ResetearIntentosFallidos");
        query.registerStoredProcedureParameter("IdUsuario", Integer.class, ParameterMode.IN);
        query.setParameter("IdUsuario", idUsuario);
        query.execute();
    }

    @Override
    @Transactional
    public void generarTokenDesbloqueo(Integer idUsuario, String token) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GenerarTokenDesbloqueo");
        query.registerStoredProcedureParameter("IdUsuario", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Token", String.class, ParameterMode.IN);
        query.setParameter("IdUsuario", idUsuario);
        query.setParameter("Token", token);
        query.execute();
    }

    @Override
    @Transactional
    public int desbloquearUsuario(String token) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_DesbloquearUsuario");
        query.registerStoredProcedureParameter("Token", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Resultado", Integer.class, ParameterMode.OUT);
        query.setParameter("Token", token);
        query.execute();
        return (Integer) query.getOutputParameterValue("Resultado");
    }

    @Override
    public List<String> obtenerNombresPrivilegios(Integer idUsuario) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_ObtenerPrivilegiosUsuario");
        query.registerStoredProcedureParameter("IdUsuario", Integer.class, ParameterMode.IN);
        query.setParameter("IdUsuario", idUsuario);
        query.execute();
        
        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();
        
        return rows.stream()
                .map(row -> (String) row[1]) // Index 1 is 'Nombre' from SELECT DISTINCT p.IdPrivilegio, p.Nombre...
                .toList();
    }
}
