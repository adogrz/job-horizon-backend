package com.jobhorizon.backend.seguridad.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobhorizon.backend.seguridad.estadousuario.EstadoUsuario;
import com.jobhorizon.backend.seguridad.rol.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUsuario")
    private Integer id;

    @Column(name = "Correo", nullable = false, unique = true, length = 150)
    private String correo;

    @JsonIgnore
    @Column(name = "PasswordHash", nullable = false)
    private String passwordHash;

    @Column(name = "IntentosFallidos", nullable = false)
    private Byte intentosFallidos;

    @Column(name = "FechaRegistro", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @JsonIgnore
    @Column(name = "TokenDesbloqueo", length = 100)
    private String tokenDesbloqueo;

    @JsonIgnore
    @Column(name = "FechaTokenExp")
    private LocalDateTime fechaTokenExp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdEstadoUsuario", nullable = false)
    private EstadoUsuario estadoUsuario;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "UsuarioRol",
            joinColumns = @JoinColumn(name = "IdUsuario"),
            inverseJoinColumns = @JoinColumn(name = "IdRol")
    )
    private Set<Rol> roles;
}