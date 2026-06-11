package com.jobhorizon.backend.seguridad.rol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobhorizon.backend.seguridad.privilegio.Privilegio;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRol")
    private Integer id;

    @Column(name = "Nombre", nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(name = "Descripcion", length = 300)
    private String descripcion;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "RolPrivilegio",
            joinColumns = @JoinColumn(name = "IdRol"),
            inverseJoinColumns = @JoinColumn(name = "IdPrivilegio")
    )
    private Set<Privilegio> privilegios;
}