package com.jobhorizon.backend.empresa;

import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.seguridad.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {
    @Id
    @Column(name = "IdUsuario")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;

    @Column(name = "NombreComercial", nullable = false, length = 150)
    private String nombreComercial;

    @Column(name = "RazonSocial", nullable = false, length = 200)
    private String razonSocial;

    @Column(name = "Nit", nullable = false, unique = true, length = 17)
    private String nit;

    @Column(name = "SitioWeb", length = 300)
    private String sitioWeb;

    @Column(name = "Descripcion", columnDefinition = "VARCHAR(MAX)")
    private String descripcion;

    @Column(name = "LogoUrl", length = 500)
    private String logoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDistrito", nullable = false)
    private Distrito distrito;
}
