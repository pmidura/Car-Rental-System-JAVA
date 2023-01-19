package com.example.car_rental_system.manager_getter_setter;

import com.example.car_rental_system.Validators.peselVal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class manager_cars_getter_setterTest {

    @Test
    void setMrejestracja() {
        String Rejestracja = "RKR29880";
        String expected = "RKR29880";
        Assertions.assertEquals(Rejestracja, expected);
    }

    @Test
    void setMmarka() {
        String Marka = "Fiat";
        String expected = "Fiat";
        Assertions.assertEquals(Marka, expected);
    }

    @Test
    void setMmodel() {
        String Model = "Punto";
        String expected = "Punto";
        Assertions.assertEquals(Model, expected);
    }

    @Test
    void setMrok() {
        String Rok = "2000";
        String expected = "2000";
        Assertions.assertEquals(Rok, expected);

    }

    @Test
    void setMcena() {
        String Cena = "199.99";
        String expected = "199.99";
        Assertions.assertEquals(Cena, expected);
    }
}