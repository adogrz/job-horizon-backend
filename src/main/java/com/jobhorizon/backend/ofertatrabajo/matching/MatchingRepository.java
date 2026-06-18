package com.jobhorizon.backend.ofertatrabajo.matching;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio para ejecutar el stored procedure de matching de aspirantes.
 *
 * <p>Ejecuta {@code sp_ObtenerAspirantes} y retorna la lista completa
 * de aspirantes candidatos para la oferta dada, ordenados por
 * {@code PuntajeMatching DESC} (orden delegado a la base de datos).</p>
 */
@Slf4j
@Repository
public class MatchingRepository {

    private static final String SP_OBTENER_ASPIRANTES = "sp_ObtenerAspirantes";

    // Índices de columna según el SELECT del SP (0-based)
    private static final int COL_ID_USUARIO            = 0;
    private static final int COL_NOMBRES               = 1;
    private static final int COL_APELLIDOS             = 2;
    private static final int COL_NOMBRE_COMPLETO       = 3;
    private static final int COL_CORREO                = 4;
    private static final int COL_DEPARTAMENTO          = 5;
    private static final int COL_DISTRITO              = 6;
    private static final int COL_HABILIDADES_COINCIDEN = 7;
    private static final int COL_HABILIDADES_REQ       = 8;
    private static final int COL_IDIOMAS_COINCIDEN     = 9;
    private static final int COL_IDIOMAS_REQ           = 10;
    private static final int COL_PUNTAJE_MATCHING      = 11;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Ejecuta {@code sp_ObtenerAspirantes} y retorna la lista completa
     * de aspirantes candidatos para la oferta dada.
     *
     * @param idOferta       ID de la oferta de trabajo
     * @param idDepartamento departamento para filtrar; {@code null} si no aplica
     * @return lista de aspirantes con su puntaje de matching
     */
    public List<AspiranteMatchResponse> obtenerAspirantesParaOferta(
            Integer idOferta,
            Integer idDepartamento) {

        log.debug("Ejecutando {} con idOferta={}, idDepartamento={}",
                SP_OBTENER_ASPIRANTES, idOferta, idDepartamento);

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery(SP_OBTENER_ASPIRANTES);

        query.registerStoredProcedureParameter("IdOferta", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdDepartamento", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("SoloDisponibles", Boolean.class, ParameterMode.IN);

        query.setParameter("IdOferta", idOferta);
        query.setParameter("IdDepartamento", idDepartamento);
        query.setParameter("SoloDisponibles", true);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(this::mapRowToResponse)
                .toList();
    }

    private AspiranteMatchResponse mapRowToResponse(Object[] row) {
        return new AspiranteMatchResponse(
                (Integer)    row[COL_ID_USUARIO],
                (String)     row[COL_NOMBRES],
                (String)     row[COL_APELLIDOS],
                (String)     row[COL_NOMBRE_COMPLETO],
                (String)     row[COL_CORREO],
                (String)     row[COL_DEPARTAMENTO],
                (String)     row[COL_DISTRITO],
                (Integer)    row[COL_HABILIDADES_COINCIDEN],
                (Integer)    row[COL_HABILIDADES_REQ],
                (Integer)    row[COL_IDIOMAS_COINCIDEN],
                (Integer)    row[COL_IDIOMAS_REQ],
                (BigDecimal) row[COL_PUNTAJE_MATCHING]
        );
    }
}
