package edu.eci.dosw.tdd.Service;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.BookNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    // Se crea un BookService fresco antes de cada prueba
    private BookService service;

    @BeforeEach
    void setUp() {
        service = new BookService();
    }

    @Test
    public void shouldAddBookSuccessfully() {
        Book book = new Book("1", "Clean Code", "Robert Martin", true);

        service.addBook(book, 2);

        assertEquals(1, service.getAllBooks().size());
    }

    @Test
    public void shouldGetBookById() {
        Book book = new Book("1", "Clean Code", "Robert Martin", true);
        service.addBook(book, 1);

        Book result = service.getBookById("1");

        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    public void shouldThrowExceptionWhenBookNotFound() {
        // Intentar buscar un libro que no existe debe lanzar BookNotFoundException
        assertThrows(BookNotFoundException.class, () -> {
            service.getBookById("id-inexistente");
        });
    }

    @Test
    public void shouldThrowExceptionWhenBookNotAvailable() {
        Book book = new Book("1", "Clean Code", "Robert Martin", false);
        service.addBook(book, 0); // stock en 0

        assertThrows(BookNotAvailableException.class, () -> {
            service.decreaseStock("1");
        });
    }

    @Test
    public void shouldIncreaseStock() {
        Book book = new Book("1", "Test", "Author", true);
        service.addBook(book, 1);

        service.increaseStock("1");

        // Después de aumentar, el stock es 2, así que sigue disponible
        assertTrue(service.isAvailable("1"));
        assertEquals(2, service.getStock("1"));
    }

    @Test
    public void shouldReturnFalseWhenStockIsZero() {
        Book book = new Book("1", "Test", "Author", true);
        service.addBook(book, 0);

        assertFalse(service.isAvailable("1"));
    }

    @Test
    public void shouldUpdateAvailability() {
        Book book = new Book("1", "Test", "Author", true);
        service.addBook(book, 1);

        // Marcar como no disponible
        service.updateAvailability("1", false);
        assertFalse(service.getBookById("1").isAvailable());

        // Marcar como disponible de nuevo
        service.updateAvailability("1", true);
        assertTrue(service.getBookById("1").isAvailable());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingNonExistentBook() {
        assertThrows(BookNotFoundException.class, () -> {
            service.updateAvailability("no-existe", true);
        });
    }
}