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
public class PostulanteOfertaId implements Serializable {

    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "IdOferta")
    private Integer idOferta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostulanteOfertaId that = (PostulanteOfertaId) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(idOferta, that.idOferta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idOferta);
    }
}
