package edu.eci.dosw.tdd.persistence.nonrelational.mapper;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.persistence.nonrelational.document.BookDocument;

public class BookDocumentMapper {

    private BookDocumentMapper() {}

    public static Book toModel(BookDocument doc) {
        int totalStock     = doc.getAvailability() != null ? doc.getAvailability().getTotalCopies() : 0;
        int availableStock = doc.getAvailability() != null ? doc.getAvailability().getAvailableCopies() : 0;
        return new Book(
                doc.getId(),
                doc.getTitle(),
                doc.getAuthor(),
                doc.isAvailable(),
                totalStock,
                availableStock
        );
    }

    public static BookDocument toDocument(Book book) {
        BookDocument doc = new BookDocument();
        doc.setId(book.getId());
        doc.setTitle(book.getTitle());
        doc.setAuthor(book.getAuthor());
        doc.setAvailable(book.isAvailable());

        BookDocument.Availability availability = new BookDocument.Availability(
                book.isAvailable() ? "AVAILABLE" : "UNAVAILABLE",
                book.getTotalStock(),
                book.getAvailableStock(),
                book.getTotalStock() - book.getAvailableStock()
        );
        doc.setAvailability(availability);

        return doc;
    }
}