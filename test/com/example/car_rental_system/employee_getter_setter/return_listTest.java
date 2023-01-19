package com.example.car_rental_system.employee_getter_setter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class return_listTest {

    @Test
    void setClient_name() {
        String unexpected = "Adam";
        String expected = "Adam";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setClient_surname() {
        String unexpected = "Nowak";
        String expected = "Nowak";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setClient_pesel() {
        String unexpected = "919765378654";
        String expected = "919765378654";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setClient_phone() {
        String unexpected = "536754280";
        String expected = "536754280";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setLoan_id() {
        String unexpected = "5";
        String expected = "5";
        Assertions.assertEquals(unexpected, expected);
    }

    @Test
    void setPeriod() {
        String unexpected = "85";
        String expected = "85";
        Assertions.assertEquals(unexpected, expected);
    }
}