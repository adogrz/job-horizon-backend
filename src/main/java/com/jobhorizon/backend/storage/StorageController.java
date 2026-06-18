package com.jobhorizon.backend.storage;

import com.jobhorizon.backend.config.ApiResponse;
import com.jobhorizon.backend.storage.dto.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controlador REST para gestionar la subida y operaciones de almacenamiento.
 * Requiere autenticación y el privilegio GESTIONAR_PERFIL.
 */
@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTIONAR_PERFIL')")
@Tag(name = "Almacenamiento (Storage)", description = "End-points para la gestión y generación de URLs pre-firmadas para Cloudflare R2.")
public class StorageController {

    private final StorageService storageService;
    private final com.jobhorizon.backend.seguridad.usuario.UsuarioRepository usuarioRepository;

    @Operation(
            summary = "Obtener URL pre-firmada",
            description = "Genera una URL pre-firmada para subir archivos (PUT) directamente a R2 y la URL pública final que se guardará en la base de datos."
    )
    @GetMapping("/presigned-url")
    public ResponseEntity<ApiResponse<PresignedUrlResponse>> obtenerPresignedUrl(
            Principal principal,
            @RequestParam("tipo") String tipo,
            @RequestParam("extension") String extension,
            @RequestParam("contentType") String contentType
    ) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String objectKey;
        if ("FOTO_PERFIL".equalsIgnoreCase(tipo)) {
            objectKey = storageService.generarLlavePostulanteFoto(usuario.getId(), extension);
        } else if ("LOGO_EMPRESA".equalsIgnoreCase(tipo)) {
            objectKey = storageService.generarLlaveEmpresaLogo(usuario.getId(), extension);
        } else if ("CV".equalsIgnoreCase(tipo)) {
            objectKey = storageService.generarLlavePostulanteCv(usuario.getId(), extension);
        } else {
            throw new IllegalArgumentException("Tipo de archivo no soportado. Valores válidos: FOTO_PERFIL, LOGO_EMPRESA, CV");
        }

        String uploadUrl = storageService.generarPresignedPutUrl(objectKey, contentType);
        String publicUrl = storageService.obtenerPublicUrl(objectKey);

        PresignedUrlResponse response = new PresignedUrlResponse(uploadUrl, publicUrl);
        return ResponseEntity.ok(new ApiResponse<>(true, "URL pre-firmada generada con éxito", response));
    }
}
