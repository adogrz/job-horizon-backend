package com.jobhorizon.backend.postulante;

import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.genero.Genero;
import com.jobhorizon.backend.seguridad.usuario.Usuario;
import com.jobhorizon.backend.tipodocumento.TipoDocumento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Postulante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Postulante {
    @Id
    @Column(name = "IdUsuario")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;

    @Column(name = "Nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "Apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "FechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "NumDocumento", nullable = false, length = 20)
    private String numDocumento;

    @Column(name = "Nup", length = 20)
    private String nup;

    @Column(name = "Nit", length = 20)
    private String nit;

    @Column(name = "Direccion", nullable = false, length = 300)
    private String direccion;

    @Column(name = "FotoUrl", length = 500)
    private String fotoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdGenero", nullable = false)
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTipoDocumento", nullable = false)
    private TipoDocumento tipoDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDistrito", nullable = false)
    private Distrito distrito;
}
