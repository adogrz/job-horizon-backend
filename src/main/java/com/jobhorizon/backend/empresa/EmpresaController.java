package com.jobhorizon.backend.empresa;

import com.jobhorizon.backend.empresa.dto.EmpresaPerfilRequest;
import com.jobhorizon.backend.empresa.dto.EmpresaPerfilResponse;
import com.jobhorizon.backend.empresa.dto.LogoRequest;
import com.jobhorizon.backend.empresa.telefono.dto.EmpresaTelefonoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller para la gestión del perfil de la empresa.
 *
 * <p>Todos los endpoints requieren autenticación JWT con el privilegio {@code GESTIONAR_PERFIL}.
 * La empresa autenticada se identifica automáticamente por su token JWT — no se necesita enviar ningún ID de usuario.</p>
 */
@RestController
@RequestMapping("/empresa/perfil")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTIONAR_PERFIL')")
@Tag(
        name = "Perfil de la Empresa",
        description = "Módulo para la visualización y actualización de la información comercial e institucional de la empresa y la gestión de sus teléfonos."
)
public class EmpresaController {

    private final EmpresaService empresaService;

    @Operation(
            summary = "Obtener perfil de la empresa",
            description = "Retorna la información del perfil completo de la empresa autenticada, incluyendo la lista de números telefónicos registrados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil de la empresa obtenido con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "No tiene el privilegio GESTIONAR_PERFIL.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "La empresa no existe para el usuario autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<EmpresaPerfilResponse>> obtenerPerfil(Principal principal) {
        Integer idUsuario = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        EmpresaPerfilResponse perfil = empresaService.obtenerPerfil(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Perfil obtenido con éxito", perfil));
    }

    @Operation(
            summary = "Actualizar perfil de la empresa",
            description = "Actualiza los datos institucionales y comerciales de la empresa autenticada. Valida que el NIT sea único y el formato sea válido."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado con éxito."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. formato NIT incorrecto).", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "409", description = "El NIT ya está registrado por otra empresa.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<EmpresaPerfilResponse>> actualizarPerfil(
            Principal principal,
            @Valid @RequestBody EmpresaPerfilRequest request) {
        Integer idUsuario = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        EmpresaPerfilResponse perfilActualizado = empresaService.actualizarPerfil(idUsuario, request);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Perfil actualizado con éxito", perfilActualizado));
    }

    @Operation(
            summary = "Actualizar logo de la empresa",
            description = "Actualiza únicamente la URL del logo de la empresa. Si se envía null o cadena vacía, se elimina físicamente de R2."
    )
    @PatchMapping("/logo")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> actualizarLogo(
            Principal principal,
            @RequestBody LogoRequest request) {
        Integer idUsuario = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        empresaService.actualizarLogo(idUsuario, request.logoUrl());
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Logo actualizado con éxito"));
    }

    @Operation(
            summary = "Agregar teléfono de la empresa",
            description = "Registra un nuevo número de teléfono para la empresa. Valida que el formato sea ####-#### o +############."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Teléfono registrado con éxito."),
            @ApiResponse(responseCode = "400", description = "Formato de teléfono inválido o datos incorrectos.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PostMapping("/telefonos")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> agregarTelefono(
            Principal principal,
            @Valid @RequestBody EmpresaTelefonoRequest request) {
        Integer idUsuario = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        empresaService.agregarTelefono(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new com.jobhorizon.backend.config.ApiResponse<>(true, "Teléfono registrado con éxito"));
    }

    @Operation(
            summary = "Eliminar teléfono de la empresa",
            description = "Elimina un número de teléfono específico del perfil de la empresa."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teléfono eliminado con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/telefonos/{telefono}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarTelefono(
            Principal principal,
            @Parameter(description = "Número de teléfono a eliminar", example = "2222-2222")
            @PathVariable String telefono) {
        Integer idUsuario = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        empresaService.eliminarTelefono(idUsuario, telefono);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Teléfono eliminado con éxito"));
    }

    @Operation(summary = "Eliminar cuenta de la empresa", description = "Elimina permanentemente el perfil de la empresa y su usuario asociado, incluyendo su logo subido a R2.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta de la empresa eliminada con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "Sin privilegios.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> eliminarCuenta(Principal principal) {
        Integer idUsuario = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        empresaService.eliminarPerfilYUsuario(idUsuario);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Cuenta de la empresa eliminada con éxito"));
    }
}
