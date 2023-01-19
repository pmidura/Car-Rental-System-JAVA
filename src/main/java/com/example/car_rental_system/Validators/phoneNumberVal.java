package com.example.car_rental_system.Validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is for Phone number validation
 *
 * @author Patryk Midura
 */
public class phoneNumberVal {
    /**
     * This method is for checking validation
     *
     * @param s Phone number
     * @return Checks if the Phone number is 9 characters long
     */
    public static boolean isValid(String s) {
        Pattern phone = Pattern.compile("^\\d{9}$");
        Matcher match = phone.matcher(s);

        return (match.matches());
    }
}
