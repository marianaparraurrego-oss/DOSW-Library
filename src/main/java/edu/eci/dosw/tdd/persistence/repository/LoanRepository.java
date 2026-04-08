package edu.eci.dosw.tdd.persistence.repository;

import edu.eci.dosw.tdd.core.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(String id);
    List<Loan> findAll();
    void deleteById(String id);
    List<Loan> findByUser_IdAndStatus(String userId, String status);
    long countByUser_IdAndStatus(String userId, String status);
    List<Loan> findByUser_Id(String userId);
}