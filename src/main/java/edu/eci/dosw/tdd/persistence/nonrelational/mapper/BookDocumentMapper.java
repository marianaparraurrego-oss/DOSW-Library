package edu.eci.dosw.tdd.persistence.nonrelational.mapper;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Metadata;
import edu.eci.dosw.tdd.persistence.nonrelational.document.BookDocument;

public class BookDocumentMapper {

    private BookDocumentMapper() {}

    public static Book toModel(BookDocument doc) {
        Metadata metadata = null;
        if (doc.getMetadata() != null) {
            metadata = new Metadata(
                    doc.getMetadata().getPages(),
                    doc.getMetadata().getLanguage(),
                    doc.getMetadata().getPublisher()
            );
        }

        return Book.builder()
                .id(doc.getId())
                .title(doc.getTitle())
                .author(doc.getAuthor())
                .available(doc.isAvailable())
                .totalStock(doc.getTotalStock())
                .availableStock(doc.getAvailableStock())
                .categories(doc.getCategories())
                .publicationType(doc.getPublicationType())
                .publishedDate(doc.getPublishedDate())
                .isbn(doc.getIsbn())
                .metadata(metadata)
                .availabilityStatus(doc.getAvailabilityStatus())
                .borrowedCopies(doc.getBorrowedCopies())
                .addedAt(doc.getAddedAt())
                .build();
    }

    public static BookDocument toDocument(Book book) {
        BookDocument.Metadata metadataDoc = null;
        if (book.getMetadata() != null) {
            metadataDoc = new BookDocument.Metadata(
                    book.getMetadata().getPages(),
                    book.getMetadata().getLanguage(),
                    book.getMetadata().getPublisher()
            );
        }

        BookDocument doc = new BookDocument();
        doc.setId(book.getId());
        doc.setTitle(book.getTitle());
        doc.setAuthor(book.getAuthor());
        doc.setAvailable(book.isAvailable());
        doc.setTotalStock(book.getTotalStock());
        doc.setAvailableStock(book.getAvailableStock());
        doc.setCategories(book.getCategories());
        doc.setPublicationType(book.getPublicationType());
        doc.setPublishedDate(book.getPublishedDate());
        doc.setIsbn(book.getIsbn());
        doc.setMetadata(metadataDoc);
        doc.setAvailabilityStatus(book.getAvailabilityStatus());
        doc.setBorrowedCopies(book.getBorrowedCopies());
        doc.setAddedAt(book.getAddedAt());
        return doc;
    }
}