package com.example.car_rental_system.Validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class priceVal {
        /**
         * This method is for checking validation
         *
         * @param s Year number
         * @return Checks if the Year is 4 characters long
         */
        public static boolean isValid(String s) {
            Pattern price = Pattern.compile("^(\\d+(.\\d{0,2})?|.?\\d{1,2})$");
            Matcher match = price.matcher(s);

            return (match.matches());
        }
}
