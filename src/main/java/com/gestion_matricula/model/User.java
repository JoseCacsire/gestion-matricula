package com.gestion_matricula.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "users")
public class User {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String username;

    @Field
    private String password;

    @Field
    private boolean status;

    @Field
    private List<Role> roles;

    public User(String username, String password, boolean status, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }
}
