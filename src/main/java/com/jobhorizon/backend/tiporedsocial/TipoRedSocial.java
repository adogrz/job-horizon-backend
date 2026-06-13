package com.jobhorizon.backend.tiporedsocial;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TipoRedSocial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoRedSocial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoRedSocial")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 50)
    private String nombre;
}
