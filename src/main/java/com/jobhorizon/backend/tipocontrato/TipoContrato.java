package com.jobhorizon.backend.tipocontrato;

import com.jobhorizon.backend.catalogo.CatalogoEntidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TipoContrato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoContrato implements CatalogoEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoContrato")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "Activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
