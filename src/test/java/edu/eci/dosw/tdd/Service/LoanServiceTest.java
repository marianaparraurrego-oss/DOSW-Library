package edu.eci.dosw.tdd.Service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.exception.UserNotFoundException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.core.service.LoanService;
import edu.eci.dosw.tdd.core.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para LoanService.
 *
 * Escenarios cubiertos:
 *   ✓ Préstamo exitoso
 *   ✓ Libro no disponible
 *   ✓ Usuario no encontrado
 *   ✓ Límite de préstamos excedido
 *   ✓ Devolución exitosa
 */
public class LoanServiceTest {

    // Estos objetos se crean de nuevo antes de cada prueba gracias a @BeforeEach
    private BookService bookService;
    private UserService userService;
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        userService = new UserService();
        loanService = new LoanService(bookService, userService);
    }

    @Test
    public void shouldLoanBookSuccessfully() {
        // Arrange (preparar los datos)
        Book book = new Book("1", "Clean Code", "Martin", true);
        bookService.addBook(book, 1);

        User user = new User("1", "Andrea");
        userService.registerUser(user);

        // Act (ejecutar la acción)
        loanService.loanBook("1", "1");

        // Assert (verificar el resultado)
        assertEquals(1, loanService.getAllLoans().size());
        assertEquals("ACTIVE", loanService.getAllLoans().get(0).getStatus());
    }

    @Test
    public void shouldThrowExceptionWhenBookNotAvailable() {
        // El libro existe pero no tiene stock (quantity = 0)
        Book book = new Book("1", "Clean Code", "Martin", true);
        bookService.addBook(book, 0);

        User user = new User("1", "Andrea");
        userService.registerUser(user);

        // Debe lanzar BookNotAvailableException
        assertThrows(BookNotAvailableException.class, () -> {
            loanService.loanBook("1", "1");
        });
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        // El libro existe con stock, pero el usuario "999" no está registrado
        Book book = new Book("1", "Clean Code", "Martin", true);
        bookService.addBook(book, 1);

        // Debe lanzar UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> {
            loanService.loanBook("999", "1");
        });
    }

    @Test
    public void shouldThrowExceptionWhenLoanLimitExceeded() {
        // Agregar libros suficientes
        bookService.addBook(new Book("1", "Libro 1", "Autor", true), 5);
        bookService.addBook(new Book("2", "Libro 2", "Autor", true), 5);
        bookService.addBook(new Book("3", "Libro 3", "Autor", true), 5);
        bookService.addBook(new Book("4", "Libro 4", "Autor", true), 5);

        User user = new User("1", "Andrea");
        userService.registerUser(user);

        // Prestar 3 libros (el máximo permitido)
        loanService.loanBook("1", "1");
        loanService.loanBook("1", "2");
        loanService.loanBook("1", "3");

        // El cuarto préstamo debe lanzar LoanLimitExceededException
        assertThrows(LoanLimitExceededException.class, () -> {
            loanService.loanBook("1", "4");
        });
    }

    @Test
    public void shouldReturnBookSuccessfully() {
        // Preparar
        Book book = new Book("1", "Clean Code", "Martin", true);
        bookService.addBook(book, 1);

        User user = new User("1", "Andrea");
        userService.registerUser(user);

        // Prestar el libro
        loanService.loanBook("1", "1");
        String loanId = loanService.getAllLoans().get(0).getId();

        // Verificar que antes de devolver no hay stock
        assertFalse(bookService.isAvailable("1"));

        // Devolver el libro
        loanService.returnBook(loanId);

        // Verificar que el estado cambió y el stock volvió
        assertEquals("RETURNED", loanService.getAllLoans().get(0).getStatus());
        assertTrue(bookService.isAvailable("1"));
    }

    @Test
    public void shouldAllowNewLoanAfterReturn() {
        // Si un usuario devuelve un libro, puede pedir prestado de nuevo
        Book book = new Book("1", "Clean Code", "Martin", true);
        bookService.addBook(book, 1);

        User user = new User("1", "Andrea");
        userService.registerUser(user);

        // Primer préstamo
        loanService.loanBook("1", "1");
        String loanId = loanService.getAllLoans().get(0).getId();

        // Devolver
        loanService.returnBook(loanId);

        // Segundo préstamo del mismo libro (ahora sí hay stock de nuevo)
        loanService.loanBook("1", "1");

        // Debe haber 2 préstamos en total
        assertEquals(2, loanService.getAllLoans().size());
    }
}