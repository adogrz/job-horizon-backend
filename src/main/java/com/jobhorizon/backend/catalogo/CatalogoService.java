package com.jobhorizon.backend.catalogo;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio genérico para realizar operaciones de consulta en catálogos del sistema.
 * Reemplaza la necesidad de inyectar múltiples interfaces Repository redundantes.
 */
@Service
@Transactional(readOnly = true)
public class CatalogoService {
    private final EntityManager entityManager;

    public CatalogoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Recupera todos los registros de una entidad de catálogo determinada.
     *
     * @param clazz la clase de la entidad del catálogo
     * @param <T>   el tipo de la entidad
     * @return lista de todos los registros del catálogo
     */
    public <T> List<T> findAll(Class<T> clazz) {
        String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e";
        if (CatalogoEntidad.class.isAssignableFrom(clazz)) {
            jpql += " WHERE e.activo = true";
        }
        return entityManager.createQuery(jpql, clazz).getResultList();
    }

    /**
     * Guarda o actualiza un registro de catálogo.
     *
     * @param entidad la entidad del catálogo a guardar
     * @param <T>     el tipo de la entidad
     * @return la entidad guardada
     */
    @Transactional
    public <T extends CatalogoEntidad> T save(T entidad) {
        if (entidad.getId() == null) {
            entityManager.persist(entidad);
            return entidad;
        } else {
            return entityManager.merge(entidad);
        }
    }

    /**
     * Realiza un soft delete de un catálogo desactivándolo.
     *
     * @param clazz la clase de la entidad
     * @param id    el identificador
     * @param <T>   el tipo de la entidad
     */
    @Transactional
    public <T extends CatalogoEntidad> void softDelete(Class<T> clazz, Object id) {
        Optional.ofNullable(entityManager.find(clazz, id)).ifPresent(entidad -> {
            entidad.setActivo(false);
            entityManager.merge(entidad);
        });
    }

    /**
     * Recupera todos los registros de una entidad de catálogo determinada,
     * sin aplicar filtros de estado activo (para uso administrativo).
     *
     * @param clazz la clase de la entidad
     * @param <T>   el tipo de la entidad
     * @return lista de todos los registros del catálogo
     */
    public <T> List<T> findAllAdmin(Class<T> clazz) {
        String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e";
        return entityManager.createQuery(jpql, clazz).getResultList();
    }

    /**
     * Recupera un registro de catálogo por su identificador.
     *
     * @param clazz la clase de la entidad del catálogo
     * @param id    el identificador del registro
     * @param <T>   el tipo de la entidad
     * @param <ID>  el tipo del identificador
     * @return un Optional con la entidad encontrada, o vacío si no existe
     */
    public <T, ID> Optional<T> findById(Class<T> clazz, ID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    /**
     * Recupera un registro de catálogo buscando por su propiedad 'nombre'.
     *
     * @param clazz  la clase de la entidad del catálogo
     * @param nombre el nombre a buscar
     * @param <T>    el tipo de la entidad
     * @return un Optional con la entidad encontrada, o vacío si no existe
     */
    public <T> Optional<T> findByNombre(Class<T> clazz, String nombre) {
        if (nombre == null) {
            return Optional.empty();
        }
        try {
            String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.nombre = :nombre";
            return Optional.of(entityManager.createQuery(jpql, clazz)
                    .setParameter("nombre", nombre)
                    .getSingleResult());
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }
}
