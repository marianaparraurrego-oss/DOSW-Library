package edu.eci.dosw.tdd.controller.mapper;

import edu.eci.dosw.tdd.controller.dto.UserDTO;
import edu.eci.dosw.tdd.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                null,   // nunca exponemos la contraseña
                user.getRole(),
                user.getEmail(),
                user.getMembershipType(),
                user.getJoinedAt()
        );
    }

    public User toModel(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(dto.getRole())
                .email(dto.getEmail())
                .membershipType(dto.getMembershipType())
                .joinedAt(dto.getJoinedAt())
                .build();
    }
}