package com.jobhorizon.backend.seguridad.auth;

import com.jobhorizon.backend.seguridad.auth.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller que gestiona el registro de usuarios, inicio de sesión y recuperación de cuentas.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro, inicio de sesión y desbloqueo de cuentas. Todos son de acceso público.")
@SecurityRequirements
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica a un usuario con su correo y contraseña. Retorna un token JWT que debe usarse en los demás endpoints protegidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso. Se retorna el token JWT."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (correo o contraseña vacíos).", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "La cuenta está bloqueada o inactiva.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Inicio de sesión exitoso", response));
    }

    @Operation(
            summary = "Registrar postulante",
            description = "Crea una cuenta nueva para un postulante. Retorna un token JWT para iniciar sesión de inmediato."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Postulante registrado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "409", description = "El correo electrónico ya está registrado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/registro/postulante")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<LoginResponse>> registrarPostulante(@Valid @RequestBody RegistroPostulanteRequest request) {
        LoginResponse response = authService.registrarPostulante(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Registro de postulante exitoso", response));
    }

    @Operation(
            summary = "Registrar empresa",
            description = "Crea una cuenta nueva para una empresa. Retorna un token JWT para iniciar sesión de inmediato."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa registrada exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "409", description = "El correo electrónico ya está registrado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/registro/empresa")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<LoginResponse>> registrarEmpresa(@Valid @RequestBody RegistroEmpresaRequest request) {
        LoginResponse response = authService.registrarEmpresa(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Registro de empresa exitoso", response));
    }

    @Operation(
            summary = "Solicitar desbloqueo de cuenta",
            description = "Envía un correo electrónico con un enlace/token para desbloquear la cuenta del usuario. Útil cuando la cuenta fue bloqueada por intentos fallidos de inicio de sesión."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Correo de desbloqueo enviado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Correo inválido o vacío.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un usuario con ese correo.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/solicitar-desbloqueo")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> solicitarDesbloqueo(@Valid @RequestBody SolicitudDesbloqueoRequest request) {
        authService.solicitarDesbloqueo(request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Se ha enviado un correo con instrucciones para desbloquear la cuenta"));
    }

    @Operation(
            summary = "Desbloquear cuenta",
            description = "Usa el token recibido por correo electrónico para desbloquear la cuenta del usuario."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta desbloqueada exitosamente."),
            @ApiResponse(responseCode = "400", description = "El token es inválido o ha expirado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/desbloquear")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> desbloquear(@Valid @RequestBody DesbloqueoRequest request) {
        authService.desbloquear(request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Cuenta desbloqueada exitosamente"));
    }
}
