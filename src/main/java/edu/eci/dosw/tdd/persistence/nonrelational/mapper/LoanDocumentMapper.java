package edu.eci.dosw.tdd.persistence.nonrelational.mapper;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.nonrelational.document.LoanDocument;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanDocumentMapper {

    private LoanDocumentMapper() {}

    public static Loan toModel(LoanDocument doc, Book book, User user) {
        List<Loan.LoanHistory> history = doc.getHistory() == null
                ? new ArrayList<>()
                : doc.getHistory().stream()
                .map(h -> new Loan.LoanHistory(h.getStatus(), h.getExecutedAt()))
                .collect(Collectors.toList());

        return new Loan(
                doc.getId(),
                book,
                user,
                doc.getLoanDate(),
                doc.getStatus(),
                doc.getReturnDate()
        );
    }

    public static LoanDocument toDocument(Loan loan) {
        List<LoanDocument.LoanHistory> historyDocs = loan.getHistory() == null
                ? new ArrayList<>()
                : loan.getHistory().stream()
                .map(h -> new LoanDocument.LoanHistory(h.getStatus(), h.getExecutedAt()))
                .collect(Collectors.toList());

        LoanDocument doc = new LoanDocument();
        doc.setId(loan.getId());
        doc.setUserId(loan.getUser().getId());
        doc.setBookId(loan.getBook().getId());
        doc.setLoanDate(loan.getLoanDate());
        doc.setReturnDate(loan.getReturnDate());
        doc.setStatus(loan.getStatus());
        doc.setHistory(historyDocs);
        return doc;
    }
}