package edu.eci.dosw.tdd.Service;

import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.service.UserService;
import edu.eci.dosw.tdd.core.exception.UserNotFoundException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class UserServiceTest {

    private final UserService service = new UserService();

    @Test
    public void shouldRegisterUserSuccessfully() {
        User user = new User("1", "Andrea");

        service.registerUser(user);

        List<User> users = service.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("Andrea", users.get(0).getName());
    }

    @Test
    public void shouldReturnAllUsers() {
        service.registerUser(new User("1", "Andrea"));
        service.registerUser(new User("2", "Juan"));

        List<User> users = service.getAllUsers();

        assertEquals(2, users.size());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            service.getUserById("999");
        });
    }
}