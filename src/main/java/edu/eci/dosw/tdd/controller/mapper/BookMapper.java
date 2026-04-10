package edu.eci.dosw.tdd.controller.mapper;

import edu.eci.dosw.tdd.controller.dto.BookDTO;
import edu.eci.dosw.tdd.controller.dto.MetadataDTO;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Metadata;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toModel(BookDTO dto) {
        Metadata metadata = null;
        if (dto.getMetadata() != null) {
            metadata = new Metadata(
                    dto.getMetadata().getPages(),
                    dto.getMetadata().getLanguage(),
                    dto.getMetadata().getPublisher()
            );
        }
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .available(true)
                .totalStock(dto.getTotalStock())
                .availableStock(dto.getAvailableStock())
                .categories(dto.getCategories())
                .publicationType(dto.getPublicationType())
                .publishedDate(dto.getPublishedDate())
                .isbn(dto.getIsbn())
                .metadata(metadata)
                .availabilityStatus(dto.getAvailabilityStatus())
                .borrowedCopies(dto.getBorrowedCopies())
                .addedAt(dto.getAddedAt())
                .build();
    }

    public BookDTO toDTO(Book book) {
        MetadataDTO metadataDTO = null;
        if (book.getMetadata() != null) {
            metadataDTO = new MetadataDTO(
                    book.getMetadata().getPages(),
                    book.getMetadata().getLanguage(),
                    book.getMetadata().getPublisher()
            );
        }
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getTotalStock(),
                book.getAvailableStock(),
                book.getCategories(),
                book.getPublicationType(),
                book.getPublishedDate(),
                book.getIsbn(),
                metadataDTO,
                book.getAvailabilityStatus(),
                book.getBorrowedCopies(),
                book.getAddedAt()
        );
    }
}