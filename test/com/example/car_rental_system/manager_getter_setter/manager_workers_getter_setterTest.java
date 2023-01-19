package com.example.car_rental_system.manager_getter_setter;

import com.example.car_rental_system.Validators.peselVal;
import com.example.car_rental_system.Validators.phoneNumberVal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class manager_workers_getter_setterTest {

    @Test
    void setMimie() {
        String imie = "Bartek";
        String expected = "Bartek";
        Assertions.assertEquals(imie, expected);
    }

    @Test
    void setMnazwisko() {
        String nazwisko = "Nowak";
        String expected = "Nowak";
        Assertions.assertEquals(nazwisko, expected);
    }

    @Test
    void setMpesel() {
        String pesel = "12345678987654";
        String expected = "12345678987";
        if (!peselVal.isValid(pesel)) {
            System.out.println("Nieprawidlowa liczba znakow! PESEL powinien skladac sie tylko i wylacznie z 11 cyfr!");
        }
        else {
            Assertions.assertEquals(pesel, expected);
        }
    }

    @Test
    void setMlogin() {
        String login = "bartek1";
        String expected = "bartek1";
        Assertions.assertEquals(login, expected);
    }

    @Test
    void setMhaslo() {
        String login = "bartek1";
        String expected = "bartek1";
        Assertions.assertEquals(login, expected);
    }

    @Test
    void setMtelefon() {
        String tel = "112233445566778899";
        String expected = "123456789";
        if (!phoneNumberVal.isValid(tel)) {
            System.out.println("Nieprawidlowa liczba znakow! Nr telefonu powinien skladac sie tylko i wylacznie z 9 cyfr!");
        }
        else {
            Assertions.assertEquals(tel, expected);
        }
    }
    }
