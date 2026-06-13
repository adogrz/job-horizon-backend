package com.jobhorizon.backend.postulante.examen;

import com.jobhorizon.backend.postulante.Postulante;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Examen")
@IdClass(ExamenId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Examen {
    @Id
    @Column(name = "NumExamen")
    private Integer numExamen;

    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "Tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "Resultado", nullable = false, length = 100)
    private String resultado;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "ArchivoUrl", length = 500)
    private String archivoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;
}
