package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.BookNotFoundException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.util.ValidationUtil;
import edu.eci.dosw.tdd.persistence.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public void addBook(Book book, int quantity) {
        ValidationUtil.requireNonBlank(book.getId(), "El ID del libro no puede estar vacío");
        ValidationUtil.requireNonBlank(book.getTitle(), "El título no puede estar vacío");
        ValidationUtil.requirePositive(quantity, "La cantidad de ejemplares debe ser mayor a 0");

        book.setTotalStock(quantity);
        book.setAvailableStock(quantity);
        book.setAvailable(true);

        repository.save(book);
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con ID: " + id));
    }

    public boolean isAvailable(String id) {
        return getBookById(id).getAvailableStock() > 0;
    }

    public void decreaseStock(String id) {
        Book book = getBookById(id);
        if (book.getAvailableStock() <= 0) {
            throw new BookNotAvailableException("No hay ejemplares disponibles: " + id);
        }
        book.setAvailableStock(book.getAvailableStock() - 1);
        if (book.getAvailableStock() == 0) {
            book.setAvailable(false);
        }
        repository.save(book);
    }

    public void increaseStock(String id) {
        Book book = getBookById(id);
        if (book.getAvailableStock() >= book.getTotalStock()) {
            throw new IllegalStateException(
                    "El stock disponible ya es igual al stock total para el libro: " + id);
        }
        book.setAvailableStock(book.getAvailableStock() + 1);
        book.setAvailable(true);
        repository.save(book);
    }

    public void updateAvailability(String id, boolean available) {
        Book book = getBookById(id);
        book.setAvailable(available);
        repository.save(book);
    }

    public void updateTotalStock(String id, int newTotalStock) {
        ValidationUtil.requirePositive(newTotalStock, "El stock total debe ser mayor a 0");
        Book book = getBookById(id);
        int diff = newTotalStock - book.getTotalStock();
        book.setTotalStock(newTotalStock);
        int newAvailable = Math.max(0, book.getAvailableStock() + diff);
        book.setAvailableStock(newAvailable);
        book.setAvailable(newAvailable > 0);
        repository.save(book);
    }
}