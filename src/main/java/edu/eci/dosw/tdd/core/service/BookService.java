package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.BookNotFoundException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    private Map<String, Book> books = new HashMap<>();
    private Map<String, Integer> stock = new HashMap<>();

    public void addBook(Book book, int quantity) {
        ValidationUtil.requireNonBlank(book.getId(), "El ID del libro no puede estar vacío");
        ValidationUtil.requireNonBlank(book.getTitle(), "El título no puede estar vacío");
        ValidationUtil.requireNonNegative(quantity, "La cantidad no puede ser negativa");

        books.put(book.getId(), book);
        stock.put(book.getId(), quantity);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public Book getBookById(String id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException("Libro no encontrado con ID: " + id);
        }
        return book;
    }

    public boolean isAvailable(String id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Libro no encontrado con ID: " + id);
        }
        return stock.getOrDefault(id, 0) > 0;
    }

    public void decreaseStock(String id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Libro no encontrado con ID: " + id);
        }
        int current = stock.getOrDefault(id, 0);
        if (current <= 0) {
            throw new BookNotAvailableException("No hay ejemplares disponibles del libro: " + id);
        }
        stock.put(id, current - 1);
    }

    public void increaseStock(String id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Libro no encontrado con ID: " + id);
        }
        int current = stock.getOrDefault(id, 0);
        stock.put(id, current + 1);
    }

    public void updateAvailability(String id, boolean available) {
        Book book = getBookById(id);
        book.setAvailable(available);
    }

    public int getStock(String id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Libro no encontrado con ID: " + id);
        }
        return stock.getOrDefault(id, 0);
    }
}