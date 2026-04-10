package edu.eci.dosw.tdd.Service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.BookNotFoundException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.persistence.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = Book.builder()
                .id("b001")
                .title("Clean Code")
                .author("Robert Martin")
                .available(true)
                .totalStock(5)
                .availableStock(5)
                .build();
    }

    @Test
    void shouldAddBookSuccessfully() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        assertDoesNotThrow(() -> bookService.addBook(book, 5));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldGetBookById() {
        when(bookRepository.findById("b001")).thenReturn(Optional.of(book));

        Book result = bookService.getBookById("b001");

        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.findById("no-existe")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById("no-existe"));
    }

    @Test
    void shouldThrowExceptionWhenBookNotAvailable() {
        Book unavailable = Book.builder()
                .id("b002")
                .title("Test")
                .author("Author")
                .available(false)
                .totalStock(5)
                .availableStock(0)
                .build();
        when(bookRepository.findById("b002")).thenReturn(Optional.of(unavailable));

        assertThrows(BookNotAvailableException.class, () -> bookService.decreaseStock("b002"));
    }

    @Test
    void shouldReturnTrueWhenBookIsAvailable() {
        when(bookRepository.findById("b001")).thenReturn(Optional.of(book));

        assertTrue(bookService.isAvailable("b001"));
    }

    @Test
    void shouldReturnFalseWhenStockIsZero() {
        Book noStock = Book.builder()
                .id("b003")
                .title("Test")
                .author("Author")
                .available(false)
                .totalStock(5)
                .availableStock(0)
                .build();
        when(bookRepository.findById("b003")).thenReturn(Optional.of(noStock));

        assertFalse(bookService.isAvailable("b003"));
    }

    @Test
    void shouldGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.getAllBooks();

        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnEmptyWhenNoBooks() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        List<Book> result = bookService.getAllBooks();

        assertTrue(result.isEmpty());
    }
}