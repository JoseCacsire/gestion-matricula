package com.gestion_matricula.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "roles")
public class Role {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String name;

    public Role(String name) {
        this.name = name;
    }

}
