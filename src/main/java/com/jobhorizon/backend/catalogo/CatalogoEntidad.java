package com.jobhorizon.backend.catalogo;

/**
 * Interfaz común para todas las entidades de catálogo del sistema.
 * Permite realizar operaciones CRUD de forma genérica.
 */
public interface CatalogoEntidad {
    Integer getId();
    void setId(Integer id);
    String getNombre();
    void setNombre(String nombre);
    Boolean getActivo();
    void setActivo(Boolean activo);
}
