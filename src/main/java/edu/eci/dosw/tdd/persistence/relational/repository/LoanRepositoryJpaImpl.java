package edu.eci.dosw.tdd.persistence.relational.repository;

import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.persistence.relational.mapper.LoanPersistenceMapper;
import edu.eci.dosw.tdd.persistence.repository.LoanRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("relational")
public class LoanRepositoryJpaImpl implements LoanRepository {

    private final JpaLoanRepository repository;

    public LoanRepositoryJpaImpl(JpaLoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        return LoanPersistenceMapper.toModel(
                repository.save(LoanPersistenceMapper.toEntity(loan))
        );
    }

    @Override
    public Optional<Loan> findById(String id) {
        return repository.findById(id).map(LoanPersistenceMapper::toModel);
    }

    @Override
    public List<Loan> findAll() {
        return repository.findAll()
                .stream()
                .map(LoanPersistenceMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Loan> findByUser_IdAndStatus(String userId, String status) {
        return repository.findByUser_IdAndStatus(userId, status)
                .stream()
                .map(LoanPersistenceMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public long countByUser_IdAndStatus(String userId, String status) {
        return repository.countByUser_IdAndStatus(userId, status);
    }

    @Override
    public List<Loan> findByUser_Id(String userId) {
        return repository.findByUser_Id(userId)
                .stream()
                .map(LoanPersistenceMapper::toModel)
                .collect(Collectors.toList());
    }
}