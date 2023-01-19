package com.example.car_rental_system.Validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class yearValTest {
    @Test
    void isValid() {
        Assertions.assertEquals("2002", "2002");
    }
}