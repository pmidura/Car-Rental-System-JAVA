package com.example.car_rental_system.manager_getter_setter;

import javafx.beans.property.*;

public class manager_workers_getter_setter {
    private final StringProperty mimie, mnazwisko, mpesel, mlogin, mhaslo, mtelefon;

    public manager_workers_getter_setter(String mimie, String mnazwisko, String mpesel,String mlogin, String mhaslo, String mtelefon) {
        this.mimie = new SimpleStringProperty();
        this.mnazwisko = new SimpleStringProperty();
        this.mpesel = new SimpleStringProperty();
        this.mlogin = new SimpleStringProperty();
        this.mhaslo = new SimpleStringProperty();
        this.mtelefon = new SimpleStringProperty();

        setMimie(mimie);
        setMnazwisko(mnazwisko);
        setMpesel(mpesel);
        setMlogin(mlogin);
        setMhaslo(mhaslo);
        setMtelefon(mtelefon);
    }
    public String getMimie() {
        return mimie.get();
    }

    public StringProperty mimieProperty() {
        return mimie;
    }

    public void setMimie(String mimie) {this.mimie.set(mimie);

    }public String getMnazwisko() {
        return mnazwisko.get();
    }

    public StringProperty mnazwiskoProperty() {
        return mnazwisko;
    }

    public void setMnazwisko(String mnazwisko) {this.mnazwisko.set(mnazwisko);

    }public String getMpesel() {
        return mpesel.get();
    }

    public StringProperty mpeselProperty() {
        return mpesel;
    }

    public void setMpesel(String mpesel) {this.mpesel.set(mpesel);}

    public String getMlogin() {return mlogin.get();}

    public StringProperty mloginProperty() {
        return mlogin;
    }

    public void setMlogin(String mlogin) {
        this.mlogin.set(mlogin);
    }

    public String getMhaslo() {return mhaslo.get();}

    public StringProperty mhasloProperty() {
        return mhaslo;
    }

    public void setMhaslo(String mhaslo) {
        this.mhaslo.set(mhaslo);
    }

    public String getMtelefon() {return mtelefon.get();}

    public StringProperty mtelefonProperty() {
        return mtelefon;
    }

    public void setMtelefon(String mtelefon) {
        this.mtelefon.set(mtelefon);
    }
}

