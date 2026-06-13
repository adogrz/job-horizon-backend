package com.jobhorizon.backend.postulante.habilidad;

import com.jobhorizon.backend.habilidad.Habilidad;
import com.jobhorizon.backend.nivelhabilidad.NivelHabilidad;
import com.jobhorizon.backend.postulante.Postulante;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PostulanteHabilidad")
@IdClass(PostulanteHabilidadId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteHabilidad {
    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Id
    @Column(name = "IdHabilidad")
    private Integer idHabilidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNivelHabilidad", nullable = false)
    private NivelHabilidad nivelHabilidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdHabilidad", insertable = false, updatable = false)
    private Habilidad habilidad;
}
