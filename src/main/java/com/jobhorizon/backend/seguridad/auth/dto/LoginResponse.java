package com.jobhorizon.backend.seguridad.auth.dto;

import java.util.List;

public record LoginResponse(
    String token,
    String correo,
    List<String> roles,
    List<String> privilegios
) {
}
