package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.BookDTO;
import edu.eci.dosw.tdd.controller.mapper.BookMapper;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Tag(name = "Libros", description = "Operaciones sobre el catálogo de libros")
@SecurityRequirement(name = "bearerAuth")
public class BookController {

    private final BookService service;
    private final BookMapper bookMapper;

    public BookController(BookService service, BookMapper bookMapper) {
        this.service = service;
        this.bookMapper = bookMapper;
    }

    @Operation(summary = "Agregar un libro al catálogo")
    @PostMapping
    public ResponseEntity<Void> addBook(@Valid @RequestBody BookDTO dto,
                                        @RequestParam int quantity) {
        Book book = bookMapper.toModel(dto);
        service.addBook(book, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Obtener todos los libros")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        List<BookDTO> result = service.getAllBooks()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener un libro por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(bookMapper.toDTO(service.getBookById(id)));
    }

    @Operation(summary = "Actualizar disponibilidad de un libro")
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateAvailability(@PathVariable String id,
                                                   @RequestParam boolean available) {
        service.updateAvailability(id, available);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar el stock total de un libro")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateTotalStock(@PathVariable String id,
                                                 @RequestParam int totalStock) {
        service.updateTotalStock(id, totalStock);
        return ResponseEntity.ok().build();
    }
}