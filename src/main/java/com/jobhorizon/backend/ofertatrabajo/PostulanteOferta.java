package com.jobhorizon.backend.ofertatrabajo;

import com.jobhorizon.backend.postulante.Postulante;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PostulanteOferta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteOferta {

    @EmbeddedId
    private PostulanteOfertaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "IdUsuario")
    private Postulante postulante;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idOferta")
    @JoinColumn(name = "IdOferta")
    private OfertaTrabajo oferta;

    @Column(name = "FechaAplicacion", nullable = false)
    @Builder.Default
    private LocalDateTime fechaAplicacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdEstadoAplicacion", nullable = false)
    private EstadoAplicacion estadoAplicacion;
}
