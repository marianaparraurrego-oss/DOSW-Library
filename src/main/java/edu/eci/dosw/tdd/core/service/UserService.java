package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.UserNotFoundException;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.mapper.UserPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, @Lazy PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(UserPersistenceMapper.toEntity(user));
    }


    public List<User> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(UserPersistenceMapper::toModel)
                .collect(Collectors.toList());
    }

    public User getUserById(String id) {
        return repository.findById(id)
                .map(UserPersistenceMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username)
                .map(UserPersistenceMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + username));
    }
}