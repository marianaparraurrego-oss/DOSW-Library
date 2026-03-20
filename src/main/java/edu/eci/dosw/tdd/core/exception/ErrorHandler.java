package edu.eci.dosw.tdd.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice(basePackages = "edu.eci.dosw.tdd.controller")
public class ErrorHandler {

    @ExceptionHandler(LoanLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleLoanLimit(LoanLimitExceededException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFound(UserNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(BookNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBookNotAvailable(BookNotAvailableException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleBookNotFound(BookNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGeneral(Exception ex) {
        return Map.of("error", ex.getMessage());
    }
}