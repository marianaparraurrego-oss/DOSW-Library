package edu.eci.dosw.tdd.controller.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private String id;

    @NotBlank
    private String name;
}