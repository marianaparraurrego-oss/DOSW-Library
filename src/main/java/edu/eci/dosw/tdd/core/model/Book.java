package edu.eci.dosw.tdd.core.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String id;
    private String title;
    private String author;
    private boolean available;
    private int totalStock;
    private int availableStock;
}