package com.jobhorizon.backend.tipodocumento;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TipoDocumento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoDocumento")
    private Integer id;

    @Column(name = "Nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "Descripcion", length = 200)
    private String descripcion;
}
