package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.BookNotAvailableException;
import edu.eci.dosw.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.mapper.LoanPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private static final int MAX_LOANS_PER_USER = 3;

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final UserService userService;

    public LoanService(BookService bookService, UserService userService, LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    public void loanBook(String userId, String bookId) {
        User user = userService.getUserById(userId);

        if (!bookService.isAvailable(bookId)) {
            throw new BookNotAvailableException("No hay ejemplares disponibles del libro: " + bookId);
        }

        long activeLoans = loanRepository.countByUser_IdAndStatus(userId, "ACTIVE");
        if (activeLoans >= MAX_LOANS_PER_USER) {
            throw new LoanLimitExceededException(
                    "El usuario ya tiene " + MAX_LOANS_PER_USER + " préstamos activos. Debe devolver un libro primero."
            );
        }

        Book book = bookService.getBookById(bookId);
        Loan loan = new Loan(UUID.randomUUID().toString(), book, user);
        loanRepository.save(LoanPersistenceMapper.toEntity(loan));

        bookService.decreaseStock(bookId);
    }

    public void returnBook(String loanId) {
        Loan loan = loanRepository.findById(loanId)
                .map(LoanPersistenceMapper::toModel)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + loanId));

        if ("RETURNED".equals(loan.getStatus())) {
            throw new IllegalStateException("Este préstamo ya fue devuelto.");
        }

        loan.returnBook();
        loanRepository.save(LoanPersistenceMapper.toEntity(loan));
        bookService.increaseStock(loan.getBook().getId());
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(LoanPersistenceMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<Loan> getActiveLoansByUser(String userId) {
        return loanRepository.findByUser_IdAndStatus(userId, "ACTIVE")
                .stream()
                .map(LoanPersistenceMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<Loan> getLoansByUser(String userId) {
        return loanRepository.findByUser_Id(userId)
                .stream()
                .map(LoanPersistenceMapper::toModel)
                .collect(Collectors.toList());
    }
}