package com.jobhorizon.backend.postulante.publicacion;

import com.jobhorizon.backend.postulante.Postulante;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Publicacion")
@IdClass(PublicacionId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publicacion {
    @Id
    @Column(name = "NumPublicacion")
    private Integer numPublicacion;

    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "Titulo", nullable = false, length = 300)
    private String titulo;

    @Column(name = "LugarPublicacion", nullable = false, length = 200)
    private String lugarPublicacion;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "Isbn", length = 20)
    private String isbn;

    @Column(name = "Edicion", length = 100)
    private String edicion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;
}
