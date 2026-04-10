package edu.eci.dosw.tdd.persistence.relational.mapper;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Metadata;
import edu.eci.dosw.tdd.persistence.relational.entity.BookEntity;

import java.util.Arrays;
import java.util.List;

public class BookPersistenceMapper {

    private BookPersistenceMapper() {}

    public static Book toModel(BookEntity entity) {
        Metadata metadata = null;
        if (entity.getMetadata() != null) {
            metadata = new Metadata(
                    entity.getMetadata().getPages(),
                    entity.getMetadata().getLanguage(),
                    entity.getMetadata().getPublisher()
            );
        }

        List<String> categories = null;
        if (entity.getCategories() != null && !entity.getCategories().isBlank()) {
            categories = Arrays.asList(entity.getCategories().split(","));
        }

        return Book.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .available(entity.isAvailable())
                .totalStock(entity.getTotalStock())
                .availableStock(entity.getAvailableStock())
                .categories(categories)
                .publicationType(entity.getPublicationType())
                .publishedDate(entity.getPublishedDate())
                .isbn(entity.getIsbn())
                .metadata(metadata)
                .availabilityStatus(entity.getAvailabilityStatus())
                .borrowedCopies(entity.getBorrowedCopies())
                .addedAt(entity.getAddedAt())
                .build();
    }

    public static BookEntity toEntity(Book book) {
        BookEntity.MetadataEmbeddable metadataEntity = null;
        if (book.getMetadata() != null) {
            metadataEntity = new BookEntity.MetadataEmbeddable(
                    book.getMetadata().getPages(),
                    book.getMetadata().getLanguage(),
                    book.getMetadata().getPublisher()
            );
        }

        String categories = null;
        if (book.getCategories() != null && !book.getCategories().isEmpty()) {
            categories = String.join(",", book.getCategories());
        }

        return new BookEntity(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.isAvailable(),
                book.getTotalStock(),
                book.getAvailableStock(),
                categories,
                book.getPublicationType(),
                book.getPublishedDate(),
                book.getIsbn(),
                metadataEntity,
                book.getAvailabilityStatus(),
                book.getBorrowedCopies(),
                book.getAddedAt()
        );
    }
}