package com.jobhorizon.backend.seguridad.usuario;

import java.util.List;

public interface CustomUsuarioRepository {
    void registrarIntentoFallido(Integer idUsuario);
    void resetearIntentosFallidos(Integer idUsuario);
    void generarTokenDesbloqueo(Integer idUsuario, String token);
    int desbloquearUsuario(String token);
    List<String> obtenerNombresPrivilegios(Integer idUsuario);
}
