package com.gestion_matricula.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    @Id
    private String id;
    private String nombres;
    private String apellidos;
    private String dni;
    private Integer edad;
}