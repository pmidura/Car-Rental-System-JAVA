package com.example.car_rental_system.admin_getter_setter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class rental_getter_setter {
    private final StringProperty rental_name, rental_city, rental_street, user_id;
    private final IntegerProperty rental_id, rental_workers;

    public rental_getter_setter(Integer rental_id, String rental_name, String rental_city, String rental_street, Integer rental_workers, String user_id){
        this.rental_id = new SimpleIntegerProperty();
        this.rental_name = new SimpleStringProperty();
        this.rental_city = new SimpleStringProperty();
        this.rental_street = new SimpleStringProperty();
        this.user_id = new SimpleStringProperty();
        this.rental_workers = new SimpleIntegerProperty();

        setRental_id(rental_id);
        setRental_name(rental_name);
        setRental_city(rental_city);
        setRental_street(rental_street);
        setRental_workers(rental_workers);
        setUser_id(user_id);

    }

    public String getRental_name() {
        return rental_name.get();
    }

    public StringProperty rental_nameProperty() {
        return rental_name;
    }

    public void setRental_name(String rental_name) {
        this.rental_name.set(rental_name);
    }

    public String getRental_city() {
        return rental_city.get();
    }

    public StringProperty rental_cityProperty() {
        return rental_city;
    }

    public void setRental_city(String rental_city) {
        this.rental_city.set(rental_city);
    }

    public String getRental_street() {
        return rental_street.get();
    }

    public StringProperty rental_streetProperty() {
        return rental_street;
    }

    public void setRental_street(String rental_street) {
        this.rental_street.set(rental_street);
    }

    public int getRental_id() {
        return rental_id.get();
    }

    public IntegerProperty rental_idProperty() {
        return rental_id;
    }

    public void setRental_id(int rental_id) {
        this.rental_id.set(rental_id);
    }

    public String getUser_id() {
        return user_id.get();
    }

    public StringProperty user_idProperty() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id.set(user_id);
    }

    public int getRental_workers() {
        return rental_workers.get();
    }

    public IntegerProperty rental_workersProperty() {
        return rental_workers;
    }

    public void setRental_workers(int rental_workers) {
        this.rental_workers.set(rental_workers);
    }
}
