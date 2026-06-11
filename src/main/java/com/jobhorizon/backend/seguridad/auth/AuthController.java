package com.jobhorizon.backend.seguridad.auth;

import com.jobhorizon.backend.config.ApiResponse;
import com.jobhorizon.backend.seguridad.auth.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Inicio de sesión exitoso", response));
    }

    @PostMapping("/registro/postulante")
    public ResponseEntity<ApiResponse<LoginResponse>> registrarPostulante(@Valid @RequestBody RegistroPostulanteRequest request) {
        LoginResponse response = authService.registrarPostulante(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Registro de postulante exitoso", response));
    }

    @PostMapping("/registro/empresa")
    public ResponseEntity<ApiResponse<LoginResponse>> registrarEmpresa(@Valid @RequestBody RegistroEmpresaRequest request) {
        LoginResponse response = authService.registrarEmpresa(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Registro de empresa exitoso", response));
    }

    @PostMapping("/solicitar-desbloqueo")
    public ResponseEntity<ApiResponse<Void>> solicitarDesbloqueo(@Valid @RequestBody SolicitudDesbloqueoRequest request) {
        authService.solicitarDesbloqueo(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Se ha enviado un correo con instrucciones para desbloquear la cuenta"));
    }

    @PostMapping("/desbloquear")
    public ResponseEntity<ApiResponse<Void>> desbloquear(@Valid @RequestBody DesbloqueoRequest request) {
        authService.desbloquear(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cuenta desbloqueada exitosamente"));
    }
}
