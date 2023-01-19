package com.example.car_rental_system;

import com.example.car_rental_system.DBconnect.DBconnect;
import com.example.car_rental_system.Validators.*;
import com.example.car_rental_system.admin_getter_setter.admin_users_table;
import com.example.car_rental_system.manager_getter_setter.manager_cars_getter_setter;
import com.example.car_rental_system.manager_getter_setter.manager_workers_getter_setter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is for Manager Controller
 *
 * @author Bartosz Gołąb
 * @author Patryk Midura
 */

public class ManagerController {

    @FXML
    private AnchorPane managerpanel;


    @FXML
    ObservableList<String> rolesitemslist = FXCollections.observableArrayList();
    @FXML
    ObservableList<String> rolesitemslist2 = FXCollections.observableArrayList();
    @FXML
    ObservableList<String> rolesitemslist3 = FXCollections.observableArrayList();

    @FXML
    private TextField m_pesel;
    @FXML
    private TextField m_imie;
    @FXML
    private TextField m_nazwisko;
    @FXML
    private TextField m_login;
    @FXML
    private PasswordField m_haslo;
    @FXML
    private TextField m_telefon;

    @FXML
    private TextField m_marka;
    @FXML
    private TextField m_model;
    @FXML
    private TextField m_rok;
    @FXML
    private TextField m_rejestracja;
    @FXML
    private TextField m_cena;



    @FXML
    ObservableList<manager_workers_getter_setter> obslist1 = FXCollections.observableArrayList();
    @FXML
    private TableView<manager_workers_getter_setter> m_tabpracownicy;
    @FXML
    private TableColumn<manager_workers_getter_setter, String> m_colpesel;
    @FXML
    private TableColumn<manager_workers_getter_setter, String> m_colimie;
    @FXML
    private TableColumn<manager_workers_getter_setter, String> m_colnazwisko;
    @FXML
    private TableColumn<manager_workers_getter_setter, String> m_collogin;
    @FXML
    private TableColumn<manager_workers_getter_setter, String> m_colhaslo;
    @FXML
    private TableColumn<manager_workers_getter_setter, String> m_coltelefon;

    @FXML
    ObservableList<manager_cars_getter_setter> obslist2 = FXCollections.observableArrayList();
    @FXML
    private TableView<manager_cars_getter_setter> m_tabsamochody;
    @FXML
    private TableColumn<manager_cars_getter_setter, String> m_colrejestracja;
    @FXML
    private TableColumn<manager_cars_getter_setter, String> m_colmarka;
    @FXML
    private TableColumn<manager_cars_getter_setter, String> m_colmodel;
    @FXML
    private TableColumn<manager_cars_getter_setter, Integer> m_colrok;
    @FXML
    private TableColumn<manager_cars_getter_setter, Double> m_colcena;
    @FXML
    private TableColumn<manager_cars_getter_setter, Boolean> m_coldostepny;
    @FXML
    private TableColumn<manager_cars_getter_setter, String> m_colwypozyczalnia;

    @FXML
    private Button wylog1;
    @FXML
    private Button wylog2;
    @FXML
    private Button btnAddWorkers;
    @FXML
    private Button btnDeleteWorkers;
    @FXML
    private Button btnEditWorkers;
    @FXML
    private Button btnAddCars;
    @FXML
    private Button btnDeleteCars;
    @FXML
    private Button editCarbtn;
    @FXML
    private Button searchbtn1;
    @FXML
    private Button searchCarsbtn;
    @FXML
    private Button odswiez1;
    @FXML
    private Button odswiez2;

    @FXML
    private Button modify_btn;
    @FXML
    private Button return_btn;

    @FXML
    private Button modify_btn2;
    @FXML
    private Button return_btn2;

    String tempUsername;

    /**
     *  * This method is used to initialize the appropriate startup items
     * @param uName
     * @throws SQLException
     */
    @FXML
    public void initialize(String uName) throws SQLException {
        tempUsername = uName;

        hideTextFieldsOnStart();
        hideTextFieldsCars();
        /* Modify Button actions */
        modify_btn.setOnAction(event -> {
            if (modify_btn.getText().equals("Dodaj pracownika")) {
                mAddWorkers();
            }
            else if (modify_btn.getText().equals("Usun pracownika")) {
                mDeleteWorkers();
            }
            else if (modify_btn.getText().equals("Edytuj pracownika")) {
                mEditWorkers();
            }
            else if (modify_btn.getText().equals("Wyszukaj po peselu")) {
                mSearchWorkers();
            }
        });

        modify_btn2.setOnAction(event -> {
            if (modify_btn2.getText().equals("Dodaj samochod")) {
                mAddCars();
            }
            else if (modify_btn2.getText().equals("Usun samochod")) {
                mDeleteCars();
            }
            else if (modify_btn2.getText().equals("Edytuj samochod")) {
                mEditCars();
            }
            else if (modify_btn2.getText().equals("Wyszukaj po rejestracji")) {
                mSearchCars();
            }
        });

        /* Return Button actions */
        return_btn.setOnAction(event -> {
            modify_btn.setText("");
            hideTextFieldsOnStart();
            showFourStartButtons();
            m_tabpracownicy.getSelectionModel().clearSelection();
            m_imie.clear();
            m_nazwisko.clear();
            m_pesel.clear();
            m_login.clear();
            m_haslo.clear();
            m_telefon.clear();
        });

        return_btn2.setOnAction(event -> {
            modify_btn2.setText("");
            hideTextFieldsCars();
            showFourCarsButtons();
            m_tabsamochody.getSelectionModel().clearSelection();
            m_marka.clear();
            m_model.clear();
            m_rok.clear();
            m_rejestracja.clear();
            m_cena.clear();
        });

        showUsers();
        showCars();
    }

    /**
     * This method is for hiding TextFields, ComboBox and 2 buttons
     *
     * @author Patryk Midura
     */
    @FXML
    private void hideTextFieldsOnStart() {
        m_imie.setVisible(false);
        m_nazwisko.setVisible(false);
        m_pesel.setVisible(false);
        m_login.setVisible(false);
        m_haslo.setVisible(false);
        m_telefon.setVisible(false);

        modify_btn.setVisible(false);
        return_btn.setVisible(false);
    }

    /**
     * This method is for showing 4 buttons (Add, Delete, Edit, Search user)
     *
     * @author Patryk Midura
     */
    @FXML
    private void showFourStartButtons() {
        btnAddWorkers.setVisible(true);
        btnDeleteWorkers.setVisible(true);
        btnEditWorkers.setVisible(true);
        searchbtn1.setVisible(true);
    }

    /**
     * This method is used to show relevant items when adding a user
     *
     * @author Patryk Midura
     */
    @FXML
    private void addWorkerBtn() {
        m_tabpracownicy.getSelectionModel().clearSelection();

        m_imie.setVisible(true);
        m_imie.setEditable(true);
        m_nazwisko.setVisible(true);
        m_nazwisko.setEditable(true);
        m_pesel.setVisible(true);
        m_pesel.setEditable(true);
        m_login.setVisible(true);
        m_login.setEditable(true);
        m_haslo.setVisible(true);
        m_haslo.setEditable(true);
        m_telefon.setVisible(true);
        m_telefon.setEditable(true);

        modify_btn.setVisible(true);
        modify_btn.setText("Dodaj pracownika");
        btnAddWorkers.setVisible(false);
        btnDeleteWorkers.setVisible(false);
        btnEditWorkers.setVisible(false);
        searchbtn1.setVisible(false);
        return_btn.setVisible(true);
        return_btn.setText("Powrót");
    }

    /**
     * This method is used to show relevant items when edit a user
     *
     * @author Patryk Midura
     */

    @FXML
    private void editWorkerBtn() {
        m_tabpracownicy.getSelectionModel().clearSelection();

        m_imie.setVisible(true);
        m_imie.setEditable(false);
        m_nazwisko.setVisible(true);
        m_nazwisko.setEditable(true);
        m_pesel.setVisible(true);
        m_pesel.setEditable(true);
        m_login.setVisible(true);
        m_login.setEditable(false);
        m_haslo.setVisible(true);
        m_haslo.setEditable(false);
        m_telefon.setVisible(true);
        m_telefon.setEditable(false);

        modify_btn.setVisible(true);
        modify_btn.setText("Edytuj pracownika");
        btnAddWorkers.setVisible(false);
        btnDeleteWorkers.setVisible(false);
        btnEditWorkers.setVisible(false);
        searchbtn1.setVisible(false);
        return_btn.setVisible(true);
        return_btn.setText("Powrót");

        m_tabpracownicy.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_btn.getText().equals("Edytuj pracownika")) {
                m_pesel.setText(newVal.getMpesel());
                m_imie.setText(newVal.getMimie());
                m_nazwisko.setText(newVal.getMnazwisko());
                m_login.setText(newVal.getMlogin());
                m_haslo.setText(newVal.getMhaslo());
                m_telefon.setText(newVal.getMtelefon());
            }
        });
    }

    /**
     * This method is used to show relevant items when deleting a user
     *
     * @author Patryk Midura
     */
    @FXML
    private void delWorkerBtn() {
        m_tabpracownicy.getSelectionModel().clearSelection();

        m_imie.setVisible(true);
        m_imie.setEditable(true);
        modify_btn.setVisible(true);
        modify_btn.setText("Usun pracownika");
        btnAddWorkers.setVisible(false);
        btnDeleteWorkers.setVisible(false);
        btnEditWorkers.setVisible(false);
        searchbtn1.setVisible(false);
        return_btn.setVisible(true);
        return_btn.setText("Powrót");

        m_tabpracownicy.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_btn.getText().equals("Usun pracownika")) {
                m_imie.setText(newVal.getMimie());
            }
        });
    }

    /**
     * This method is used to show relevant items when searching a user
     *
     * @author Patryk Midura
     */
    @FXML
    private void searchWorkerBtn() {
        m_tabpracownicy.getSelectionModel().clearSelection();

        m_imie.setVisible(true);
        m_imie.setEditable(true);
        modify_btn.setVisible(true);
        modify_btn.setText("Wyszukaj po peselu");
        btnAddWorkers.setVisible(false);
        btnDeleteWorkers.setVisible(false);
        btnEditWorkers.setVisible(false);
        searchbtn1.setVisible(false);
        return_btn.setVisible(true);
        return_btn.setText("Powrót");

        m_tabpracownicy.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_btn.getText().equals("Wyszukaj po peselu")) {
                m_imie.setText(newVal.getMimie());
            }
        });
    }

    /**
     * This method is for hide Textfields
     *
     * @author Patryk Midura
     */
    @FXML
    private void hideTextFieldsCars() {
        m_marka.setVisible(false);
        m_model.setVisible(false);
        m_rok.setVisible(false);
        m_rejestracja.setVisible(false);
        m_cena.setVisible(false);

        modify_btn2.setVisible(false);
        return_btn2.setVisible(false);
    }

    /**
     * This method is for showing 4 buttons (Add, Delete, Edit, Search user)
     *
     * @author Patryk Midura
     */
    @FXML
    private void showFourCarsButtons() {
        btnAddCars.setVisible(true);
        btnDeleteCars.setVisible(true);
        editCarbtn.setVisible(true);
        searchCarsbtn.setVisible(true);
    }

    /**
     * This method is used to show relevant items when adding a cars
     *
     * @author Patryk Midura
     */
    @FXML
    private void addCarBtn(){
        m_tabsamochody.getSelectionModel().clearSelection();

        m_marka.setVisible(true);
        m_marka.setEditable(true);
        m_model.setVisible(true);
        m_model.setEditable(true);
        m_rok.setVisible(true);
        m_rok.setEditable(true);
        m_rejestracja.setVisible(true);
        m_rejestracja.setEditable(true);
        m_cena.setVisible(true);
        m_cena.setEditable(true);

        modify_btn2.setVisible(true);
        modify_btn2.setText("Dodaj samochod");
        btnAddCars.setVisible(false);
        btnDeleteCars.setVisible(false);
        editCarbtn.setVisible(false);
        searchCarsbtn.setVisible(false);
        return_btn2.setVisible(true);
        return_btn2.setText("Powrót");
    }

    /**
     * This method is used to show relevant items when edit a cars
     *
     * @author Patryk Midura
     */
    @FXML
    private void editCarBtn() {
        m_tabsamochody.getSelectionModel().clearSelection();

        m_marka.setVisible(true);
        m_marka.setEditable(true);
        m_model.setVisible(true);
        m_model.setEditable(true);
        m_rok.setVisible(true);
        m_rok.setEditable(true);
        m_rejestracja.setVisible(true);
        m_rejestracja.setEditable(false);
        m_cena.setVisible(true);
        m_cena.setEditable(true);

        modify_btn2.setVisible(true);
        modify_btn2.setText("Edytuj samochod");
        btnAddCars.setVisible(false);
        btnDeleteCars.setVisible(false);
        editCarbtn.setVisible(false);
        searchCarsbtn.setVisible(false);
        return_btn2.setVisible(true);
        return_btn2.setText("Powrót");

        m_tabsamochody.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_btn2.getText().equals("Edytuj samochod")) {
                m_rejestracja.setText(newVal.getMrejestracja());
                m_marka.setText(newVal.getMmarka());
                m_model.setText(newVal.getMmodel());
                m_rok.setText(String.valueOf(newVal.getMrok()));
                m_cena.setText(String.valueOf(newVal.getMcena()));
            }
        });
    }

    /**
     * This method is used to show relevant items when delete a cars
     *
     * @author Patryk Midura
     */
    @FXML
    private void delCarBtn() {
        m_tabsamochody.getSelectionModel().clearSelection();

        m_rejestracja.setVisible(true);
        m_rejestracja.setEditable(true);
        modify_btn2.setVisible(true);
        modify_btn2.setText("Usun samochod");
        btnAddCars.setVisible(false);
        btnDeleteCars.setVisible(false);
        editCarbtn.setVisible(false);
        searchCarsbtn.setVisible(false);
        return_btn2.setVisible(true);
        return_btn2.setText("Powrót");

        m_tabsamochody.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_btn2.getText().equals("Usun samochod")) {
                m_rejestracja.setText(newVal.getMrejestracja());
            }
        });
    }

    /**
     * This method is used to show relevant items when searching a cars
     *
     * @author Patryk Midura
     */
    @FXML
    private void searchCarBtn() {
        m_tabsamochody.getSelectionModel().clearSelection();

        m_rejestracja.setVisible(true);
        m_rejestracja.setEditable(true);
        modify_btn2.setVisible(true);
        modify_btn2.setText("Wyszukaj po rejestracji");
        btnAddCars.setVisible(false);
        btnDeleteCars.setVisible(false);
        editCarbtn.setVisible(false);
        searchCarsbtn.setVisible(false);
        return_btn2.setVisible(true);
        return_btn2.setText("Powrót");

        m_tabsamochody.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_btn2.getText().equals("Wyszukaj po rejestracji")) {
                m_rejestracja.setText(newVal.getMrejestracja());
            }
        });
    }

    /**
     * This method is used to add a workers to the database
     *
     * @author Bartosz Gołąb
     */
    @FXML
    private void mAddWorkers() {
        String managerpesel = m_imie.getText();
        String managerimie = m_nazwisko.getText();
        String managernazwisko = m_pesel.getText();
        String managerlogin = m_login.getText();
        String managerhaslo = m_haslo.getText();
        String managertelefon = m_telefon.getText();


        if (managerpesel.isEmpty()) {
            Alert null_pesel = new Alert(Alert.AlertType.ERROR);
            null_pesel.setContentText("Proszę wprowadzić pesel!");
            null_pesel.show();
        }
        else if (!peselVal.isValid(managerpesel)) {
            Alert not_valid_pesel = new Alert(Alert.AlertType.ERROR);
            not_valid_pesel.setContentText("Nieprawidłowy format nr PESEL!\n"
                    + "Nr PESEL powinien składać się tylko i wyłącznie z 11 cyfr!");
            not_valid_pesel.show();
        }
        else if (managerimie.isEmpty()) {
            Alert null_imie = new Alert(Alert.AlertType.ERROR);
            null_imie.setContentText("Proszę wprowadzić imie!");
            null_imie.show();
        }
        else if (managerimie.length() > 20) {
            Alert outOfLimit_userName = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userName.setContentText("Przekroczono limit 20 znaków w polu Imię!");
            outOfLimit_userName.show();
        }
        else if (managernazwisko.isEmpty()) {
            Alert null_nazwisko = new Alert(Alert.AlertType.ERROR);
            null_nazwisko.setContentText("Proszę wprowadzić nazwisko!");
            null_nazwisko.show();
        }

        else if (managernazwisko.length() > 20) {
            Alert outOfLimit_userSurname = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userSurname.setContentText("Przekroczono limit 20 znaków w polu Nazwisko!");
            outOfLimit_userSurname.show();
        }
        else if (managerlogin.isEmpty()) {
            Alert null_login = new Alert(Alert.AlertType.ERROR);
            null_login.setContentText("Proszę wprowadzić login!");
            null_login.show();
        }

        else if (managerlogin.length() > 30) {
            Alert outOfLimit_userLogin = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userLogin.setContentText("Przekroczono limit 30 znaków w polu Login!");
            outOfLimit_userLogin.show();
        }
        else if (managerhaslo.isEmpty()) {
            Alert null_haslo = new Alert(Alert.AlertType.ERROR);
            null_haslo.setContentText("Proszę wprowadzić haslo!");
            null_haslo.show();
        }
        else if (managertelefon.isEmpty()) {
            Alert null_tel = new Alert(Alert.AlertType.ERROR);
            null_tel.setContentText("Proszę wprowadzić telefon!");
            null_tel.show();
        }

        else if (!phoneNumberVal.isValid(managertelefon)) {
            Alert not_valid_phone = new Alert(Alert.AlertType.ERROR);
            not_valid_phone.setContentText("Nieprawidłowy format nr telefonu!\n"
                    + "Nr telefonu powinien składać się tylko i wyłącznie z 9 cyfr!");
            not_valid_phone.show();
        }
        else {
            try {

                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(managerhaslo.getBytes(), 0, managerhaslo.length());
                String hashPass = new BigInteger(1, md.digest()).toString(16);

                Connection conn = DBconnect.getConnection();



                PreparedStatement getPESEL = conn.prepareStatement("SELECT pesel FROM users WHERE "
                        + "pesel = '"+managerpesel+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetPESEL = getPESEL.executeQuery();


                PreparedStatement getPhone = conn.prepareStatement("SELECT user_phone FROM users WHERE "
                        + "user_phone = '"+managertelefon+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetPhone = getPhone.executeQuery();


                PreparedStatement getLogin = conn.prepareStatement("SELECT login FROM users WHERE "
                        + "login = '"+managerlogin+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetLogin = getLogin.executeQuery();


                if (rsGetPESEL.first()) {
                    Alert pesel_in_DB = new Alert(Alert.AlertType.ERROR);
                    pesel_in_DB.setContentText("Podany PESEL istnieje już w bazie!");
                    pesel_in_DB.show();
                }

                else if (rsGetPhone.first()) {
                    Alert phone_in_DB = new Alert(Alert.AlertType.ERROR);
                    phone_in_DB.setContentText("Podany nr telefonu istnieje już w bazie!");
                    phone_in_DB.show();
                }

                else if (rsGetLogin.first()) {
                    Alert login_in_DB = new Alert(Alert.AlertType.ERROR);
                    login_in_DB.setContentText("Podany login istnieje już w bazie!");
                    login_in_DB.show();
                }
                else {

                    Statement stmt = conn.createStatement();
                    PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                            + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rsGetRentalID = getRentalID.executeQuery();
                    rsGetRentalID.first();
                    Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

                    stmt.executeUpdate("INSERT INTO users (pesel, user_name, user_surname, login, password, user_phone, role_id , rental_id)"
                            + "VALUES ('"+managerpesel+"', '"+managerimie+"', '"+managernazwisko+"', '"+managerlogin+"', '"+hashPass+"', '"+managertelefon+"', '2', '"+rentalID+"')");

                    PreparedStatement countEmp = conn.prepareStatement("SELECT COUNT(u.user_id) AS empCount FROM users u "
                        + "JOIN rentals r ON u.rental_id = r.rental_id WHERE r.rental_id = '"+rentalID+"' "
                            + "AND role_id = 2", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rsCountEmp = countEmp.executeQuery();
                    rsCountEmp.first();
                    int countedEmp = rsCountEmp.getInt("empCount");

                    stmt.executeUpdate("UPDATE rentals SET rental_workers = '"+countedEmp+"' "
                            + "WHERE rental_id = '"+rentalID+"'");
                    Alert success_add = new Alert(Alert.AlertType.CONFIRMATION);
                    success_add.setContentText("Dodano nowego użytkownika!");
                    success_add.show();

                    m_pesel.clear();
                    m_imie.clear();
                    m_nazwisko.clear();
                    m_login.clear();
                    m_haslo.clear();
                    m_telefon.clear();

                    refreshTable();

                    /* Hide TextFields, Buttons after successful add */
                    hideTextFieldsOnStart();

                    /* Show buttons add, edit, delete */
                    showFourStartButtons();
                }
                conn.close();
            }
            catch (SQLException e) {
                Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, e);
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * * This method is used to edit a workers data
     *      *
     *      * @author Bartosz Gołąb
     */
    @FXML
    private void mEditWorkers() {
        String managerpesel = m_imie.getText();
        String managerimie = m_nazwisko.getText();
        String managernazwisko = m_pesel.getText();
        String managerlogin = m_login.getText();
        String managerhaslo = m_haslo.getText();
        String managertelefon = m_telefon.getText();


        if (managerpesel.isEmpty() && managerlogin.isEmpty() && managertelefon.isEmpty()) {
            Alert null_pesel = new Alert(Alert.AlertType.ERROR);
            null_pesel.setContentText("Proszę wybrać użytkownika!");
            null_pesel.show();
        }
        else if (managerimie.isEmpty()) {
            Alert null_imie = new Alert(Alert.AlertType.ERROR);
            null_imie.setContentText("Proszę wprowadzić imie!");
            null_imie.show();
        }
        else if (managerimie.length() > 20) {
            Alert outOfLimit_userName = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userName.setContentText("Przekroczono limit 20 znaków w polu Imię!");
            outOfLimit_userName.show();
        }
        else if (managernazwisko.isEmpty()) {
            Alert null_nazwisko = new Alert(Alert.AlertType.ERROR);
            null_nazwisko.setContentText("Proszę wprowadzić nazwisko!");
            null_nazwisko.show();
        }
        else if (managernazwisko.length() > 20) {
            Alert outOfLimit_userSurname = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userSurname.setContentText("Przekroczono limit 20 znaków w polu Nazwisko!");
            outOfLimit_userSurname.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                        + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRentalID = getRentalID.executeQuery();
                rsGetRentalID.first();
                Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

                Statement stmt1 = conn.createStatement();
                stmt1.executeUpdate("UPDATE users SET user_name = '"+managerimie+"', user_surname = '"+managernazwisko+"' "
                    + "WHERE pesel = '"+managerpesel+"' AND login = '"+managerlogin+"' "
                        + "AND user_phone = '"+managertelefon+"' AND role_id = '2' AND rental_id = '"+rentalID+"'");

                Alert success_edit = new Alert(Alert.AlertType.CONFIRMATION);
                success_edit.setContentText("Zaktualizowano dane użytkownika!");
                success_edit.show();

                m_pesel.clear();
                m_imie.clear();
                m_nazwisko.clear();
                m_login.clear();
                m_haslo.clear();
                m_telefon.clear();

                refreshTable();

                /* Hide TextFields, Buttons after successful edit */
                hideTextFieldsOnStart();

                /* Show buttons add, edit, delete */
                showFourStartButtons();

                conn.close();
            }
            catch (SQLException e) {
                Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * This method is used to showing a information about workers on Tableview
     *
     * @author Bartosz Gołąb
     * @throws SQLException
     */
    @FXML
    private void showUsers() throws SQLException {
        Connection conn = DBconnect.getConnection();

        PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetRentalID = getRentalID.executeQuery();
        rsGetRentalID.first();
        Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

        ResultSet rsusers = conn.createStatement().executeQuery("SELECT u.pesel, u.user_name, u.user_surname, u.login, "
                + "u.password, r.role, u.user_phone FROM users u, roles r WHERE u.role_id = r.role_id "
                + "AND u.rental_id ='"+rentalID+"' AND r.role = 'employee'");

        while (rsusers.next()) {
            obslist1.add(new manager_workers_getter_setter(rsusers.getString("pesel"), rsusers.getString("user_name"),
                    rsusers.getString("user_surname"), rsusers.getString("login"),
                    rsusers.getString("password"), rsusers.getString("user_phone")));
        }
        m_colpesel.setCellValueFactory(managerpesel -> managerpesel.getValue().mpeselProperty());
        m_colimie.setCellValueFactory(managerimie -> managerimie.getValue().mimieProperty());
        m_colnazwisko.setCellValueFactory(managernazwisko -> managernazwisko.getValue().mnazwiskoProperty());
        m_collogin.setCellValueFactory(managerlogin -> managerlogin.getValue().mloginProperty());
        m_colhaslo.setCellValueFactory(managerhaslo -> managerhaslo.getValue().mhasloProperty());
        m_coltelefon.setCellValueFactory(managertelefon -> managertelefon.getValue().mtelefonProperty());

        m_tabpracownicy.setItems(obslist1);
    }

    /**
     * This method is used to search a workers in database
     *
     * @author Bartosz Gołąb
     */
    @FXML
    private void mSearchWorkers() {
        String managerpesel = m_imie.getText();
        if (managerpesel.isEmpty()) {
            Alert null_pesel = new Alert(Alert.AlertType.ERROR);
            null_pesel.setContentText("Proszę wprowadzić pesel!");
            null_pesel.show();
        } else if (!peselVal.isValid(managerpesel)) {
            Alert not_valid_pesel = new Alert(Alert.AlertType.ERROR);
            not_valid_pesel.setContentText("Nieprawidłowy format nr PESEL!\n"
                    + "Nr PESEL powinien składać się tylko i wyłącznie z 11 cyfr!");
            not_valid_pesel.show();
        } else {
            try {
                obslist1.clear();
                Connection conn = DBconnect.getConnection();

                PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                        + "login = '" + tempUsername + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRentalID = getRentalID.executeQuery();
                rsGetRentalID.first();
                Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

                ResultSet rs_users = conn.createStatement().executeQuery("SELECT u.pesel, u.user_name, u.user_surname, u.login, "
                        + "u.password, r.role, u.user_phone FROM users u, roles r WHERE u.pesel = '" + managerpesel + "' AND u.role_id = r.role_id "
                        + "AND u.rental_id ='" + rentalID + "' AND r.role = 'employee'");

                while (rs_users.next()) {
                    obslist1.add(new manager_workers_getter_setter(rs_users.getString("pesel"), rs_users.getString("user_name"),
                            rs_users.getString("user_surname"), rs_users.getString("login"),
                            rs_users.getString("password"), rs_users.getString("user_phone")));
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is used to search a car in database
     *
     * @author Bartosz Gołąb
     */

    @FXML
    private void mSearchCars() {
        String managerrejestracja = m_rejestracja.getText();

        if (managerrejestracja.isEmpty()) {
            Alert emp_rejestracja = new Alert(Alert.AlertType.ERROR);
            emp_rejestracja.setContentText("Proszę wprowadzić nr. rejestracyjny!");
            emp_rejestracja.show();
        } else if (managerrejestracja.length() > 8) {
            Alert not_valid_rejestracja = new Alert(Alert.AlertType.ERROR);
            not_valid_rejestracja.setContentText("Nieprawidłowy format nr. rejestracyjnego!\n"
                    + "Nr rejestracyjny powinien składać się tylko i wyłącznie z 8 lub mniej znaków!");
            not_valid_rejestracja.show();
        } else if (managerrejestracja.length() < 7) {
        Alert not_valid_rejestracja = new Alert(Alert.AlertType.ERROR);
        not_valid_rejestracja.setContentText("Nieprawidłowy format nr. rejestracyjnego!\n"
                + "Nr rejestracyjny powinien składać się tylko i wyłącznie z 8 lub mniej znaków!");
        not_valid_rejestracja.show();
    }
        else {
            try {
                obslist2.clear();
                Connection conn = DBconnect.getConnection();

                ResultSet rs_cars = conn.createStatement().executeQuery("SELECT c.car_registration, c.car_brand, c.car_model, c.car_production, "
                        + "c.car_price, c.availability, r.rental_name FROM cars c, rentals r  WHERE c.car_registration = '" + managerrejestracja + "' AND r.rental_id = c.rental_id ");

                while (rs_cars.next()) {
                    obslist2.add(new manager_cars_getter_setter(rs_cars.getString("car_registration"), rs_cars.getString("car_brand"),
                            rs_cars.getString("car_model"), rs_cars.getInt("car_production"),
                            rs_cars.getDouble("car_price"), rs_cars.getBoolean("availability"),
                            rs_cars.getString("rental_name")));
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is used to add a cars to the database
     *
     * @author Bartosz Gołąb
     */
    @FXML
    private void mAddCars() {
        String managerrejestracja = m_rejestracja.getText();
        String managermarka = m_marka.getText();
        String managermodel = m_model.getText();
        String managerrok = m_rok.getText();
        String managercena = m_cena.getText();
        managercena.replace(",",".");
        if (managerrejestracja.isEmpty()) {
            Alert null_rejestracja = new Alert(Alert.AlertType.ERROR);
            null_rejestracja.setContentText("Proszę wprowadzić rejestracje!");
            null_rejestracja.show();
        }
        else if (managerrejestracja.length() > 8) {
            Alert not_valid_rejestracja = new Alert(Alert.AlertType.ERROR);
            not_valid_rejestracja.setContentText("Nieprawidłowy format nr rejestracyjnego!\n"
                    + "Nr Rejestracynjy powinien składać się z 8 lub 7 znaków!");
            not_valid_rejestracja.show();
        }
        else if (managerrejestracja.length() < 7) {
            Alert not_valid_rejestracja = new Alert(Alert.AlertType.ERROR);
            not_valid_rejestracja.setContentText("Nieprawidłowy format nr rejestracyjnego!\n"
                    + "Nr Rejestracynjy powinien składać się z 8 lub 7 znaków!");
            not_valid_rejestracja.show();
        }
        else if (managermarka.isEmpty()) {
            Alert null_marka = new Alert(Alert.AlertType.ERROR);
            null_marka.setContentText("Proszę wprowadzić marke samochodu!");
            null_marka.show();
        }
        else if (managermarka.length() > 30) {
            Alert outOfLimit_userName = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userName.setContentText("Przekroczono limit 30 znaków w polu Marka!");
            outOfLimit_userName.show();
        }
        else if (managermodel.isEmpty()) {
            Alert null_model = new Alert(Alert.AlertType.ERROR);
            null_model.setContentText("Proszę wprowadzić model samochodu!");
            null_model.show();
        }
        else if (managermodel.length() > 30) {
            Alert outOfLimit_model = new Alert(Alert.AlertType.ERROR);
            outOfLimit_model.setContentText("Przekroczono limit 30 znaków w polu Model!");
            outOfLimit_model.show();
        }
        else if (managerrok.isEmpty()) {
            Alert null_rok = new Alert(Alert.AlertType.ERROR);
            null_rok.setContentText("Proszę wprowadzić rok!");
            null_rok.show();
        }
        else if (!yearVal.isValid(managerrok)) {
            Alert not_valid_year = new Alert(Alert.AlertType.ERROR);
            not_valid_year.setContentText("Nieprawidłowy format roku!\n"
                    + "Rok powinien składać się z 4 cyfr!");
            not_valid_year.show();
        }
        else if (!priceVal.isValid(managercena.replace(",","."))) {
            Alert not_valid_year = new Alert(Alert.AlertType.ERROR);
            not_valid_year.setContentText("Nieprawidłowy format ceny!\n"
                    + "Cena ma się składać wyłącznie z cyfr!");
            not_valid_year.show();
        }
        else if (managercena.isEmpty()) {
            Alert null_cena = new Alert(Alert.AlertType.ERROR);
            null_cena.setContentText("Proszę wprowadzić cene za dzień!");
            null_cena.show();
        }

        else {
            try {
                Connection conn = DBconnect.getConnection();

                PreparedStatement getMrejestracja = conn.prepareStatement("SELECT car_registration  FROM cars WHERE "
                        + "car_registration  = '"+managerrejestracja+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetMrejestracja = getMrejestracja.executeQuery();

                if (rsGetMrejestracja.first()) {
                    Alert rejestracja_in_DB = new Alert(Alert.AlertType.ERROR);
                    rejestracja_in_DB.setContentText("Podana rejestracja istnieje już w bazie!");
                    rejestracja_in_DB.show();
                }
                else {
                    PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                            + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rsGetRentalID = getRentalID.executeQuery();
                    rsGetRentalID.first();
                    Integer rentalIDc = (Integer) rsGetRentalID.getObject("rental_id");
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO cars (car_registration, car_brand, car_model, car_production, car_price, availability, rental_id) "
                            + "VALUES ('"+managerrejestracja+"', '"+managermarka+"', '"+managermodel+"', '"+managerrok+"',"
                            + "'"+managercena.replace(",",".")+"','1', "+rentalIDc+")");

                    Alert success_add = new Alert(Alert.AlertType.CONFIRMATION);
                    success_add.setContentText("Dodano nowy samochód!");
                    success_add.show();

                    m_rejestracja.clear();
                    m_marka.clear();
                    m_model.clear();
                    m_rok.clear();
                    m_cena.clear();

                    refreshTable2();

                    /* Hide TextFields, Buttons after successful add */
                    hideTextFieldsCars();

                    /* Show buttons add, edit, delete */
                    showFourCarsButtons();
                }
                conn.close();
            }
            catch (SQLException e) {
                Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * This method is used to edit a cars data
     *
     * @author Bartosz Gołąb
     */
    @FXML
    private void mEditCars() {
        String managerrejestracja = m_rejestracja.getText();
        String managermarka = m_marka.getText();
        String managermodel = m_model.getText();
        String managerrok = m_rok.getText();
        String managercena = m_cena.getText();

        if (managerrejestracja.isEmpty()) {
            Alert null_rej = new Alert(Alert.AlertType.ERROR);
            null_rej.setContentText("Proszę wybrać samochód!");
            null_rej.show();
        } else if (managermarka.isEmpty()) {
            Alert null_marka = new Alert(Alert.AlertType.ERROR);
            null_marka.setContentText("Proszę wprowadzić imie!");
            null_marka.show();
        } else if (managerrejestracja.length() < 7) {
            Alert outOfLimit_userName = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userName.setContentText("Rejestracja powinna mieć 7 lub 8 znaków!");
            outOfLimit_userName.show();
        }  else if (managerrejestracja.length() > 8) {
            Alert outOfLimit_userName1 = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userName1.setContentText("Rejestracja powinna mieć 7 lub 8 znaków!");
            outOfLimit_userName1.show();
        }   else if (managermodel.isEmpty()) {
            Alert null_model = new Alert(Alert.AlertType.ERROR);
            null_model.setContentText("Proszę wprowadzić model samochodu!");
            null_model.show();
        } else if (managerrok.isEmpty()) {
            Alert null_rok = new Alert(Alert.AlertType.ERROR);
            null_rok.setContentText("Proszę wprowadzić rok produkcji!");
            null_rok.show();
        } else if (!yearVal.isValid(managerrok)) {
            Alert not_valid_year = new Alert(Alert.AlertType.ERROR);
            not_valid_year.setContentText("Nieprawidłowy format roku!\n"
                    + "Rok powinien składać się z 4  cyfr!");
            not_valid_year.show();
        }   else if (!priceVal.isValid(managercena.replace(",","."))) {
            Alert not_valid_year = new Alert(Alert.AlertType.ERROR);
            not_valid_year.setContentText("Nieprawidłowy format ceny!\n"
                    + "Cena ma się składać wyłącznie z cyfr!");
            not_valid_year.show();
        } else if (managercena.isEmpty()) {
            Alert null_cena = new Alert(Alert.AlertType.ERROR);
            null_cena.setContentText("Proszę wprowadzić cene!");
            null_cena.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                PreparedStatement getRej = conn.prepareStatement("SELECT car_registration FROM cars WHERE "
                        + "car_registration = '" + managerrejestracja + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRej = getRej.executeQuery();

                PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                        + "login = '" + tempUsername + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRentalID = getRentalID.executeQuery();
                rsGetRentalID.first();
                Integer rentalIDc = (Integer) rsGetRentalID.getObject("rental_id");

                Statement stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE cars SET car_brand = '" + managermarka + "', car_model = '" + managermodel + "', "
                    + "car_production = '" + managerrok + "', car_price = '" + managercena.replace(",", ".") + "'"
                        + "WHERE car_registration = '" + managerrejestracja + "' AND rental_id = '" + rentalIDc + "'");

                Alert success_add = new Alert(Alert.AlertType.CONFIRMATION);
                success_add.setContentText("Zaktualizowano dane samochodu!");
                success_add.show();

                m_rejestracja.clear();
                m_marka.clear();
                m_model.clear();
                m_rok.clear();
                m_cena.clear();

                refreshTable2();

                /* Hide TextFields, Buttons after successful edit */
                hideTextFieldsCars();

                /* Show buttons add, edit, delete */
                showFourCarsButtons();
                conn.close();

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to show workers from the database to TableView
     *
     * @author Bartosz Gołąb
     * @throws SQLException exeption handling
     *
     */
    @FXML
    public void showCars() throws SQLException {
        Connection conn = DBconnect.getConnection();

        PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetRentalID2 = getRentalID.executeQuery();
        rsGetRentalID2.first();
        Integer rentalID2 = (Integer) rsGetRentalID2.getObject("rental_id");

        ResultSet rsusers2 = conn.createStatement().executeQuery("SELECT c.car_registration, c.car_brand, c.car_model, c.car_production, "
                + "c.car_price, c.availability, r.rental_name FROM cars c, rentals r  WHERE c.rental_id ='"+rentalID2+"' AND r.rental_id = c.rental_id");


        while (rsusers2.next()) {
            obslist2.add(new manager_cars_getter_setter(rsusers2.getString("car_registration"), rsusers2.getString("car_brand"),
                    rsusers2.getString("car_model"), rsusers2.getInt("car_production"),
                    rsusers2.getDouble("car_price"), rsusers2.getBoolean("availability"),
                    rsusers2.getString("rental_name")));
        }

        m_colrejestracja.setCellValueFactory(managerrejestracja -> managerrejestracja.getValue().mrejestracjaProperty());
        m_colmarka.setCellValueFactory(managermarka -> managermarka.getValue().mmarkaProperty());
        m_colmodel.setCellValueFactory(managermodel -> managermodel.getValue().mmodelProperty());
        m_colrok.setCellValueFactory(cellData -> cellData.getValue().mrokProperty().asObject());
        m_colcena.setCellValueFactory(managercena -> managercena.getValue().mcenaProperty().asObject());
        m_coldostepny.setCellValueFactory(cellData -> cellData.getValue().mwypozyczonyProperty().asObject());
        m_colwypozyczalnia.setCellValueFactory(managerwypozyczalnia -> managerwypozyczalnia.getValue().mwypozyczalniaProperty());

        m_tabsamochody.setItems(obslist2);
    }

    /**
     * This method is used to delete a workers from the database
     *
     * @author Bartosz Gołąb
     */
    @FXML
    private void mDeleteWorkers() {
        String managerpesel = m_imie.getText();

        if (managerpesel.isEmpty()) {
            Alert emp_pesel = new Alert(Alert.AlertType.ERROR);
            emp_pesel.setContentText("Proszę wybrać użytkownika lub wprowadzić PESEL!");
            emp_pesel.show();
        }
        else if (!peselVal.isValid(managerpesel)) {
            Alert not_valid_pesel = new Alert(Alert.AlertType.ERROR);
            not_valid_pesel.setContentText("Nieprawidłowy format nr PESEL!\n"
                    + "Nr PESEL powinien składać się tylko i wyłącznie z 11 cyfr!");
            not_valid_pesel.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                PreparedStatement getPESEL = conn.prepareStatement("SELECT pesel FROM users WHERE "
                        + "pesel = '"+managerpesel+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetPESEL = getPESEL.executeQuery();


                if (rsGetPESEL.first()) {
                    PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                            + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rsGetRentalID = getRentalID.executeQuery();
                    rsGetRentalID.first();
                    Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("DELETE FROM users WHERE pesel = '"+managerpesel+"'");

                    PreparedStatement countEmp = conn.prepareStatement("SELECT COUNT(u.user_id) AS empCount FROM users u "
                        + "JOIN rentals r ON u.rental_id = r.rental_id WHERE r.rental_id = '"+rentalID+"' "
                            + "AND role_id = 2", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rsCountEmp = countEmp.executeQuery();
                    rsCountEmp.first();
                    int countedEmp = rsCountEmp.getInt("empCount");

                    stmt.executeUpdate("UPDATE rentals SET rental_workers = '"+countedEmp+"' "
                            + "WHERE rental_id = '"+rentalID+"'");

                    Alert success_del = new Alert(Alert.AlertType.CONFIRMATION);
                    success_del.setContentText("Pomyślnie usunięto użytkownika!");
                    success_del.show();

                    m_pesel.clear();
                    m_imie.clear();
                    m_nazwisko.clear();
                    m_login.clear();
                    m_haslo.clear();
                    m_telefon.clear();
                    refreshTable();

                    /* Hide TextFields, Buttons after successful delete */
                    hideTextFieldsOnStart();

                    /* Show buttons add, edit, delete */
                    showFourStartButtons();

                    conn.close();
                }
                else {
                    Alert mpesel_not_found = new Alert(Alert.AlertType.ERROR);
                    mpesel_not_found.setContentText("Podany PESEL nie istnieje w bazie!");
                    mpesel_not_found.show();
                    conn.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is used to delete a workers from the database
     *
     * @author Bartosz Gołąb
     */
    @FXML
    private void mDeleteCars() {
        String managerrejestracja = m_rejestracja.getText();

        if (managerrejestracja.isEmpty()) {
            Alert emp_rejestracja = new Alert(Alert.AlertType.ERROR);
            emp_rejestracja.setContentText("Proszę wybrać samochód lub wprowadzić nr. rejestracyjny!");
            emp_rejestracja.show();
        }
        else if (managerrejestracja.length() > 8) {
            Alert not_valid_rejestracja = new Alert(Alert.AlertType.ERROR);
            not_valid_rejestracja.setContentText("Nieprawidłowy format nr. rejestracyjnego!\n"
                    + "Nr rejestracyjny powinien składać się tylko i wyłącznie z 8 lub mniej znaków!");
            not_valid_rejestracja.show();
        }
        else if (managerrejestracja.length() < 7) {
            Alert not_valid_rejestracja = new Alert(Alert.AlertType.ERROR);
            not_valid_rejestracja.setContentText("Nieprawidłowy format nr. rejestracyjnego!\n"
                    + "Nr rejestracyjny powinien składać się tylko i wyłącznie z 8 lub mniej znaków!");
            not_valid_rejestracja.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                PreparedStatement getMrejestracja = conn.prepareStatement("SELECT car_registration FROM cars WHERE "
                        + "car_registration = '"+managerrejestracja+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetrejestracja= getMrejestracja.executeQuery();

                PreparedStatement getMdostep = conn.prepareStatement("SELECT availability FROM cars WHERE "
                        + "car_registration = '"+managerrejestracja+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetdostep= getMdostep.executeQuery();
                rsGetdostep.first();
                Boolean intAva = (Boolean) rsGetdostep.getObject("availability");

                    if (rsGetrejestracja.first() && intAva == true) {

                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("DELETE FROM cars WHERE car_registration = '"+managerrejestracja+"' AND availability = '1'");

                    Alert success_del = new Alert(Alert.AlertType.CONFIRMATION);
                    success_del.setContentText("Pomyślnie usunięto samochód!");
                    success_del.show();

                    m_rejestracja.clear();
                    m_marka.clear();
                    m_model.clear();
                    m_rok.clear();
                    m_cena.clear();
                    refreshTable2();

                    /* Hide TextFields, Buttons after successful edit */
                    hideTextFieldsCars();

                    /* Show buttons add, edit, delete */
                    showFourCarsButtons();

                    conn.close();
                }
                    else if (rsGetrejestracja.first() && intAva == false){
                        Alert mava_not_found = new Alert(Alert.AlertType.ERROR);
                        mava_not_found.setContentText("Nie można usunąć wypożyczononego samochodu!");
                        mava_not_found.show();
                        conn.close();
                    }
                else {
                    Alert mrej_not_found = new Alert(Alert.AlertType.ERROR);
                    mrej_not_found.setContentText("Podana rejestracja nie istnieje w bazie!");
                    mrej_not_found.show();
                    conn.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is used to refresh a workers data in Tableview
     *
     * @author Bartosz Gołąb
     */
    @FXML
    private void refreshTable() {
        try {
            obslist1.clear();
            Connection conn = DBconnect.getConnection();

            PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                    + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsGetRentalID = getRentalID.executeQuery();
            rsGetRentalID.first();
            Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

            ResultSet rs_users = conn.createStatement().executeQuery("SELECT u.pesel, u.user_name, u.user_surname, u.login, "
                    + "u.password, r.role, u.user_phone FROM users u, roles r WHERE u.role_id = r.role_id "
                    + "AND u.rental_id ='"+rentalID+"' AND r.role = 'employee'");

            while (rs_users.next()) {
                obslist1.add(new manager_workers_getter_setter(rs_users.getString("pesel"), rs_users.getString("user_name"),
                        rs_users.getString("user_surname"), rs_users.getString("login"),
                        rs_users.getString("password"), rs_users.getString("user_phone")));
            }
            conn.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method is used to refresh a workers data in Tableview
     *
     * @author Bartosz Gołąb
     */
    @FXML
    private void refreshTable2() {
        try {
            obslist2.clear();
            Connection conn = DBconnect.getConnection();

            PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                    + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsGetRentalID2 = getRentalID.executeQuery();
            rsGetRentalID2.first();
            Integer rentalID2 = (Integer) rsGetRentalID2.getObject("rental_id");

            ResultSet rs_cars = conn.createStatement().executeQuery("SELECT c.car_registration, c.car_brand, c.car_model, c.car_production, "
                    + "c.car_price, c.availability, r.rental_name FROM cars c, rentals r  WHERE c.rental_id ='"+rentalID2+"' AND r.rental_id = c.rental_id ");

            while (rs_cars.next()) {
                obslist2.add(new manager_cars_getter_setter(rs_cars.getString("car_registration"), rs_cars.getString("car_brand"),
                        rs_cars.getString("car_model"), rs_cars.getInt("car_production"),
                        rs_cars.getDouble("car_price"), rs_cars.getBoolean("availability"),
                        rs_cars.getString("rental_name")));
            }
            conn.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is used to log out of the account
     *
     * @author Bartosz Gołąb
     */

    @FXML
    public void wyloguj1() {
        if (wylog1.isArmed()) {
            final double[] xOffset = {0};
            final double[] yOffset = {0};

            try {
                Stage stage = (Stage) wylog1.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginPanel.fxml"))));
                scene.setOnMousePressed(mouseEvent -> {
                    xOffset[0] = mouseEvent.getSceneX();
                    yOffset[0] = mouseEvent.getSceneY();
                });
                scene.setOnMouseDragged(mouseEvent -> {
                    stage.setX(mouseEvent.getScreenX() - xOffset[0]);
                    stage.setY(mouseEvent.getScreenY() - yOffset[0]);
                });
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Error!");
        }
    }

    /**
     * This method is used to log out of the account
     *
     * @author Bartosz Gołąb
     */
    @FXML
    public void wyloguj2() {
        if (wylog2.isArmed()) {
            final double[] xOffset = {0};
            final double[] yOffset = {0};

            try {
                Stage stage = (Stage) wylog2.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginPanel.fxml"))));
                scene.setOnMousePressed(mouseEvent -> {
                    xOffset[0] = mouseEvent.getSceneX();
                    yOffset[0] = mouseEvent.getSceneY();
                });
                scene.setOnMouseDragged(mouseEvent -> {
                    stage.setX(mouseEvent.getScreenX() - xOffset[0]);
                    stage.setY(mouseEvent.getScreenY() - yOffset[0]);
                });
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Error!");
        }
    }
}
