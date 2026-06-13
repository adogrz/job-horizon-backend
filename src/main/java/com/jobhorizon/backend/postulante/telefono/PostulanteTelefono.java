package com.jobhorizon.backend.postulante.telefono;

import com.jobhorizon.backend.postulante.Postulante;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PostulanteTelefono")
@IdClass(PostulanteTelefonoId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteTelefono {
    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Id
    @Column(name = "Telefono", length = 15)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;
}
