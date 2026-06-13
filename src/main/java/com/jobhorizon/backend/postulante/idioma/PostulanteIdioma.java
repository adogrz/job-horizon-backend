package com.jobhorizon.backend.postulante.idioma;

import com.jobhorizon.backend.idioma.Idioma;
import com.jobhorizon.backend.nivelidioma.NivelIdioma;
import com.jobhorizon.backend.postulante.Postulante;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PostulanteIdioma")
@IdClass(PostulanteIdiomaId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteIdioma {
    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Id
    @Column(name = "IdIdioma")
    private Integer idIdioma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNivelLectura", nullable = false)
    private NivelIdioma nivelLectura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNivelEscritura", nullable = false)
    private NivelIdioma nivelEscritura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNivelConversacion", nullable = false)
    private NivelIdioma nivelConversacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNivelEscucha", nullable = false)
    private NivelIdioma nivelEscucha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdIdioma", insertable = false, updatable = false)
    private Idioma idioma;
}
