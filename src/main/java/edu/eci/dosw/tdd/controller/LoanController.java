package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.controller.mapper.LoanMapper;
import edu.eci.dosw.tdd.core.service.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans")
@Tag(name = "Préstamos", description = "Gestión de préstamos de libros")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }


    @Operation(summary = "Prestar un libro a un usuario")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN')")
    public void loanBook(@RequestParam String userId,
                         @RequestParam String bookId) {
        service.loanBook(userId, bookId);
    }



    @Operation(summary = "Listar todos los préstamos (Solo librarian)")
    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<List<LoanDTO>> getAll() {
        List<LoanDTO> result = service.getAllLoans()
                .stream()
                .map(LoanMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Consultar mis préstamos (USER ve solo los suyos)")
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<LoanDTO>> getMyLoans(Authentication auth) {
        // El subject del token es el userId
        String userId = (String) auth.getPrincipal();
        List<LoanDTO> result = service.getLoansByUser(userId)
                .stream()
                .map(LoanMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Devolver un libro prestado")
    @PostMapping("/return")
    @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN')")
    public ResponseEntity<Void> returnBook(@RequestParam String loanId) {
        service.returnBook(loanId);
        return ResponseEntity.ok().build();
    }
}