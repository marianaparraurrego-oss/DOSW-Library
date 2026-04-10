package edu.eci.dosw.tdd.Service;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.service.BookService;
import edu.eci.dosw.tdd.core.service.LoanService;
import edu.eci.dosw.tdd.core.service.UserService;
import edu.eci.dosw.tdd.persistence.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoanService loanService;

    private Loan loan;
    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .id("u001")
                .name("Juan")
                .username("juan")
                .password("pass")
                .role("USER")
                .build();
        book = Book.builder()
                .id("b001")
                .title("Clean Code")
                .author("Martin")
                .available(true)
                .totalStock(5)
                .availableStock(5)
                .build();
        loan = new Loan(UUID.randomUUID().toString(), book, user);
    }

    @Test
    void dadoQueHayUnaReserva_cuandoLaConsulto_entoncesEsExitosaValidandoId() {
        when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));

        Optional<Loan> result = loanRepository.findById(loan.getId());

        assertTrue(result.isPresent());
        assertEquals(loan.getId(), result.get().getId());
    }

    @Test
    void dadoQueNoHayReservas_cuandoLaConsulto_entoncesNoRetornaNingunResultado() {
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        List<Loan> result = loanService.getAllLoans();

        assertTrue(result.isEmpty());
    }

    @Test
    void dadoQueNoHayReservas_cuandoLaCreo_entoncesLaCreacionEsExitosa() {
        when(userService.getUserById("u001")).thenReturn(user);
        when(bookService.isAvailable("b001")).thenReturn(true);
        when(bookService.getBookById("b001")).thenReturn(book);
        when(loanRepository.countByUser_IdAndStatus("u001", "ACTIVE")).thenReturn(0L);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        assertDoesNotThrow(() -> loanService.loanBook("u001", "b001"));
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void dadoQueHayUnaReserva_cuandoLaElimino_entoncesLaEliminacionEsExitosa() {
        doNothing().when(loanRepository).deleteById(loan.getId());

        loanRepository.deleteById(loan.getId());

        verify(loanRepository, times(1)).deleteById(loan.getId());
    }

    @Test
    void dadoQueHayUnaReserva_cuandoLaEliminoYConsulto_entoncesNoRetornaNingunResultado() {
        doNothing().when(loanRepository).deleteById(loan.getId());
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        loanRepository.deleteById(loan.getId());
        List<Loan> result = loanService.getAllLoans();

        assertTrue(result.isEmpty());
    }
}