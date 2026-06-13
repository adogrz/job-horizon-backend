package com.jobhorizon.backend.seguridad.usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller para la administración de usuarios del sistema.
 *
 * <p>Todos los endpoints requieren autenticación JWT y el privilegio {@code GESTIONAR_USUARIOS}.</p>
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTIONAR_USUARIOS')")
@Tag(name = "Gestión de Usuarios", description = "Administración de usuarios del sistema. Requiere autenticación JWT con el privilegio GESTIONAR_USUARIOS (rol de administrador).")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(
            summary = "Listar usuarios",
            description = """
                    Retorna una página de usuarios del sistema. Soporta paginación y ordenamiento mediante parámetros de query.
                    
                    **Parámetros de paginación (opcionales):**
                    - `page`: Número de página (inicia en 0, por defecto 0)
                    - `size`: Cantidad de registros por página (por defecto 20)
                    - `sort`: Campo y dirección de ordenamiento (ej. `sort=correo,asc`)
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "No tiene el privilegio GESTIONAR_USUARIOS.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Page<Usuario>>> listar(Pageable pageable) {
        Page<Usuario> usuarios = usuarioService.listarUsuarios(pageable);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Usuarios listados con éxito", usuarios));
    }

    @Operation(summary = "Obtener usuario por ID", description = "Retorna los datos de un usuario específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario obtenido con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "No tiene el privilegio GESTIONAR_USUARIOS.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el usuario con ese ID.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Usuario>> obtener(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerUsuario(id);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Usuario obtenido con éxito", usuario));
    }

    @Operation(summary = "Dar de baja a un usuario", description = "Desactiva la cuenta de un usuario. El usuario no podrá iniciar sesión mientras esté de baja.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario dado de baja con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "No tiene el privilegio GESTIONAR_USUARIOS.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el usuario con ese ID.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PatchMapping("/{id}/dar-de-baja")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> darDeBaja(
            @Parameter(description = "ID del usuario a dar de baja", example = "1")
            @PathVariable Integer id) {
        usuarioService.darDeBaja(id);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Usuario dado de baja con éxito"));
    }

    @Operation(summary = "Activar un usuario", description = "Reactiva la cuenta de un usuario que estaba dado de baja.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario activado con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "No tiene el privilegio GESTIONAR_USUARIOS.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el usuario con ese ID.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PatchMapping("/{id}/activar")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> activar(
            @Parameter(description = "ID del usuario a activar", example = "1")
            @PathVariable Integer id) {
        usuarioService.activar(id);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Usuario activado con éxito"));
    }

    @Operation(
            summary = "Asignar roles a un usuario",
            description = """
                    Reemplaza **todos** los roles actuales del usuario con el conjunto de roles proporcionado.
                    
                    El cuerpo debe ser un arreglo de IDs de rol (números enteros). Por ejemplo: `[1, 3]`
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles asignados con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "No tiene el privilegio GESTIONAR_USUARIOS.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el usuario con ese ID.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @PutMapping("/{id}/roles")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> asignarRoles(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Conjunto de IDs de rol a asignar. Reemplaza los roles existentes.", required = true)
            @RequestBody Set<Integer> roleIds) {
        usuarioService.asignarRoles(id, roleIds);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Roles asignados con éxito"));
    }

    @Operation(summary = "Revocar un rol a un usuario", description = "Elimina un rol específico de un usuario sin afectar sus otros roles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol revocado con éxito."),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "403", description = "No tiene el privilegio GESTIONAR_USUARIOS.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró el usuario o el rol.", content = @Content(schema = @Schema(implementation = com.jobhorizon.backend.config.ApiResponse.class)))
    })
    @DeleteMapping("/{id}/roles/{rolId}")
    public ResponseEntity<com.jobhorizon.backend.config.ApiResponse<Void>> revocarRol(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "ID del rol a revocar", example = "2")
            @PathVariable Integer rolId) {
        usuarioService.revocarRol(id, rolId);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Rol revocado con éxito"));
    }
}
