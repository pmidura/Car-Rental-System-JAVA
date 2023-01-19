package com.example.car_rental_system.employee_getter_setter;

import javafx.beans.property.*;


public class rental_list {
    private final StringProperty car_registration2, car_brand2, car_model2, rental_name2;
    private final DoubleProperty car_price2;

    public rental_list(String car_registration2, String car_brand2, String car_model2, String rental_name2, Double car_price2) {
        this.car_registration2 = new SimpleStringProperty();
        this.car_brand2 = new SimpleStringProperty();
        this.car_model2 = new SimpleStringProperty();
        this.rental_name2 = new SimpleStringProperty();
        this.car_price2 = new SimpleDoubleProperty();

        setCar_registration2(car_registration2);
        setCar_brand2(car_brand2);
        setCar_model2(car_model2);
        setRental_name2(rental_name2);
        setCar_price2(car_price2);
    }

    public String getCar_registration2() {
        return car_registration2.get();
    }

    public StringProperty car_registration2Property() {
        return car_registration2;
    }

    public void setCar_registration2(String car_registration2) {
        this.car_registration2.set(car_registration2);
    }

    public String getCar_brand2() {
        return car_brand2.get();
    }

    public StringProperty car_brand2Property() {
        return car_brand2;
    }

    public void setCar_brand2(String car_brand2) {
        this.car_brand2.set(car_brand2);
    }

    public String getCar_model2() {
        return car_model2.get();
    }

    public StringProperty car_model2Property() {
        return car_model2;
    }

    public void setCar_model2(String car_model2) {
        this.car_model2.set(car_model2);
    }

    public String getRental_name2() {
        return rental_name2.get();
    }

    public StringProperty rental_name2Property() {
        return rental_name2;
    }

    public void setRental_name2(String rental_name2) {
        this.rental_name2.set(rental_name2);
    }

    public double getCar_price2() {
        return car_price2.get();
    }

    public DoubleProperty car_price2Property() {
        return car_price2;
    }

    public void setCar_price2(double car_price2) {
        this.car_price2.set(car_price2);
    }
}
