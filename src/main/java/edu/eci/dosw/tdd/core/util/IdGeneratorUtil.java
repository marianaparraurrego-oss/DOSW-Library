package edu.eci.dosw.tdd.core.util;

import java.util.UUID;


public class IdGeneratorUtil {

    private IdGeneratorUtil() {}


    public static String generate() {
        return UUID.randomUUID().toString();
    }

    public static String generateShort() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}