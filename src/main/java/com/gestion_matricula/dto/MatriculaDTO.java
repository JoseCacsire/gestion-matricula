package com.gestion_matricula.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatriculaDTO {

    private String id;

    @NotNull(message = "La fecha de matrícula no puede ser nula")
    private LocalDateTime fechaMatricula;

    @NotNull(message = "El estudiante no puede ser nulo")
    private EstudianteDTO estudiante;

    @NotNull(message = "La lista de cursos no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos un curso")
    private List<CursoDTO> cursos;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean estado;
}