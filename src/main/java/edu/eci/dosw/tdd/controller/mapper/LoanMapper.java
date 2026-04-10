package edu.eci.dosw.tdd.controller.mapper;

import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.core.model.Loan;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class LoanMapper {

    public LoanDTO toDTO(Loan loan) {
        var history = loan.getHistory() == null ? Collections.<LoanDTO.HistoryDTO>emptyList() :
                loan.getHistory().stream()
                        .map(h -> new LoanDTO.HistoryDTO(h.getStatus(), h.getExecutedAt()))
                        .collect(Collectors.toList());

        return new LoanDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getUser().getId(),
                loan.getLoanDate(),
                loan.getStatus(),
                loan.getReturnDate(),
                history
        );
    }
}