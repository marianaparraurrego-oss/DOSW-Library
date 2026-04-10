package edu.eci.dosw.tdd.persistence.relational.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private boolean available;

    @Column(nullable = false)
    private int totalStock;

    @Column(nullable = false)
    private int availableStock;

    // Extendidos Parte 3 — categories como CSV: "ficcion,drama"
    private String categories;
    private String publicationType;
    private LocalDate publishedDate;

    @Column(unique = true)
    private String isbn;

    // Metadata embebida
    @Embedded
    private MetadataEmbeddable metadata;

    private String availabilityStatus;
    private int borrowedCopies;
    private LocalDate addedAt;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MetadataEmbeddable {
        private int pages;
        private String language;
        private String publisher;
    }
}