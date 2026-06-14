package com.jobhorizon.backend.empresa.dto;

import java.util.List;

public record EmpresaPerfilResponse(
    Integer idUsuario,
    String correo,
    String nombreComercial,
    String razonSocial,
    String nit,
    String sitioWeb,
    String descripcion,
    String logoUrl,
    Integer idDistrito,
    String distritoNombre,
    Integer idDepartamento,
    String departamentoNombre,
    List<String> telefonos
) {}
