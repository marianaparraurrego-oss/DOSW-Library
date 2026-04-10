package edu.eci.dosw.tdd.core.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Metadata {
    private int pages;
    private String language;
    private String publisher;
}