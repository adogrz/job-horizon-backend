package com.jobhorizon.backend.ofertatrabajo;

import com.jobhorizon.backend.habilidad.Habilidad;
import com.jobhorizon.backend.nivelhabilidad.NivelHabilidad;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OfertaHabilidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaHabilidad {

    @EmbeddedId
    private OfertaHabilidadId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idOferta")
    @JoinColumn(name = "IdOferta")
    private OfertaTrabajo oferta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idHabilidad")
    @JoinColumn(name = "IdHabilidad")
    private Habilidad habilidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNivelHabilidad", nullable = false)
    private NivelHabilidad nivelHabilidad;
}
