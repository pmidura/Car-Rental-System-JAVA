package com.example.car_rental_system.employee_getter_setter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class wished_cars {
    private final IntegerProperty wished_id, peroid, car_id;
    private final StringProperty name, surname, pesel, phone_nr;


    public wished_cars(int wished_id, String name, String surname, String pesel, String phone_nr, int peroid, int car_id) {
        this.wished_id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.pesel = new SimpleStringProperty();
        this.phone_nr = new SimpleStringProperty();
        this.peroid = new SimpleIntegerProperty();
        this.car_id = new SimpleIntegerProperty();


        setWished_id(wished_id);
        setName(name);
        setSurname(surname);
        setPesel(pesel);
        setPhone_nr(phone_nr);
        setPeroid(peroid);
        setCar_id(car_id);
    }

    public int getWished_id() {
        return wished_id.get();
    }

    public IntegerProperty wished_idProperty() {
        return wished_id;
    }

    public void setWished_id(int wished_id) {
        this.wished_id.set(wished_id);
    }

    public int getPeroid() {
        return peroid.get();
    }

    public IntegerProperty peroidProperty() {
        return peroid;
    }

    public void setPeroid(int peroid) {
        this.peroid.set(peroid);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPesel() {
        return pesel.get();
    }

    public StringProperty peselProperty() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel.set(pesel);
    }

    public String getPhone_nr() {
        return phone_nr.get();
    }

    public StringProperty phone_nrProperty() {
        return phone_nr;
    }

    public void setPhone_nr(String phone_nr) {
        this.phone_nr.set(phone_nr);
    }
    public int getCar_id() {
        return car_id.get();
    }

    public IntegerProperty car_idProperty() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id.set(car_id);
    }
}



