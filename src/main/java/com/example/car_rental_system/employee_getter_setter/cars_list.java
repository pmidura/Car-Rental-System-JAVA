package com.example.car_rental_system.employee_getter_setter;


import javafx.beans.property.*;

public class cars_list {
    private final StringProperty car_registration, car_brand, car_model, rental_name;
    private final IntegerProperty car_production;
    private final DoubleProperty car_price;
    private final BooleanProperty availability;

    public cars_list(String car_registration, String car_brand, String car_model, String rental_name, int car_production, double car_price, boolean availability) {
        this.car_registration = new SimpleStringProperty();
        this.car_brand = new SimpleStringProperty();
        this.car_model = new SimpleStringProperty();
        this.rental_name = new SimpleStringProperty();
        this.car_production = new SimpleIntegerProperty();
        this.car_price = new SimpleDoubleProperty();
        this.availability = new SimpleBooleanProperty();

        setCar_registration(car_registration);
        setCar_brand(car_brand);
        setCar_model(car_model);
        setRental_name(rental_name);
        setCar_production(car_production);
        setCar_price(car_price);
        setAvailability(availability);
    }

    public String getCar_registration() {
        return car_registration.get();
    }

    public StringProperty car_registrationProperty() {
        return car_registration;
    }

    public void setCar_registration(String car_registration) {
        this.car_registration.set(car_registration);
    }

    public String getCar_brand() {
        return car_brand.get();
    }

    public StringProperty car_brandProperty() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand.set(car_brand);
    }

    public String getCar_model() {
        return car_model.get();
    }

    public StringProperty car_modelProperty() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model.set(car_model);
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

    public int getCar_production() {
        return car_production.get();
    }

    public IntegerProperty car_productionProperty() {
        return car_production;
    }

    public void setCar_production(int car_production) {
        this.car_production.set(car_production);
    }

    public double getCar_price() {
        return car_price.get();
    }

    public DoubleProperty car_priceProperty() {
        return car_price;
    }

    public void setCar_price(double car_price) {
        this.car_price.set(car_price);
    }

    public boolean getAvailability() {
        return availability.get();
    }

    public BooleanProperty availabilityProperty() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability.set(availability);
    }
}
