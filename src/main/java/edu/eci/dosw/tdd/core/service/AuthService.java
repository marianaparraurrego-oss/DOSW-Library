package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.UserNotFoundException;
import edu.eci.dosw.tdd.persistence.nonrelational.document.UserDocument;
import edu.eci.dosw.tdd.persistence.nonrelational.repository.MongoUserRepository;
import edu.eci.dosw.tdd.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MongoUserRepository mongoUserRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(MongoUserRepository mongoUserRepository,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder) {
        this.mongoUserRepository = mongoUserRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        UserDocument user = mongoUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException("Credenciales incorrectas");
        }

        return jwtService.generateToken(user.getId(), user.getRole());
    }
}