package com.jobhorizon.backend.ofertatrabajo;

import com.jobhorizon.backend.config.ApiResponse;
import com.jobhorizon.backend.empresa.EmpresaService;
import com.jobhorizon.backend.ofertatrabajo.dto.OfertaTrabajoRequest;
import com.jobhorizon.backend.ofertatrabajo.dto.OfertaTrabajoResponse;
import com.jobhorizon.backend.ofertatrabajo.matching.AspiranteMatchResponse;
import com.jobhorizon.backend.ofertatrabajo.matching.MatchingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/ofertas")
@RequiredArgsConstructor
@Tag(name = "Ofertas de Trabajo", description = "Endpoints para la publicación, edición y búsqueda de ofertas de trabajo.")
public class OfertaTrabajoController {

    private final OfertaTrabajoService ofertaTrabajoService;
    private final EmpresaService empresaService;
    private final MatchingService matchingService;

    @Operation(summary = "Crear oferta de trabajo", description = "Permite a la empresa autenticada publicar una nueva oferta de trabajo vacante. Requiere GESTIONAR_PERFIL.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Oferta de trabajo creada con éxito."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    @PreAuthorize("hasAuthority('GESTIONAR_PERFIL')")
    public ResponseEntity<ApiResponse<OfertaTrabajoResponse>> crearOferta(
            Principal principal,
            @Valid @RequestBody OfertaTrabajoRequest request) {
        Integer idEmpresa = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        OfertaTrabajoResponse response = ofertaTrabajoService.crearOferta(request, idEmpresa);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Oferta de trabajo creada con éxito", response));
    }

    @Operation(summary = "Actualizar oferta de trabajo", description = "Permite a la empresa modificar una oferta de trabajo de su propiedad. Requiere GESTIONAR_PERFIL.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Oferta de trabajo actualizada con éxito."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "No tiene permisos para modificar esta oferta.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Oferta no encontrada.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('GESTIONAR_PERFIL')")
    public ResponseEntity<ApiResponse<OfertaTrabajoResponse>> actualizarOferta(
            @PathVariable Integer id,
            Principal principal,
            @Valid @RequestBody OfertaTrabajoRequest request) {
        Integer idEmpresa = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        OfertaTrabajoResponse response = ofertaTrabajoService.actualizarOferta(id, request, idEmpresa);
        return ResponseEntity.ok(new ApiResponse<>(true, "Oferta de trabajo actualizada con éxito", response));
    }

    @Operation(summary = "Cambiar estado de la oferta", description = "Permite cambiar el estado de la oferta (ej. de ACTIVA a CERRADA). Si se cierra, se envía notificación de proceso finalizado a los postulantes por correo. Requiere GESTIONAR_PERFIL.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Estado cambiado con éxito."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Oferta o estado no encontrado.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAuthority('GESTIONAR_PERFIL')")
    public ResponseEntity<ApiResponse<OfertaTrabajoResponse>> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam Integer idEstado,
            Principal principal) {
        Integer idEmpresa = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        OfertaTrabajoResponse response = ofertaTrabajoService.cambiarEstado(id, idEstado, idEmpresa);
        return ResponseEntity.ok(new ApiResponse<>(true, "Estado de la oferta actualizado con éxito", response));
    }

    @Operation(summary = "Obtener oferta por ID", description = "Retorna la información completa de una oferta de trabajo. Endpoint público.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Detalles de la oferta de trabajo obtenidos."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Oferta no encontrada.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OfertaTrabajoResponse>> obtenerPorId(@PathVariable Integer id) {
        OfertaTrabajoResponse response = ofertaTrabajoService.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Oferta obtenida con éxito", response));
    }

    @Operation(summary = "Listar ofertas de una empresa", description = "Retorna la lista de todas las ofertas de trabajo publicadas por una empresa específica. Endpoint público.")
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<ApiResponse<List<OfertaTrabajoResponse>>> listarPorEmpresa(@PathVariable Integer idEmpresa) {
        List<OfertaTrabajoResponse> response = ofertaTrabajoService.listarPorEmpresa(idEmpresa);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ofertas de la empresa obtenidas con éxito", response));
    }

    @Operation(summary = "Buscar ofertas de trabajo (Filtros y Paginación)", description = "Endpoint público para postulantes y visitantes. Retorna ofertas en estado 'ACTIVA' filtradas y paginadas. Los filtros son acumulativos.")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<OfertaTrabajoResponse>>> buscarOfertas(
            @Parameter(description = "Texto de búsqueda para título o descripción") @RequestParam(required = false) String query,
            @Parameter(description = "ID del tipo de contrato") @RequestParam(required = false) Integer idTipoContrato,
            @Parameter(description = "ID de la modalidad de trabajo") @RequestParam(required = false) Integer idModalidad,
            @Parameter(description = "ID del nivel educativo mínimo") @RequestParam(required = false) Integer idNivelEducativo,
            @Parameter(description = "ID del distrito") @RequestParam(required = false) Integer idDistrito,
            @Parameter(description = "Salario mínimo esperado") @RequestParam(required = false) BigDecimal salarioMin,
            @Parameter(description = "Años máximos de experiencia requeridos por la vacante") @RequestParam(required = false) Short aniosExperiencia,
            @Parameter(description = "Lista de IDs de habilidades requeridas (al menos una)") @RequestParam(required = false) List<Integer> habilidades,
            @Parameter(description = "Lista de IDs de idiomas requeridos (al menos uno)") @RequestParam(required = false) List<Integer> idiomas,
            @Parameter(description = "Número de página (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size
    ) {
        Page<OfertaTrabajoResponse> response = ofertaTrabajoService.buscarOfertas(
                query, idTipoContrato, idModalidad, idNivelEducativo, idDistrito,
                salarioMin, aniosExperiencia, habilidades, idiomas, page, size
        );
        return ResponseEntity.ok(new ApiResponse<>(true, "Búsqueda de ofertas realizada con éxito", response));
    }

    @Operation(
            summary = "Motor de matching de aspirantes para una oferta",
            description = """
                    Ejecuta el algoritmo de matching de la base de datos (sp_ObtenerAspirantes + fn_PuntajeMatching)
                    para una oferta específica y devuelve los candidatos potenciales que aún NO han aplicado,
                    ordenados por puntaje de matching (0–100). Requiere VER_ASPIRANTES y ser propietario de la oferta.

                    **Filtros disponibles:**
                    - `idDepartamento` — delimitado a la BD, filtro más eficiente.
                    - `nombre` — búsqueda parcial (case-insensitive) sobre el nombre completo del aspirante.
                    - `puntajeMin` — puntaje mínimo de matching (0.00–100.00).

                    **Ordenamiento:** `sortBy` acepta `puntajeMatching` (default), `nombre`, `departamento`, `habilidades`.
                    `sortDir` acepta `desc` (default) o `asc`.
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de aspirantes con matching obtenida con éxito."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "No tiene permisos sobre esta oferta.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Oferta no encontrada.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{idOferta}/aspirantes-match")
    @PreAuthorize("hasAuthority('VER_ASPIRANTES')")
    public ResponseEntity<ApiResponse<Page<AspiranteMatchResponse>>> buscarAspirantesMatch(
            @PathVariable Integer idOferta,
            @Parameter(description = "ID del departamento para filtrar aspirantes (delegado al SP)") @RequestParam(required = false) Integer idDepartamento,
            @Parameter(description = "Búsqueda parcial por nombre o apellido del aspirante") @RequestParam(required = false) String nombre,
            @Parameter(description = "Puntaje mínimo de matching (0.00–100.00)") @RequestParam(required = false) BigDecimal puntajeMin,
            @Parameter(description = "Campo de ordenamiento: puntajeMatching (default), nombre, departamento, habilidades") @RequestParam(defaultValue = "puntajeMatching") String sortBy,
            @Parameter(description = "Dirección de ordenamiento: desc (default) o asc") @RequestParam(defaultValue = "desc") String sortDir,
            @Parameter(description = "Número de página (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página (máximo 100)") @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        Integer idEmpresa = empresaService.obtenerIdUsuarioPorCorreo(principal.getName());
        Page<AspiranteMatchResponse> resultado = matchingService.buscarAspirantesParaOferta(
                idOferta, idEmpresa, idDepartamento, nombre,
                puntajeMin, sortBy, sortDir, page, size
        );
        return ResponseEntity.ok(new ApiResponse<>(true, "Motor de matching ejecutado con éxito", resultado));
    }
}
