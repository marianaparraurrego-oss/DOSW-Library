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

    // Subdocumento embebido: metadata
    private Metadata metadata;

    // Subdocumento embebido: availability
    private Availability availability;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Metadata {
        private int pages;
        private String language;
        private String publisher;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Availability {
        private String status;
        private int totalCopies;
        private int availableCopies;
        private int borrowedCopies;
    }
}