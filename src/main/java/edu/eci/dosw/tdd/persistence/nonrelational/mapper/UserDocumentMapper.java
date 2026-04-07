package edu.eci.dosw.tdd.persistence.nonrelational.mapper;

import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.nonrelational.document.UserDocument;

public class UserDocumentMapper {

    private UserDocumentMapper() {}

    public static User toModel(UserDocument doc) {
        return new User(
                doc.getId(),
                doc.getName(),
                doc.getUsername(),
                doc.getPassword(),
                doc.getRole()
        );
    }

    public static UserDocument toDocument(User user) {
        return new UserDocument(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}