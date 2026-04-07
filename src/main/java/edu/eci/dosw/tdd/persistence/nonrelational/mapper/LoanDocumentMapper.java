package edu.eci.dosw.tdd.persistence.nonrelational.mapper;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.nonrelational.document.LoanDocument;

public class LoanDocumentMapper {

    private LoanDocumentMapper() {}

    public static Loan toModel(LoanDocument doc, Book book, User user) {
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
        LoanDocument doc = new LoanDocument();
        doc.setId(loan.getId());
        doc.setUserId(loan.getUser().getId());
        doc.setBookId(loan.getBook().getId());
        doc.setLoanDate(loan.getLoanDate());
        doc.setReturnDate(loan.getReturnDate());
        doc.setStatus(loan.getStatus());

        // Agrega entrada al historial
        LoanDocument.LoanHistory entry = new LoanDocument.LoanHistory(
                loan.getStatus(),
                loan.getLoanDate() != null ? loan.getLoanDate() : java.time.LocalDate.now()
        );
        doc.getHistory().add(entry);

        return doc;
    }
}