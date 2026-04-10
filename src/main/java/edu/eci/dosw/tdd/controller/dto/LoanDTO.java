package edu.eci.dosw.tdd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {

    private String id;

    @NotBlank
    private String bookId;

    @NotBlank
    private String userId;

    private LocalDate loanDate;
    private String status;
    private LocalDate returnDate;

    // Extendido Parte 3
    private List<HistoryDTO> history;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HistoryDTO {
        private String status;
        private LocalDate executedAt;
    }
}