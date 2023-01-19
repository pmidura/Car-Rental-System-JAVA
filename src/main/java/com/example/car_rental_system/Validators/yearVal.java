package com.example.car_rental_system.Validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class yearVal {
    /**
     * This method is for checking validation
     *
     * @param s Year number
     * @return Checks if the Year is 4 characters long
     */
    public static boolean isValid(String s) {
        Pattern rok = Pattern.compile("^\\d{4}$");
        Matcher match = rok.matcher(s);

        return (match.matches());
    }
}
