package com.jobhorizon.backend.postulante.logro;

import com.jobhorizon.backend.postulante.Postulante;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Logro")
@IdClass(LogroId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Logro {
    @Id
    @Column(name = "NumLogro")
    private Integer numLogro;

    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "Descripcion", nullable = false, columnDefinition = "VARCHAR(MAX)")
    private String descripcion;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;
}
