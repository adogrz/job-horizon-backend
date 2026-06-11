package com.jobhorizon.backend.seguridad.usuario;

import com.jobhorizon.backend.config.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTIONAR_USUARIOS')")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Usuario>>> listar(Pageable pageable) {
        Page<Usuario> usuarios = usuarioService.listarUsuarios(pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios listados con éxito", usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> obtener(@PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerUsuario(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario obtenido con éxito", usuario));
    }

    @PatchMapping("/{id}/dar-de-baja")
    public ResponseEntity<ApiResponse<Void>> darDeBaja(@PathVariable Integer id) {
        usuarioService.darDeBaja(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario dado de baja con éxito"));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<ApiResponse<Void>> activar(@PathVariable Integer id) {
        usuarioService.activar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario activado con éxito"));
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<Void>> asignarRoles(@PathVariable Integer id, @RequestBody Set<Integer> roleIds) {
        usuarioService.asignarRoles(id, roleIds);
        return ResponseEntity.ok(new ApiResponse<>(true, "Roles asignados con éxito"));
    }

    @DeleteMapping("/{id}/roles/{rolId}")
    public ResponseEntity<ApiResponse<Void>> revocarRol(@PathVariable Integer id, @PathVariable Integer rolId) {
        usuarioService.revocarRol(id, rolId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Rol revocado con éxito"));
    }
}
