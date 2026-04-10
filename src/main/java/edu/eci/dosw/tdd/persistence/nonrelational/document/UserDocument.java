package edu.eci.dosw.tdd.persistence.nonrelational.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDocument {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String username;
    private String password;
    private String role;

    // Extendidos Parte 3
    private String email;
    private String membershipType;
    private LocalDate joinedAt;
}