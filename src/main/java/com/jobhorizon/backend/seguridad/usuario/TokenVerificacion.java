package com.jobhorizon.backend.seguridad.usuario;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad JPA que representa la tabla TokenVerificacion.
 * Almacena el token utilizado para la verificación de correo electrónico al registrarse.
 */
@Entity
@Table(name = "TokenVerificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenVerificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTokenVerificacion")
    private Integer id;

    @Column(name = "Token", nullable = false, unique = true, length = 100)
    private String token;

    @Column(name = "FechaExpiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUsuario", nullable = false)
    private Usuario usuario;
}
