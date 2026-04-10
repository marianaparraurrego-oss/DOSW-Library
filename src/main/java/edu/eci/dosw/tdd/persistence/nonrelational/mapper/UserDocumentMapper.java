package edu.eci.dosw.tdd.persistence.nonrelational.mapper;

import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.nonrelational.document.UserDocument;

public class UserDocumentMapper {

    private UserDocumentMapper() {}

    public static User toModel(UserDocument doc) {
        return User.builder()
                .id(doc.getId())
                .name(doc.getName())
                .username(doc.getUsername())
                .password(doc.getPassword())
                .role(doc.getRole())
                .email(doc.getEmail())
                .membershipType(doc.getMembershipType())
                .joinedAt(doc.getJoinedAt())
                .build();
    }

    public static UserDocument toDocument(User user) {
        return new UserDocument(
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