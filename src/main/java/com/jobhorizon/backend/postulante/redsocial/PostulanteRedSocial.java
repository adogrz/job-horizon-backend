package com.jobhorizon.backend.postulante.redsocial;

import com.jobhorizon.backend.postulante.Postulante;
import com.jobhorizon.backend.tiporedsocial.TipoRedSocial;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PostulanteRedSocial")
@IdClass(PostulanteRedSocialId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteRedSocial {
    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Id
    @Column(name = "IdTipoRedSocial")
    private Integer idTipoRedSocial;

    @Column(name = "Url", nullable = false, length = 500)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTipoRedSocial", insertable = false, updatable = false)
    private TipoRedSocial tipoRedSocial;
}
