package edu.eci.dosw.tdd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.*;

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
}
