package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.controller.mapper.LoanMapper;
import edu.eci.dosw.tdd.core.service.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void loanBook(@RequestParam String userId,
                         @RequestParam String bookId) {
        service.loanBook(userId, bookId);
    }


    @Operation(summary = "Listar todos los préstamos")
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAll() {
        List<LoanDTO> result = service.getAllLoans()
                .stream()
                .map(LoanMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Devolver un libro prestado")
    @PostMapping("/return")
    public ResponseEntity<Void> returnBook(@RequestParam String loanId) {
        service.returnBook(loanId);
        return ResponseEntity.ok().build();
    }
}