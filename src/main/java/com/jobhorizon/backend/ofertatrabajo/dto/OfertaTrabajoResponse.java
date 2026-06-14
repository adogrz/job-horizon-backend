package com.jobhorizon.backend.ofertatrabajo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Detalle completo de una oferta de trabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaTrabajoResponse {
    private Integer id;
    private String titulo;
    private String descripcion;
    private BigDecimal salarioMin;
    private BigDecimal salarioMax;
    private Short numVacantes;
    private Short aniosExperienciaMinima;
    private LocalDateTime fechaPublicacion;
    private LocalDate fechaVencimiento;

    // Empresa info
    private Integer idEmpresa;
    private String nombreEmpresa;
    private String logoUrlEmpresa;

    // Catálogos info
    private Integer idTipoContrato;
    private String nombreTipoContrato;
    private Integer idNivelEducativo;
    private String nombreNivelEducativo;
    private Integer idModalidad;
    private String nombreModalidad;
    private Integer idEstadoOferta;
    private String nombreEstadoOferta;
    private Integer idDistrito;
    private String nombreDistrito;
    private Integer idDepartamento;
    private String nombreDepartamento;

    @Builder.Default
    private Set<OfertaHabilidadResponse> habilidades = new HashSet<>();

    @Builder.Default
    private Set<OfertaIdiomaResponse> idiomas = new HashSet<>();
}
