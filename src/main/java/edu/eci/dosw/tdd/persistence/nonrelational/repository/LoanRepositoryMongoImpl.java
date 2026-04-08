package edu.eci.dosw.tdd.persistence.nonrelational.repository;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.nonrelational.mapper.LoanDocumentMapper;
import edu.eci.dosw.tdd.persistence.nonrelational.document.LoanDocument;
import edu.eci.dosw.tdd.persistence.repository.LoanRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("mongo")
public class LoanRepositoryMongoImpl implements LoanRepository {

    private final MongoLoanRepository loanRepo;
    private final MongoBookRepository bookRepo;
    private final MongoUserRepository userRepo;

    public LoanRepositoryMongoImpl(MongoLoanRepository loanRepo,
                                   MongoBookRepository bookRepo,
                                   MongoUserRepository userRepo) {
        this.loanRepo = loanRepo;
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Loan save(Loan loan) {
        LoanDocument saved = loanRepo.save(LoanDocumentMapper.toDocument(loan));
        return toModel(saved);
    }

    @Override
    public Optional<Loan> findById(String id) {
        return loanRepo.findById(id).map(this::toModel);
    }

    @Override
    public List<Loan> findAll() {
        return loanRepo.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        loanRepo.deleteById(id);
    }

    @Override
    public List<Loan> findByUser_IdAndStatus(String userId, String status) {
        return loanRepo.findByUserIdAndStatus(userId, status).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public long countByUser_IdAndStatus(String userId, String status) {
        return loanRepo.countByUserIdAndStatus(userId, status);
    }

    @Override
    public List<Loan> findByUser_Id(String userId) {
        return loanRepo.findByUserId(userId).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    // Resuelve las referencias por ID buscando book y user en sus colecciones
    private Loan toModel(LoanDocument doc) {
        Book book = bookRepo.findById(doc.getBookId())
                .map(edu.eci.dosw.tdd.persistence.nonrelational.mapper.BookDocumentMapper::toModel)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + doc.getBookId()));
        User user = userRepo.findById(doc.getUserId())
                .map(edu.eci.dosw.tdd.persistence.nonrelational.mapper.UserDocumentMapper::toModel)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + doc.getUserId()));
        return LoanDocumentMapper.toModel(doc, book, user);
    }
}