package com.jobhorizon.backend.postulacion;

import com.jobhorizon.backend.config.ApiResponse;
import com.jobhorizon.backend.empresa.EmpresaService;
import com.jobhorizon.backend.postulante.PostulanteService;
import com.jobhorizon.backend.postulacion.dto.CambioEstadoPostulacionRequest;
import com.jobhorizon.backend.postulacion.dto.PostulacionEmpresaResponse;
import com.jobhorizon.backend.postulacion.dto.PostulacionPostulanteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/postulaciones")
@RequiredArgsConstructor
@Tag(name = "Postulaciones", description = "Endpoints para gestionar el flujo de postulaciones a ofertas de trabajo.")
public class PostulacionController {

    private final PostulacionService postulacionService;
    private final PostulanteService postulanteService;
    private final EmpresaService empresaService;

    @Operation(summary = "Aplicar a una oferta de trabajo", description = "Permite al postulante autenticado aplicar a una oferta de trabajo activa. Requiere el privilegio APLICAR_OFERTA.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Postulación registrada con éxito."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "La oferta no está activa o ya se postuló previamente.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Postulante u Oferta no encontrada.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/ofertas/{idOferta}")
    @PreAuthorize("hasAuthority('APLICAR_OFERTA')")
    public ResponseEntity<ApiResponse<Void>> aplicarOferta(
            @PathVariable Integer idOferta,
            Principal principal) {
        Integer idPostulante = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulacionService.aplicarOferta(idPostulante, idOferta);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Postulación registrada con éxito"));
    }

    @Operation(summary = "Ver historial de postulaciones del postulante", description = "Lista de forma paginada y ordenada por fecha las ofertas a las que el postulante autenticado ha aplicado. Requiere APLICAR_OFERTA.")
    @GetMapping("/mi-historial")
    @PreAuthorize("hasAuthority('APLICAR_OFERTA')")
    public ResponseEntity<ApiResponse<Page<PostulacionPostulanteResponse>>> listarHistorialPostulante(
            @Parameter(description = "Número de página (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        Integer idPostulante = postulanteService.obtenerIdUsuarioPorCorreo(principal.getName());
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaAplicacion").descending());
        Page<PostulacionPostulanteResponse> historial = postulacionService.listarHistorialPostulante(idPostulante, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Historial de postulaciones obtenido con éxito", historial));
    }

    @Operation(summary = "Ver aspirantes de una oferta de trabajo", description = "Lista paginada de postulantes que han aplicado a una oferta específica. Valida propiedad de la oferta por la empresa autenticada. Requiere VER_ASPIRANTES.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de aspirantes obtenida con éxito."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "No tiene permisos sobre esta oferta (no es suya).", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Oferta no encontrada.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/ofertas/{idOferta}/aspirantes")
    @PreAuthorize("hasAuthority('VER_ASPIRANTES')")
    public ResponseEntity<ApiResponse<Page<PostulacionEmpresaResponse>>> listarAspirantesPorOferta(
            @PathVariable Integer idOferta,
            @Parameter(description = "Número de página (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        Integer idEmpresa = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaAplicacion").descending());
        Page<PostulacionEmpresaResponse> aspirantes = postulacionService.listarAspirantesPorOferta(idEmpresa, idOferta, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de aspirantes obtenida con éxito", aspirantes));
    }

    @Operation(summary = "Actualizar estado de una postulación", description = "Permite a la empresa cambiar el estado de la postulación de un candidato (ej: De PENDIENTE a CONTACTADO). Requiere VER_ASPIRANTES o GESTIONAR_OFERTAS.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Estado de postulación actualizado con éxito."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "No tiene permisos para modificar postulaciones de esta oferta.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Postulación, oferta o estado no encontrado.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PatchMapping("/ofertas/{idOferta}/aspirantes/{idPostulante}/estado")
    @PreAuthorize("hasAnyAuthority('VER_ASPIRANTES', 'GESTIONAR_OFERTAS')")
    public ResponseEntity<ApiResponse<Void>> actualizarEstadoAplicacion(
            @PathVariable Integer idOferta,
            @PathVariable Integer idPostulante,
            @Valid @RequestBody CambioEstadoPostulacionRequest request,
            Principal principal) {
        Integer idEmpresa = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        postulacionService.actualizarEstadoAplicacion(idEmpresa, idOferta, idPostulante, request.idEstadoAplicacion());
        return ResponseEntity.ok(new ApiResponse<>(true, "Estado de postulación actualizado con éxito"));
    }
}
