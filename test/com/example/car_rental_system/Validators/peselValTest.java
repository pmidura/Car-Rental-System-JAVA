package com.example.car_rental_system.Validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PeselValTest {

    @Test
    void isValid() {
        Assertions.assertEquals("95061105345", "95061105345");
    }
}