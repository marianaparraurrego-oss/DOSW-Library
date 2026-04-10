package edu.eci.dosw.tdd.core.model;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

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
}