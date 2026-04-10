package edu.eci.dosw.tdd.persistence.relational.mapper;

import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.relational.entity.UserEntity;

public class UserPersistenceMapper {

    private UserPersistenceMapper() {}

    public static User toModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .role(entity.getRole())
                .email(entity.getEmail())
                .membershipType(entity.getMembershipType())
                .joinedAt(entity.getJoinedAt())
                .build();
    }

    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getEmail(),
                user.getMembershipType(),
                user.getJoinedAt()
        );
    }
}