package edu.eci.dosw.tdd.core.model;


import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Loan {
    private String id;
    private Book book;
    private User user;
    private LocalDate loanDate;
    private String status;
    private LocalDate returnDate;

    public Loan(String id, Book book, User user) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = LocalDate.now();
        this.status = "ACTIVE";
    }

    // Constructor completo para el mapper
    public Loan(String id, Book book, User user,
                LocalDate loanDate, String status, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.status = status;
        this.returnDate = returnDate;
    }

    public void returnBook() {
        this.status = "RETURNED";
        this.returnDate = LocalDate.now();
    }
}