package edu.eci.dosw.tdd.core.model;

import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {

    private String id;
    private Book book;
    private User user;
    private LocalDate loanDate;
    private String status;
    private LocalDate returnDate;

    @Builder.Default
    private List<LoanHistory> history = new ArrayList<>();

    public Loan(String id, Book book, User user) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = LocalDate.now();
        this.status = "ACTIVE";
        this.history = new ArrayList<>();
        this.history.add(new LoanHistory("ACTIVE", LocalDate.now()));
    }

    public Loan(String id, Book book, User user,
                LocalDate loanDate, String status, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.status = status;
        this.returnDate = returnDate;
        this.history = new ArrayList<>();
    }

    public void returnBook() {
        this.status = "RETURNED";
        this.returnDate = LocalDate.now();
        this.history.add(new LoanHistory("RETURNED", LocalDate.now()));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoanHistory {
        private String status;
        private LocalDate executedAt;
    }
}