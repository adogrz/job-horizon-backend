package com.jobhorizon.backend.ofertatrabajo;

import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.empresa.Empresa;
import com.jobhorizon.backend.estadooferta.EstadoOferta;
import com.jobhorizon.backend.modalidad.Modalidad;
import com.jobhorizon.backend.niveleducativo.NivelEducativo;
import com.jobhorizon.backend.tipocontrato.TipoContrato;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OfertaTrabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdOferta")
    private Integer id;

    @Column(name = "Titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "Descripcion", nullable = false, columnDefinition = "VARCHAR(MAX)")
    private String descripcion;

    @Column(name = "SalarioMin", precision = 10, scale = 2)
    private BigDecimal salarioMin;

    @Column(name = "SalarioMax", precision = 10, scale = 2)
    private BigDecimal salarioMax;

    @Column(name = "NumVacantes", nullable = false)
    private Short numVacantes;

    @Column(name = "AniosExperienciaMinima", nullable = false)
    private Short aniosExperienciaMinima;

    @Column(name = "FechaPublicacion", nullable = false)
    private LocalDateTime fechaPublicacion;

    @Column(name = "FechaVencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdEmpresa", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTipoContrato", nullable = false)
    private TipoContrato tipoContrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNivelEducativo", nullable = false)
    private NivelEducativo nivelEducativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdModalidad", nullable = false)
    private Modalidad modalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdEstadoOferta", nullable = false)
    private EstadoOferta estadoOferta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDistrito", nullable = false)
    private Distrito distrito;

    @OneToMany(mappedBy = "oferta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OfertaHabilidad> habilidades = new HashSet<>();

    @OneToMany(mappedBy = "oferta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OfertaIdioma> idiomas = new HashSet<>();
}
