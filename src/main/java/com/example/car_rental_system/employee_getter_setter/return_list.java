package com.example.car_rental_system.employee_getter_setter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class return_list {
    private final StringProperty client_name, client_surname, client_pesel, client_phone;
    private final IntegerProperty loan_id, period;

    public String getClient_name() {
        return client_name.get();
    }

    public StringProperty client_nameProperty() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name.set(client_name);
    }

    public String getClient_surname() {
        return client_surname.get();
    }

    public StringProperty client_surnameProperty() {
        return client_surname;
    }

    public void setClient_surname(String client_surname) {
        this.client_surname.set(client_surname);
    }

    public String getClient_pesel() {
        return client_pesel.get();
    }

    public StringProperty client_peselProperty() {
        return client_pesel;
    }

    public void setClient_pesel(String client_pesel) {
        this.client_pesel.set(client_pesel);
    }

    public String getClient_phone() {
        return client_phone.get();
    }

    public StringProperty client_phoneProperty() {
        return client_phone;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone.set(client_phone);
    }

    public int getLoan_id() {
        return loan_id.get();
    }

    public IntegerProperty loan_idProperty() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id.set(loan_id);
    }

    public int getPeriod() {
        return period.get();
    }

    public IntegerProperty periodProperty() {
        return period;
    }

    public void setPeriod(int period) {
        this.period.set(period);
    }

    public return_list(IntegerProperty loan_id, StringProperty client_name, StringProperty client_surname, StringProperty client_pesel, StringProperty client_phone, IntegerProperty period) {
        this.client_name = client_name;
        this.client_surname = client_surname;
        this.client_pesel = client_pesel;
        this.client_phone = client_phone;
        this.loan_id = loan_id;
        this.period = period;
    }

}
