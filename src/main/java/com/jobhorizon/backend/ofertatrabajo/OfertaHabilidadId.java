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
public class OfertaHabilidadId implements Serializable {

    @Column(name = "IdOferta")
    private Integer idOferta;

    @Column(name = "IdHabilidad")
    private Integer idHabilidad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfertaHabilidadId that = (OfertaHabilidadId) o;
        return Objects.equals(idOferta, that.idOferta) && Objects.equals(idHabilidad, that.idHabilidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOferta, idHabilidad);
    }
}
