package com.jobhorizon.backend.ofertatrabajo;

import com.jobhorizon.backend.idioma.Idioma;
import com.jobhorizon.backend.nivelidioma.NivelIdioma;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OfertaIdioma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaIdioma {

    @EmbeddedId
    private OfertaIdiomaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idOferta")
    @JoinColumn(name = "IdOferta")
    private OfertaTrabajo oferta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idIdioma")
    @JoinColumn(name = "IdIdioma")
    private Idioma idioma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNivelIdioma", nullable = false)
    private NivelIdioma nivelIdioma;
}
