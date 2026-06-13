package com.jobhorizon.backend.postulante.certificacion;

import com.jobhorizon.backend.postulante.Postulante;
import com.jobhorizon.backend.tipocertificacion.TipoCertificacion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Certificacion")
@IdClass(CertificacionId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificacion {
    @Id
    @Column(name = "CodCert")
    private Integer codCert;

    @Id
    @Column(name = "IdUsuario")
    private Integer idUsuario;

    @Column(name = "CodigoCertificacion", length = 100)
    private String codigoCertificacion;

    @Column(name = "Nombre", nullable = false, length = 200)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTipoCertificacion", nullable = false)
    private TipoCertificacion tipoCertificacion;

    @Column(name = "Institucion", nullable = false, length = 200)
    private String institucion;

    @Column(name = "FechaObtencion", nullable = false)
    private LocalDate fechaObtencion;

    @Column(name = "ArchivoUrl", length = 500)
    private String archivoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Postulante postulante;
}
