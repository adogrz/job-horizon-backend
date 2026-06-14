package com.jobhorizon.backend.ofertatrabajo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaIdiomaId implements Serializable {

    @Column(name = "IdOferta")
    private Integer idOferta;

    @Column(name = "IdIdioma")
    private Integer idIdioma;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfertaIdiomaId that = (OfertaIdiomaId) o;
        return Objects.equals(idOferta, that.idOferta) && Objects.equals(idIdioma, that.idIdioma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOferta, idIdioma);
    }
}
