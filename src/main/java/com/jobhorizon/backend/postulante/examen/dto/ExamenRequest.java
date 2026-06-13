package com.jobhorizon.backend.postulante.examen.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "Cuerpo de la solicitud para agregar o actualizar un examen o prueba de aptitud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamenRequest {

    @Schema(description = "Tipo o nombre del examen (máx. 100 caracteres)", example = "TOEFL", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 100)
    private String tipo;

    @Schema(description = "Resultado o puntaje obtenido en el examen (máx. 100 caracteres)", example = "95/120 puntos", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 100)
    private String resultado;

    @Schema(description = "Fecha en que se rindió el examen en formato YYYY-MM-DD", example = "2023-03-22", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private LocalDate fecha;

    @Schema(description = "URL del archivo o certificado del examen (opcional, máx. 500 caracteres)", example = "https://storage.jobhorizon.com/examenes/toefl-resultado.pdf")
    @Size(max = 500)
    private String archivoUrl;
}
