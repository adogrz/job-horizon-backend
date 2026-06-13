package com.jobhorizon.backend.postulante.examen.dto;

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
public class ExamenRequest {
    @NotBlank
    @Size(max = 100)
    private String tipo;

    @NotBlank
    @Size(max = 100)
    private String resultado;

    @NotNull
    private LocalDate fecha;

    @Size(max = 500)
    private String archivoUrl;
}
