package edu.eci.dosw.tdd.persistence.relational.repository;


import edu.eci.dosw.tdd.persistence.relational.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLoanRepository extends JpaRepository<LoanEntity,String> {
    List<LoanEntity> findByUser_IdAndStatus(String userId, String status);
    long countByUser_IdAndStatus(String userId, String status);
    List<LoanEntity> findByUser_Id(String userId);
}
