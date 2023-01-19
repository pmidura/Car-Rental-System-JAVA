package com.example.car_rental_system.manager_getter_setter;

import javafx.beans.property.*;

public class manager_cars_getter_setter {
    private final StringProperty mrejestracja, mmarka, mmodel, mwypozyczalnia;
    private final IntegerProperty mrok;
    private final DoubleProperty mcena;
    private final BooleanProperty mwypozyczony;

    public manager_cars_getter_setter(String mrejestracja, String mmarka, String mmodel, Integer mrok, Double mcena, Boolean mwypozyczony,
                                      String mwypozyczalnia) {
        this.mrejestracja = new SimpleStringProperty();
        this.mmarka = new SimpleStringProperty();
        this.mmodel = new SimpleStringProperty();
        this.mrok = new SimpleIntegerProperty();
        this.mcena = new SimpleDoubleProperty();
        this.mwypozyczony = new SimpleBooleanProperty();
        this.mwypozyczalnia = new SimpleStringProperty();

        setMrejestracja(mrejestracja);
        setMmarka(mmarka);
        setMmodel(mmodel);
        setMrok(mrok);
        setMcena(mcena);
        setMwypozyczony(mwypozyczony);
        setMwypozyczalnia(mwypozyczalnia);
    }
    public String getMrejestracja() {
        return mrejestracja.get();
    }

    public StringProperty mrejestracjaProperty() {
        return mrejestracja;
    }

    public void setMrejestracja(String mrejestracja) {
        this.mrejestracja.set(mrejestracja);
    }

    public String getMmarka() {
        return mmarka.get();
    }

    public StringProperty mmarkaProperty() {
        return mmarka;
    }

    public void setMmarka(String mmarka) {
        this.mmarka.set(mmarka);
    }

    public String getMmodel() {
        return mmodel.get();
    }

    public StringProperty mmodelProperty() {
        return mmodel;
    }

    public void setMmodel(String mmodel) {
        this.mmodel.set(mmodel);
    }

    public int getMrok() {return mrok.get();}

    public IntegerProperty mrokProperty() {
        return mrok;
    }

    public void setMrok(int mrok) {
        this.mrok.set(mrok);
    }

    public double getMcena() {
        return mcena.get();
    }

    public DoubleProperty mcenaProperty() {
        return mcena;
    }

    public void setMcena(double mcena) {
        this.mcena.set(mcena);
    }

    public boolean getMwypozyczony() {return mwypozyczony.get();}

    public BooleanProperty mwypozyczonyProperty() {
        return mwypozyczony;
    }

    public void setMwypozyczony(boolean mwypozyczony) {
        this.mwypozyczony.set(mwypozyczony);
    }

    public String getMwypozyczalnia() {
        return mwypozyczalnia.get();
    }

    public StringProperty mwypozyczalniaProperty() {
        return mwypozyczalnia;
    }

    public void setMwypozyczalnia(String mwypozyczalnia) {
        this.mwypozyczalnia.set(mwypozyczalnia);
    }
}



