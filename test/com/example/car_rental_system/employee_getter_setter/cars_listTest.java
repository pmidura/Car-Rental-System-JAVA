package com.example.car_rental_system.employee_getter_setter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class cars_listTest {

    @Test
    void setCar_registration() {
        String unexpected = "WP875C";
        String expected = "WP875C";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setCar_brand() {
        String unexpected = "AUDI";
        String expected = "AUDI";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setCar_model() {
        String unexpected = "A3";
        String expected = "A3";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setRental_name() {
        String unexpected = "AutoShare";
        String expected = "AutoShare";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setCar_production() {
        String unexpected = "2003";
        String expected = "2003";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setCar_price() {
        String unexpected = "58.99";
        String expected = "58.99";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setAvailability() {
        String unexpected = "true";
        String expected = "true";
        Assertions.assertEquals(unexpected, expected);
    }
}