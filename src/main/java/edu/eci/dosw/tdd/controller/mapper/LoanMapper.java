package edu.eci.dosw.tdd.controller.mapper;

import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.core.model.Loan;

public class LoanMapper {

    public static LoanDTO toDTO(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getUser().getId(),
                loan.getLoanDate(),
                loan.getStatus(),
                loan.getReturnDate()
        );
    }
}