package edu.eci.dosw.tdd.persistence.relational.mapper;

import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.persistence.relational.entity.LoanEntity;

public class LoanPersistenceMapper {

    private LoanPersistenceMapper() {}

    public static Loan toModel(LoanEntity entity) {
        return new Loan(
                entity.getId(),
                BookPersistenceMapper.toModel(entity.getBook()),
                UserPersistenceMapper.toModel(entity.getUser()),
                entity.getLoanDate(),
                entity.getStatus(),
                entity.getReturnDate()
        );
    }

    public static LoanEntity toEntity(Loan loan) {
        return new LoanEntity(
                loan.getId(),
                BookPersistenceMapper.toEntity(loan.getBook()),
                UserPersistenceMapper.toEntity(loan.getUser()),
                loan.getLoanDate(),
                loan.getStatus(),
                loan.getReturnDate()
        );
    }
}