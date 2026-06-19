package com.jobhorizon.backend.catalogo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhorizon.backend.departamento.Departamento;
import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.genero.Genero;
import com.jobhorizon.backend.habilidad.CategoriaHabilidad;
import com.jobhorizon.backend.habilidad.Habilidad;
import com.jobhorizon.backend.idioma.Idioma;
import com.jobhorizon.backend.niveleducativo.NivelEducativo;
import com.jobhorizon.backend.nivelhabilidad.NivelHabilidad;
import com.jobhorizon.backend.nivelidioma.NivelIdioma;
import com.jobhorizon.backend.pais.Pais;
import com.jobhorizon.backend.tipocertificacion.TipoCertificacion;
import com.jobhorizon.backend.tipodocumento.TipoDocumento;
import com.jobhorizon.backend.tipoparticipacion.TipoParticipacion;
import com.jobhorizon.backend.tiporecomendacion.TipoRecomendacion;
import com.jobhorizon.backend.tiporedsocial.TipoRedSocial;
import com.jobhorizon.backend.tipocontrato.TipoContrato;
import com.jobhorizon.backend.modalidad.Modalidad;
import com.jobhorizon.backend.seguridad.exception.RecursoNoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/catalogos")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTIONAR_CATALOGOS')")
@Tag(name = "Administración de Catálogos", description = "Endpoints para que los administradores gestionen (CRUD) los catálogos del sistema.")
public class AdminCatalogoController {

    private final CatalogoService catalogoService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Map<String, Class<? extends CatalogoEntidad>> REGISTRY = new HashMap<>();

    static {
        REGISTRY.put("departamentos", Departamento.class);
        REGISTRY.put("distritos", Distrito.class);
        REGISTRY.put("generos", Genero.class);
        REGISTRY.put("categorias-habilidad", CategoriaHabilidad.class);
        REGISTRY.put("habilidades", Habilidad.class);
        REGISTRY.put("idiomas", Idioma.class);
        REGISTRY.put("niveles-educativos", NivelEducativo.class);
        REGISTRY.put("niveles-habilidad", NivelHabilidad.class);
        REGISTRY.put("niveles-idioma", NivelIdioma.class);
        REGISTRY.put("paises", Pais.class);
        REGISTRY.put("tipos-certificacion", TipoCertificacion.class);
        REGISTRY.put("tipos-documento", TipoDocumento.class);
        REGISTRY.put("tipos-participacion", TipoParticipacion.class);
        REGISTRY.put("tipos-recomendacion", TipoRecomendacion.class);
        REGISTRY.put("tipos-red-social", TipoRedSocial.class);
        REGISTRY.put("tipos-contrato", TipoContrato.class);
        REGISTRY.put("modalidades", Modalidad.class);
    }

    private Class<? extends CatalogoEntidad> resolveClass(String tipo) {
        Class<? extends CatalogoEntidad> clazz = REGISTRY.get(tipo.toLowerCase());
        if (clazz == null) {
            throw new RecursoNoEncontradoException("Catálogo '" + tipo + "' no soportado o no encontrado.");
        }
        return clazz;
    }

    @Operation(summary = "Listar todos los elementos de un catálogo", description = "Retorna todos los elementos de un catálogo específico, incluyendo los inactivos.")
    @GetMapping("/{tipo}")
    public ResponseEntity<?> listar(@PathVariable String tipo) {
        Class<? extends CatalogoEntidad> clazz = resolveClass(tipo);
        List<?> all = catalogoService.findAllAdmin(clazz);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Catálogo obtenido con éxito", all));
    }

    @Operation(summary = "Crear elemento de catálogo", description = "Crea un nuevo elemento en el catálogo especificado.")
    @PostMapping("/{tipo}")
    public ResponseEntity<?> crear(@PathVariable String tipo, @RequestBody String body) throws Exception {
        Class<? extends CatalogoEntidad> clazz = resolveClass(tipo);
        CatalogoEntidad entidad = objectMapper.readValue(body, clazz);
        entidad.setActivo(true);
        CatalogoEntidad guardado = catalogoService.save(entidad);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Elemento creado con éxito", guardado));
    }

    @Operation(summary = "Actualizar elemento de catálogo", description = "Actualiza un elemento existente en el catálogo especificado.")
    @PutMapping("/{tipo}/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String tipo, @PathVariable Integer id, @RequestBody String body) throws Exception {
        Class<? extends CatalogoEntidad> clazz = resolveClass(tipo);
        CatalogoEntidad existente = catalogoService.findById(clazz, id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Elemento de catálogo no encontrado con ID: " + id));

        objectMapper.readerForUpdating(existente).readValue(body);
        existente.setId(id); // Garantizar que no se altere el ID
        
        CatalogoEntidad guardado = catalogoService.save(existente);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Elemento actualizado con éxito", guardado));
    }

    @Operation(summary = "Desactivar elemento de catálogo (Soft Delete)", description = "Marca un elemento de catálogo como inactivo.")
    @DeleteMapping("/{tipo}/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String tipo, @PathVariable Integer id) {
        Class<? extends CatalogoEntidad> clazz = resolveClass(tipo);
        catalogoService.softDelete(clazz, id);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Elemento desactivado con éxito", null));
    }

    @Operation(summary = "Reactivar elemento de catálogo", description = "Reactiva un elemento de catálogo previamente desactivado.")
    @PatchMapping("/{tipo}/{id}/reactivar")
    public ResponseEntity<?> reactivar(@PathVariable String tipo, @PathVariable Integer id) {
        Class<? extends CatalogoEntidad> clazz = resolveClass(tipo);
        CatalogoEntidad existente = catalogoService.findById(clazz, id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Elemento de catálogo no encontrado con ID: " + id));

        existente.setActivo(true);
        catalogoService.save(existente);
        return ResponseEntity.ok(new com.jobhorizon.backend.config.ApiResponse<>(true, "Elemento reactivado con éxito", existente));
    }
}
