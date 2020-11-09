package com.morlimoore.piggybankapi.util;

import java.time.LocalDate;

public class Validator {

    /**
     * This validates that the user is above 18 years
     */
    public static Boolean validateDateOfBirth(LocalDate dateOfBirth) {
        int presentYear = LocalDate.now().getYear();
        int birthYear = dateOfBirth.getYear();
        return (presentYear - birthYear >= 18);
    }
}