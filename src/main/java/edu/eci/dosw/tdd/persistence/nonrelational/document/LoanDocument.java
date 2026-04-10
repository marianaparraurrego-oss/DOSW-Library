package edu.eci.dosw.tdd.persistence.nonrelational.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "loans")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDocument {

    @Id
    private String id;
    private String userId;
    private String bookId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private String status;

    private List<LoanHistory> history = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoanHistory {
        private String status;
        private LocalDate executedAt;
    }
}