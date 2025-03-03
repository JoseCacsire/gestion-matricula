package com.gestion_matricula.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    private String id;
    private String nombre;
    private String siglas;
    private Boolean estado;
}
