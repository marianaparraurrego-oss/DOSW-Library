package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.BookNotFoundException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.util.ValidationUtil;
import edu.eci.dosw.tdd.persistence.mapper.BookPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        repository.save(BookPersistenceMapper.toEntity(book));
    }

    public List<Book> getAllBooks() {
        return repository.findAll()
                .stream()
                .map(BookPersistenceMapper::toModel)
                .collect(Collectors.toList());
    }

    public Book getBookById(String id) {
        return repository.findById(id)
                .map(BookPersistenceMapper::toModel)
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
        // Si llega a 0, marcamos como no disponible
        if (book.getAvailableStock() == 0) {
            book.setAvailable(false);
        }
        repository.save(BookPersistenceMapper.toEntity(book));
    }

    public void increaseStock(String id) {
        Book book = getBookById(id);
        // No puede superar el stock total original
        if (book.getAvailableStock() >= book.getTotalStock()) {
            throw new IllegalStateException(
                    "El stock disponible ya es igual al stock total para el libro: " + id);
        }
        book.setAvailableStock(book.getAvailableStock() + 1);
        book.setAvailable(true);
        repository.save(BookPersistenceMapper.toEntity(book));
    }

    public void updateAvailability(String id, boolean available) {
        Book book = getBookById(id);
        book.setAvailable(available);
        repository.save(BookPersistenceMapper.toEntity(book));
    }

    /**
     * Permite al bibliotecario actualizar el stock total (y disponible si corresponde).
     * El stock total nunca puede ser <= 0.
     */
    public void updateTotalStock(String id, int newTotalStock) {
        ValidationUtil.requirePositive(newTotalStock, "El stock total debe ser mayor a 0");
        Book book = getBookById(id);
        int diff = newTotalStock - book.getTotalStock();
        book.setTotalStock(newTotalStock);

        int newAvailable = Math.max(0, book.getAvailableStock() + diff);
        book.setAvailableStock(newAvailable);
        book.setAvailable(newAvailable > 0);
        repository.save(BookPersistenceMapper.toEntity(book));
    }
}