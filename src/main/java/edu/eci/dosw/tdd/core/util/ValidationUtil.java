package edu.eci.dosw.tdd.core.util;


public class ValidationUtil {

    private ValidationUtil() {}


    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }


    public static void requireNonBlank(String value, String errorMessage) {
        if (isBlank(value)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


    public static boolean isNonNegative(int value) {
        return value >= 0;
    }


    public static void requireNonNegative(int value, String errorMessage) {
        if (!isNonNegative(value)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


    public static boolean isPositive(int value) {
        return value > 0;
    }


    public static void requirePositive(int value, String errorMessage) {
        if (!isPositive(value)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}