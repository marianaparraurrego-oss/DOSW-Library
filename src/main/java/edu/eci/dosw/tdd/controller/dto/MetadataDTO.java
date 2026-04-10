package edu.eci.dosw.tdd.controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetadataDTO {
    private int pages;
    private String language;
    private String publisher;
}