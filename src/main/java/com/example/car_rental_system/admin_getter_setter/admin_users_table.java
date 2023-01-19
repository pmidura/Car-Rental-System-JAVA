package com.example.car_rental_system.admin_getter_setter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Users table in Admin Panel
 *
 * @author Patryk Midura
 */
public class admin_users_table {

    private StringProperty pesel;
    private StringProperty imie;
    private StringProperty nazwisko;
    private StringProperty login;
    private StringProperty haslo;
    private StringProperty stanowisko;
    private StringProperty nrTelefonu;

    /**
     * Constructor for users table in Admin Panel
     *
     * @param pesel User PESEL number
     * @param imie User name
     * @param nazwisko User surname
     * @param login User login
     * @param haslo User password
     * @param stanowisko User role
     * @param nrTelefonu User phone number
     */
    public admin_users_table(String pesel, String imie, String nazwisko, String login, String haslo, String stanowisko,
        String nrTelefonu) {
            this.pesel = new SimpleStringProperty(pesel);
            this.imie = new SimpleStringProperty(imie);
            this.nazwisko = new SimpleStringProperty(nazwisko);
            this.login = new SimpleStringProperty(login);
            this.haslo = new SimpleStringProperty(haslo);
            this.stanowisko = new SimpleStringProperty(stanowisko);
            this.nrTelefonu = new SimpleStringProperty(nrTelefonu);
    }

    /**
     * This method is for getting user PESEL number
     *
     * @return PESEL number
     */
    public String getPesel() {
        return pesel.get();
    }

    /**
     * This method is for PESEL number property
     *
     * @return PESEL number
     */
    public StringProperty peselProperty() {
        return pesel;
    }

    /**
     * This method is for setting user PESEL number
     *
     * @param pesel User PESEL
     */
    public void setPesel(String pesel) {
        this.pesel.set(pesel);
    }

    /**
     * This method is for getting user Name
     *
     * @return User Name
     */
    public String getImie() {
        return imie.get();
    }

    /**
     * This method is for user Name property
     *
     * @return User Name
     */
    public StringProperty imieProperty() {
        return imie;
    }

    /**
     * This method is for setting user Name
     *
     * @param imie User Name
     */
    public void setImie(String imie) {
        this.imie.set(imie);
    }

    /**
     * This method is for getting user Surname
     *
     * @return User Surname
     */
    public String getNazwisko() {
        return nazwisko.get();
    }

    /**
     * This method is for user Surname property
     *
     * @return User Surname
     */
    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }

    /**
     * This method is for setting user Surname
     *
     * @param nazwisko User Surname
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    /**
     * This method is for getting user Login
     *
     * @return User Login
     */
    public String getLogin() {
        return login.get();
    }

    /**
     * This method is for user Login property
     *
     * @return User Login
     */
    public StringProperty loginProperty() {
        return login;
    }

    /**
     * This method is for setting user Login
     *
     * @param login User Login
     */
    public void setLogin(String login) {
        this.login.set(login);
    }

    /**
     * This method is for getting user Password
     *
     * @return User Password
     */
    public String getHaslo() {
        return haslo.get();
    }

    /**
     * This method is for user Password property
     *
     * @return User Password
     */
    public StringProperty hasloProperty() {
        return haslo;
    }

    /**
     * This method is for setting user Password
     *
     * @param haslo User Password
     */
    public void setHaslo(String haslo) {
        this.haslo.set(haslo);
    }

    /**
     * This method is for getting user Role
     *
     * @return User Role
     */
    public String getStanowisko() {
        return stanowisko.get();
    }

    /**
     * This method is for user Role property
     *
     * @return User Role
     */
    public StringProperty stanowiskoProperty() {
        return stanowisko;
    }

    /**
     * This method is for setting user Role
     *
     * @param stanowisko User Role
     */
    public void setStanowisko(String stanowisko) {
        this.stanowisko.set(stanowisko);
    }

    /**
     * This method is for getting user Phone number
     *
     * @return User Phone
     */
    public String getNrTelefonu() {
        return nrTelefonu.get();
    }

    /**
     * This method is for user Phone number property
     *
     * @return User Phone
     */
    public StringProperty nrTelefonuProperty() {
        return nrTelefonu;
    }

    /**
     * This method is for setting user Phone number
     *
     * @param nrTelefonu User Phone
     */
    public void setNrTelefonu(String nrTelefonu) {
        this.nrTelefonu.set(nrTelefonu);
    }
}
