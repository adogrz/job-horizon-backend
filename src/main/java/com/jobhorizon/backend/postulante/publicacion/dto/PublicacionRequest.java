package com.jobhorizon.backend.postulante.publicacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicacionRequest {
    @NotBlank
    @Size(max = 300)
    private String titulo;

    @NotBlank
    @Size(max = 200)
    private String lugarPublicacion;

    @NotNull
    private LocalDate fecha;

    @Size(max = 20)
    private String isbn;

    @Size(max = 100)
    private String edicion;
}
