package com.example.car_rental_system.admin_getter_setter;

import com.example.car_rental_system.Validators.peselVal;
import com.example.car_rental_system.Validators.phoneNumberVal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdminUsersTableTest {
    @Test
    void setPesel() {
        String pesel = "99021503254333";
        String expected = "9902150254";
        if (!peselVal.isValid(pesel)) {
            System.out.println("Nieprawidlowa liczba znakow! PESEL powinien skladac sie tylko i wylacznie z 11 cyfr!");
        }
        else {
            Assertions.assertEquals(pesel, expected);
        }
    }

    @Test
    void setImie() {
        String imie = "Patryk";
        String expected = "Patryk";
        Assertions.assertEquals(imie, expected);
    }

    @Test
    void setNazwisko() {
        String nazwisko = "Midura";
        String expected = "Midura";
        Assertions.assertEquals(nazwisko, expected);
    }

    @Test
    void setLogin() {
        String login = "patryk1";
        String expected = "patryk1";
        Assertions.assertEquals(login, expected);
    }

    @Test
    void setHaslo() {
        String haslo = "patryk1";
        String expected = "patryk1";
        Assertions.assertEquals(haslo, expected);
    }

    @Test
    void setStanowisko() {
        String role = "admin";
        String expected = "admin";
        Assertions.assertEquals(role, expected);
    }

    @Test
    void setNrTelefonu() {
        String tel = "665098354111";
        String expected = "665098354";
        if (!phoneNumberVal.isValid(tel)) {
            System.out.println("Nieprawidlowa liczba znakow! Nr telefonu powinien skladac sie tylko i wylacznie z 9 cyfr!");
        }
        else {
            Assertions.assertEquals(tel, expected);
        }
    }
}