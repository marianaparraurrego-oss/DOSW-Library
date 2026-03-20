package edu.eci.dosw.tdd.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateUtil() {}


    public static LocalDate today() {
        return LocalDate.now();
    }


    public static String format(LocalDate date) {
        if (date == null) {
            return "N/A";
        }
        return date.format(FORMATTER);
    }


    public static long daysSince(LocalDate date) {
        if (date == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(date, LocalDate.now());
    }


    public static boolean isOverdue(LocalDate loanDate, int days) {
        if (loanDate == null) {
            return false;
        }
        return daysSince(loanDate) > days;
    }
}