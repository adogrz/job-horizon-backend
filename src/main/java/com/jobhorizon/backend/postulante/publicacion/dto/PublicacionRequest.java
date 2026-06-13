package com.jobhorizon.backend.postulante.publicacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "Cuerpo de la solicitud para agregar o actualizar una publicación académica o científica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicacionRequest {

    @Schema(description = "Título de la publicación (máx. 300 caracteres)", example = "Optimización de algoritmos de búsqueda en grafos distribuidos", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 300)
    private String titulo;

    @Schema(description = "Nombre de la revista, editorial o conferencia donde se publicó (máx. 200 caracteres)", example = "Revista Iberoamericana de Inteligencia Artificial", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 200)
    private String lugarPublicacion;

    @Schema(description = "Fecha de publicación en formato YYYY-MM-DD", example = "2023-05-10", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private LocalDate fecha;

    @Schema(description = "Número ISBN de la publicación (opcional, máx. 20 caracteres)", example = "978-3-16-148410-0")
    @Size(max = 20)
    private String isbn;

    @Schema(description = "Número de edición de la publicación (opcional, máx. 100 caracteres)", example = "2da edición")
    @Size(max = 100)
    private String edicion;
}
