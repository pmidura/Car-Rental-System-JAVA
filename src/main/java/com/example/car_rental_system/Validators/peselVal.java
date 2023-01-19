package com.example.car_rental_system.Validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is for PESEL validation
 *
 * @author Patryk Midura
 */
public class peselVal {
    /**
     * This method is for checking validation
     *
     * @param s PESEL number
     * @return Checks if the PESEL is 11 characters long
     */
    public static boolean isValid(String s) {
        Pattern pesel = Pattern.compile("^\\d{11}$");
        Matcher match = pesel.matcher(s);

        return (match.matches());
    }
}
