package edu.eci.dosw.tdd.core.model;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String id;
    private String name;
    private String username;
    private String password;
    private String role;

    private String email;
    private String membershipType;
    private LocalDate joinedAt;
}