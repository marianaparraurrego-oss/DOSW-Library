package edu.eci.dosw.tdd.persistence.repository;

import edu.eci.dosw.tdd.core.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(String id);
    List<Book> findAll();
    void deleteById(String id);
}