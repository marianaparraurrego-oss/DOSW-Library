package edu.eci.dosw.tdd.controller.mapper;

import edu.eci.dosw.tdd.controller.dto.UserDTO;
import edu.eci.dosw.tdd.core.model.User;

public class UserMapper {

    private UserMapper() {}

    public static UserDTO toDTO(User user) {
        // No exponemos la contraseña en la respuesta
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                null,
                user.getRole()
        );
    }

    public static User toModel(UserDTO dto) {
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getUsername(),
                dto.getPassword(),  // el servicio la encriptará antes de persistir
                dto.getRole()
        );
    }
}