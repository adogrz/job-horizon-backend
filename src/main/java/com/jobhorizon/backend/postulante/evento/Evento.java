package com.jobhorizon.backend.postulante.evento;

import com.jobhorizon.backend.pais.Pais;
import com.jobhorizon.backend.postulante.Postulante;
import com.jobhorizon.backend.tipoparticipacion.TipoParticipacion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Evento")
@IdClass(EventoId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {
    @Id
    @Column(name = "NumEvento")
    private Integer numEvento;

    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "NombreEvento", nullable = false, length = 200)
    private String nombreEvento;

    @Column(name = "Lugar", nullable = false, length = 200)
    private String lugar;

    @Column(name = "Anfitrion", nullable = false, length = 200)
    private String anfitrion;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTipoParticipacion", nullable = false)
    private TipoParticipacion tipoParticipacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdPais", nullable = false)
    private Pais pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;
}
