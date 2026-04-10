package edu.eci.dosw.tdd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    @NotBlank
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private int totalStock;
    private int availableStock;

    // Extendidos Parte 3
    private List<String> categories;
    private String publicationType;
    private LocalDate publishedDate;
    private String isbn;
    private MetadataDTO metadata;
    private String availabilityStatus;
    private int borrowedCopies;
    private LocalDate addedAt;
}