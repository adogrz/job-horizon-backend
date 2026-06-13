package com.jobhorizon.backend.postulante.formacion;

import com.jobhorizon.backend.niveleducativo.NivelEducativo;
import com.jobhorizon.backend.postulante.Postulante;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "FormacionAcademica")
@IdClass(FormacionAcademicaId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormacionAcademica {
    @Id
    @Column(name = "NumFormacion")
    private Integer numFormacion;

    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "Institucion", nullable = false, length = 200)
    private String institucion;

    @Column(name = "Titulo", nullable = false, length = 200)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNivelEducativo", nullable = false)
    private NivelEducativo nivelEducativo;

    @Column(name = "FechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "FechaFin")
    private LocalDate fechaFin;

    @Column(name = "EnCurso", nullable = false)
    private Boolean enCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;
}
