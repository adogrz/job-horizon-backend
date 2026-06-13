package com.jobhorizon.backend.catalogo;

import com.jobhorizon.backend.config.ApiResponse;
import com.jobhorizon.backend.departamento.Departamento;
import com.jobhorizon.backend.departamento.DepartamentoRepository;
import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.distrito.DistritoRepository;
import com.jobhorizon.backend.genero.Genero;
import com.jobhorizon.backend.genero.GeneroRepository;
import com.jobhorizon.backend.habilidad.CategoriaHabilidad;
import com.jobhorizon.backend.habilidad.CategoriaHabilidadRepository;
import com.jobhorizon.backend.habilidad.Habilidad;
import com.jobhorizon.backend.habilidad.HabilidadRepository;
import com.jobhorizon.backend.habilidad.dto.HabilidadResponse;
import com.jobhorizon.backend.idioma.Idioma;
import com.jobhorizon.backend.idioma.IdiomaRepository;
import com.jobhorizon.backend.niveleducativo.NivelEducativo;
import com.jobhorizon.backend.niveleducativo.NivelEducativoRepository;
import com.jobhorizon.backend.nivelhabilidad.NivelHabilidad;
import com.jobhorizon.backend.nivelhabilidad.NivelHabilidadRepository;
import com.jobhorizon.backend.nivelidioma.NivelIdioma;
import com.jobhorizon.backend.nivelidioma.NivelIdiomaRepository;
import com.jobhorizon.backend.pais.Pais;
import com.jobhorizon.backend.pais.PaisRepository;
import com.jobhorizon.backend.tipocertificacion.TipoCertificacion;
import com.jobhorizon.backend.tipocertificacion.TipoCertificacionRepository;
import com.jobhorizon.backend.tipodocumento.TipoDocumento;
import com.jobhorizon.backend.tipodocumento.TipoDocumentoRepository;
import com.jobhorizon.backend.tipoparticipacion.TipoParticipacion;
import com.jobhorizon.backend.tipoparticipacion.TipoParticipacionRepository;
import com.jobhorizon.backend.tiporecomendacion.TipoRecomendacion;
import com.jobhorizon.backend.tiporecomendacion.TipoRecomendacionRepository;
import com.jobhorizon.backend.tiporedsocial.TipoRedSocial;
import com.jobhorizon.backend.tiporedsocial.TipoRedSocialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogos")
@RequiredArgsConstructor
public class CatalogoController {

    private final GeneroRepository generoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final DistritoRepository distritoRepository;
    private final NivelEducativoRepository nivelEducativoRepository;
    private final CategoriaHabilidadRepository categoriaHabilidadRepository;
    private final HabilidadRepository habilidadRepository;
    private final NivelHabilidadRepository nivelHabilidadRepository;
    private final IdiomaRepository idiomaRepository;
    private final NivelIdiomaRepository nivelIdiomaRepository;
    private final TipoCertificacionRepository tipoCertificacionRepository;
    private final TipoRecomendacionRepository tipoRecomendacionRepository;
    private final PaisRepository paisRepository;
    private final TipoParticipacionRepository tipoParticipacionRepository;
    private final TipoRedSocialRepository tipoRedSocialRepository;

    @GetMapping("/generos")
    public ResponseEntity<ApiResponse<List<Genero>>> listarGeneros() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Géneros obtenidos con éxito", generoRepository.findAll()));
    }

    @GetMapping("/tipos-documento")
    public ResponseEntity<ApiResponse<List<TipoDocumento>>> listarTiposDocumento() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Tipos de documento obtenidos con éxito", tipoDocumentoRepository.findAll()));
    }

    @GetMapping("/departamentos")
    public ResponseEntity<ApiResponse<List<Departamento>>> listarDepartamentos() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Departamentos obtenidos con éxito", departamentoRepository.findAll()));
    }

    @GetMapping("/departamentos/{id}/distritos")
    public ResponseEntity<ApiResponse<List<Distrito>>> listarDistritosPorDepartamento(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Distritos obtenidos con éxito", distritoRepository.findByIdDepartamento(id)));
    }

    @GetMapping("/niveles-educativos")
    public ResponseEntity<ApiResponse<List<NivelEducativo>>> listarNivelesEducativos() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Niveles educativos obtenidos con éxito", nivelEducativoRepository.findAll()));
    }

    @GetMapping("/categorias-habilidad")
    public ResponseEntity<ApiResponse<List<CategoriaHabilidad>>> listarCategoriasHabilidad() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Categorías de habilidad obtenidas con éxito", categoriaHabilidadRepository.findAll()));
    }

    @GetMapping("/categorias-habilidad/{id}/habilidades")
    public ResponseEntity<ApiResponse<List<HabilidadResponse>>> listarHabilidadesPorCategoria(@PathVariable Integer id) {
        List<HabilidadResponse> habilidades = habilidadRepository.findByCategoriaHabilidadId(id).stream()
                .map(h -> new HabilidadResponse(h.getId(), h.getNombre(), h.getDescripcion()))
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Habilidades obtenidas con éxito", habilidades));
    }

    @GetMapping("/niveles-habilidad")
    public ResponseEntity<ApiResponse<List<NivelHabilidad>>> listarNivelesHabilidad() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Niveles de habilidad obtenidos con éxito", nivelHabilidadRepository.findAll()));
    }

    @GetMapping("/idiomas")
    public ResponseEntity<ApiResponse<List<Idioma>>> listarIdiomas() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Idiomas obtenidos con éxito", idiomaRepository.findAll()));
    }

    @GetMapping("/niveles-idioma")
    public ResponseEntity<ApiResponse<List<NivelIdioma>>> listarNivelesIdioma() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Niveles de idioma obtenidos con éxito", nivelIdiomaRepository.findAll()));
    }

    @GetMapping("/tipos-certificacion")
    public ResponseEntity<ApiResponse<List<TipoCertificacion>>> listarTiposCertificacion() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Tipos de certificación obtenidos con éxito", tipoCertificacionRepository.findAll()));
    }

    @GetMapping("/tipos-recomendacion")
    public ResponseEntity<ApiResponse<List<TipoRecomendacion>>> listarTiposRecomendacion() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Tipos de recomendación obtenidos con éxito", tipoRecomendacionRepository.findAll()));
    }

    @GetMapping("/paises")
    public ResponseEntity<ApiResponse<List<Pais>>> listarPaises() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Países obtenidos con éxito", paisRepository.findAll()));
    }

    @GetMapping("/tipos-participacion")
    public ResponseEntity<ApiResponse<List<TipoParticipacion>>> listarTiposParticipacion() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Tipos de participación obtenidos con éxito", tipoParticipacionRepository.findAll()));
    }

    @GetMapping("/tipos-red-social")
    public ResponseEntity<ApiResponse<List<TipoRedSocial>>> listarTiposRedSocial() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Tipos de red social obtenidos con éxito", tipoRedSocialRepository.findAll()));
    }
}
