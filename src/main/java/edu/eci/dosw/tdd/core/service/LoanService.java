package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private static final int MAX_LOANS_PER_USER = 3;

    private List<Loan> loans = new ArrayList<>();

    private final BookService bookService;
    private final UserService userService;

    public LoanService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }


    public void loanBook(String userId, String bookId) {

        User user = userService.getUserById(userId);

        if (!bookService.isAvailable(bookId)) {
            throw new BookNotAvailableException("No hay ejemplares disponibles del libro: " + bookId);
        }

        long activeLoans = loans.stream()
                .filter(l -> l.getUser().getId().equals(userId))
                .filter(l -> "ACTIVE".equals(l.getStatus()))
                .count();

        if (activeLoans >= MAX_LOANS_PER_USER) {
            throw new LoanLimitExceededException(
                    "El usuario ya tiene " + MAX_LOANS_PER_USER + " préstamos activos. Debe devolver un libro primero."
            );
        }

        Book book = bookService.getBookById(bookId);
        Loan loan = new Loan(UUID.randomUUID().toString(), book, user);
        loans.add(loan);


        bookService.decreaseStock(bookId);
    }


    public void returnBook(String loanId) {

        Loan loan = loans.stream()
                .filter(l -> l.getId().equals(loanId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + loanId));

        loan.returnBook();

        // Devolver el ejemplar al stock
        bookService.increaseStock(loan.getBook().getId());
    }


    public List<Loan> getAllLoans() {
        return loans;
    }


    public List<Loan> getActiveLoansByUser(String userId) {
        return loans.stream()
                .filter(l -> l.getUser().getId().equals(userId))
                .filter(l -> "ACTIVE".equals(l.getStatus()))
                .collect(Collectors.toList());
    }
}