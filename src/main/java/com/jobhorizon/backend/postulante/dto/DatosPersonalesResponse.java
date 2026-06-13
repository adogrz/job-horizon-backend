package com.jobhorizon.backend.postulante.dto;

import java.time.LocalDate;

/**
 * DTO de respuesta con los datos personales del postulante.
 * Incluye los nombres de catálogo resueltos para el frontend.
 */
public record DatosPersonalesResponse(
    Integer idUsuario,
    String correo,
    String nombres,
    String apellidos,
    LocalDate fechaNacimiento,
    String numDocumento,
    String nup,
    String nit,
    String direccion,
    String fotoUrl,
    Integer idGenero,
    String genero,
    Integer idTipoDocumento,
    String tipoDocumento,
    Integer idDistrito,
    String distrito,
    Integer idDepartamento,
    String departamento
) {}
