package edu.eci.dosw.tdd.persistence.nonrelational.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDocument {

    @Id
    private String id;
    private String title;
    private String author;
    private boolean available;
    private int totalStock;
    private int availableStock;

    // Extendidos Parte 3
    private List<String> categories;
    private String publicationType;
    private LocalDate publishedDate;
    private String isbn;
    private Metadata metadata;
    private String availabilityStatus;
    private int borrowedCopies;
    private LocalDate addedAt;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Metadata {
        private int pages;
        private String language;
        private String publisher;
    }
}