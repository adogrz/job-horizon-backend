package com.jobhorizon.backend.postulante.recomendacion;

import com.jobhorizon.backend.postulante.Postulante;
import com.jobhorizon.backend.tiporecomendacion.TipoRecomendacion;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Recomendacion")
@IdClass(RecomendacionId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recomendacion {
    @Id
    @Column(name = "NumRecomendacion")
    private Integer numRecomendacion;

    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "NombreContacto", nullable = false, length = 150)
    private String nombreContacto;

    @Column(name = "TelefonoContacto", nullable = false, length = 15)
    private String telefonoContacto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTipoRecomendacion", nullable = false)
    private TipoRecomendacion tipoRecomendacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;
}
