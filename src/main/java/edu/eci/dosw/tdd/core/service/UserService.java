package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.UserNotFoundException;
import edu.eci.dosw.tdd.core.model.User;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    public void registerUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
    }
}