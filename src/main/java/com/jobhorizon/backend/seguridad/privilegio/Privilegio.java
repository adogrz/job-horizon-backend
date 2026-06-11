package com.jobhorizon.backend.seguridad.privilegio;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Privilegio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Privilegio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPrivilegio")
    private Integer id;

    @Column(name = "Nombre", nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(name = "NombreMenu", nullable = false, length = 100)
    private String nombreMenu;

    @Column(name = "Ruta", length = 200)
    private String ruta;
}
