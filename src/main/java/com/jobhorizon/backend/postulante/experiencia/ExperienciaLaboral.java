package com.jobhorizon.backend.postulante.experiencia;

import com.jobhorizon.backend.postulante.Postulante;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ExperienciaLaboral")
@IdClass(ExperienciaLaboralId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienciaLaboral {
    @Id
    @Column(name = "NumExp")
    private Integer numExp;

    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "NombreEmpresa", nullable = false, length = 150)
    private String nombreEmpresa;

    @Column(name = "Puesto", nullable = false, length = 120)
    private String puesto;

    @Column(name = "FechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "FechaFin")
    private LocalDate fechaFin;

    @Column(name = "TrabajoActual", nullable = false)
    private Boolean trabajoActual;

    @Column(name = "Funciones", nullable = false, columnDefinition = "VARCHAR(MAX)")
    private String funciones;

    @Column(name = "TelefonoContacto", length = 15)
    private String telefonoContacto;

    @Column(name = "CorreoContacto", length = 150)
    private String correoContacto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;
}
