package com.example.car_rental_system;

import com.example.car_rental_system.Validators.peselVal;
import com.example.car_rental_system.Validators.phoneNumberVal;
import com.example.car_rental_system.DBconnect.DBconnect;
import com.example.car_rental_system.admin_getter_setter.admin_getter_setter;
import com.example.car_rental_system.admin_getter_setter.admin_users_table;
import com.example.car_rental_system.admin_getter_setter.rental_getter_setter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
 * This class is for Admin Controller
 *
 * @author Piotr Janik
 * @author Patryk Midura
 */
public class AdminController {
    /* Admin Panel Buttons */
    @FXML
    private Button add_employee;
    @FXML
    private Button delete_employee;
    @FXML
    private Button edit_employee;
    @FXML
    private Button modify_button;
    @FXML
    private Button modify_button2;
    @FXML
    private Button modify_button3;
    @FXML
    private Button return_button;
    @FXML
    private Button return_button2;
    @FXML
    private Button return_button3;
    @FXML
    private Button add_rental;
    @FXML
    private Button delete_rental;
    @FXML
    private Button edit_rental;
    @FXML
    private Button add_car;
    @FXML
    private Button delete_car;
    @FXML
    private Button edit_car;
    @FXML
    private Button logout_button;
    @FXML
    private ComboBox<String> roles_items;
    @FXML
    ObservableList<String> roles_items_list = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> rentals_items;
    @FXML
    ObservableList<String> rentals_items_list = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> rental_name;
    @FXML
    ObservableList<String> rental_name_list = FXCollections.observableArrayList();

    @FXML
    ObservableList<rental_getter_setter> obs_list2 = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> rental_managers;
    @FXML
    ObservableList<String> rental_managers_list = FXCollections.observableArrayList();
    @FXML
    private TableView<rental_getter_setter> show_rentals;
    @FXML
    private TableColumn<rental_getter_setter, Integer> r_id;
    @FXML
    private TableColumn<rental_getter_setter, String> r_name;
    @FXML
    private TableColumn<rental_getter_setter, String> r_city;
    @FXML
    private TableColumn<rental_getter_setter, String> r_street;
    @FXML
    private TableColumn<rental_getter_setter, Integer> r_workers;
    @FXML
    private TableColumn<rental_getter_setter, String> r_user;

    @FXML
    private TextField txt_r_name;
    @FXML
    private TextField txt_city;
    @FXML
    private TextField txt_street;
    @FXML
    private TextField txt_r_id;

    @FXML
    ObservableList<admin_getter_setter> obs_list = FXCollections.observableArrayList();
    @FXML
    private TableView<admin_getter_setter> show_cars;
    @FXML
    private TableColumn<admin_getter_setter, String> col_reg;
    @FXML
    private TableColumn<admin_getter_setter, String> col_brand;
    @FXML
    private TableColumn<admin_getter_setter, String> col_model;
    @FXML
    private TableColumn<admin_getter_setter, String> col_rent;
    @FXML
    private TableColumn<admin_getter_setter, Integer> col_year;
    @FXML
    private TableColumn<admin_getter_setter, Double> col_price;
    @FXML
    private TableColumn<admin_getter_setter, Boolean> col_ava;

    @FXML
    private TextField txt_reg;
    @FXML
    private TextField txt_mark;
    @FXML
    private TextField txt_model;
    @FXML
    private TextField txt_year;
    @FXML
    private TextField txt_price;
    @FXML
    private TextField txt_ava;

    /* All users TableView */
    @FXML
    private TableView<admin_users_table> users_table;
    @FXML
    private TableColumn<admin_users_table, String> col_pesel;
    @FXML
    private TableColumn<admin_users_table, String> col_user_name;
    @FXML
    private TableColumn<admin_users_table, String> col_user_surname;
    @FXML
    private TableColumn<admin_users_table, String> col_login;
    @FXML
    private TableColumn<admin_users_table, String> col_password;
    @FXML
    private TableColumn<admin_users_table, String> col_role;
    @FXML
    private TableColumn<admin_users_table, String> col_user_phone;

    ObservableList<admin_users_table> users_list = FXCollections.observableArrayList();

    /* TextFields Users Table */
    @FXML
    private TextField txt_pesel;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_surname;
    @FXML
    private TextField txt_login;
    @FXML
    private PasswordField txt_password;
    @FXML
    private TextField txt_phone;

    /**
     * This method is used to initialize the appropriate startup items
     *
     * @throws SQLException Exception handling
     */
    @FXML
    private void initialize() throws SQLException {
        /* Hide TextFields and ComboBox on start */
        hideTextFieldsComboBoxButtons();
        hideRentalContent();
        hideCarsContent();

        /* Modify Button actions */
        modify_button.setOnAction(event -> {
            if (modify_button.getText().equals("Dodaj pracownika")) {
                addUserToDB();
            }
            else if (modify_button.getText().equals("Usun pracownika")) {
                deleteUserFromDB();
            }
            else if (modify_button.getText().equals("Edytuj pracownika")) {
                editUser();
            }

        });

        modify_button2.setOnAction(event -> {
            if (modify_button2.getText().equals("Dodaj wypozyczalnie")) {
                addRentalToDB();
            }
            else if (modify_button2.getText().equals("Usun wypozyczalnie")) {
                deleteRentalFromDB();
            }
            else if (modify_button2.getText().equals("Edytuj wypozyczalnie")) {
                editRental();
            }

        });
        modify_button3.setOnAction(event -> {
            if (modify_button3.getText().equals("Dodaj samochod")) {
                addCarToCarsList();
            }
            else if (modify_button3.getText().equals("Usun samochod")) {
                deleteCarFromCarsList();
            }
            else if (modify_button3.getText().equals("Edytuj samochod")) {
                editCarFromCarsList();
            }

        });

        /* Return Button actions */
        return_button.setOnAction(event -> {
            modify_button.setText("");
            hideTextFieldsComboBoxButtons();
            showButtonsAddEditDelete();
            users_table.getSelectionModel().clearSelection();
            txt_pesel.clear();
            txt_name.clear();
            txt_surname.clear();
            txt_login.clear();
            txt_password.clear();
            txt_phone.clear();
            refreshRole();
            refreshRentals();
        });

        return_button2.setOnAction(event -> {
            modify_button2.setText("");
            hideRentalContent();
            showRentalButtons();
            show_rentals.getSelectionModel().clearSelection();
            txt_r_name.clear();
            txt_city.clear();
            txt_street.clear();
            txt_r_id.clear();
            refreshManagers();
            refreshRentals();
        });

        return_button3.setOnAction(event -> {
            modify_button3.setText("");
            hideCarsContent();
            showCarsButtons();
            show_cars.getSelectionModel().clearSelection();
            txt_reg.clear();
            txt_mark.clear();
            txt_model.clear();
            txt_year.clear();
            txt_price.clear();
            txt_ava.clear();
            refreshCarsTable();
            refreshRentals();
        });

        /* Get roles from database to ComboBox */
        getRolesFromDB();

        /* Get rentals from database to ComboBox */
        getRentalsFromDB();

        getRentalsFromDB2();

        /* Set visible to ComboBox with rentals when role employee is selected */
        roles_items.valueProperty().addListener((obsVal, oldVal, newVal) -> {
            if (roles_items.getValue() == null) {
                rentals_items.setVisible(false);
            }
            else if (roles_items.getValue().equals("employee")) {
                rentals_items.setVisible(true);
            }
            else {
                rentals_items.setVisible(false);
            }
        });

        /* Get managers from database to ComboBox */
        getManagersFromDB();

        /* Show Cars */
        showCars();

        /* Show Rentals */
        showRentals();

        /* Show all users data from database in TableView */
        showAllUsers();
    }

    /**
     * This method is for showing 3 buttons (Add, Delete, Edit user)
     *
     * @author Patryk Midura
     */
    @FXML
    private void showButtonsAddEditDelete() {
        add_employee.setVisible(true);
        edit_employee.setVisible(true);
        delete_employee.setVisible(true);
    }

    /**
     * This method is for hiding TextFields, ComboBox and 2 buttons
     *
     * @author Patryk Midura
     */
    @FXML
    private void hideTextFieldsComboBoxButtons() {
        txt_pesel.setVisible(false);
        txt_name.setVisible(false);
        txt_surname.setVisible(false);
        txt_login.setVisible(false);
        txt_password.setVisible(false);
        txt_phone.setVisible(false);
        roles_items.setVisible(false);
        rentals_items.setVisible(false);
        modify_button.setVisible(false);
        return_button.setVisible(false);
    }

    /**
     * This method is used to show relevant items when adding a user
     *
     * @author Patryk Midura
     */
    @FXML
    private void showTextFieldsComboBoxButtonOnAdding() {
        users_table.getSelectionModel().clearSelection();

        txt_pesel.setVisible(true);
        txt_pesel.setEditable(true);
        txt_name.setVisible(true);
        txt_name.setEditable(true);
        txt_surname.setVisible(true);
        txt_surname.setEditable(true);
        txt_login.setVisible(true);
        txt_login.setEditable(true);
        txt_password.setVisible(true);
        txt_password.setEditable(true);
        txt_phone.setVisible(true);
        txt_phone.setEditable(true);
        roles_items.setVisible(true);
        modify_button.setVisible(true);
        modify_button.setText("Dodaj pracownika");
        add_employee.setVisible(false);
        edit_employee.setVisible(false);
        delete_employee.setVisible(false);
        return_button.setVisible(true);
        return_button.setText("Powrót");
    }

    /**
     * This method is used to show relevant items when editing a user
     *
     * @author Patryk Midura
     */
    @FXML
    private void showTextFieldsComboBoxButtonOnEditing() {
        users_table.getSelectionModel().clearSelection();

        txt_pesel.setVisible(true);
        txt_pesel.setEditable(false);
        txt_name.setVisible(true);
        txt_surname.setVisible(true);
        txt_login.setVisible(true);
        txt_login.setEditable(false);
        txt_password.setVisible(true);
        txt_password.setEditable(false);
        txt_phone.setVisible(true);
        txt_phone.setEditable(false);
        roles_items.setVisible(false);
        modify_button.setVisible(true);
        modify_button.setText("Edytuj pracownika");
        add_employee.setVisible(false);
        edit_employee.setVisible(false);
        delete_employee.setVisible(false);
        return_button.setVisible(true);
        return_button.setText("Powrót");

        users_table.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_button.getText().equals("Edytuj pracownika")) {
                txt_pesel.setText(newVal.getPesel());
                txt_name.setText(newVal.getImie());
                txt_surname.setText(newVal.getNazwisko());
                txt_login.setText(newVal.getLogin());
                txt_password.setText(newVal.getHaslo());
                txt_phone.setText(newVal.getNrTelefonu());
            }
        });
    }

    /**
     * This method is used to show relevant items when deleting a user
     *
     * @author Patryk Midura
     */
    @FXML
    private void showPESELButtonOnDeleting() {
        users_table.getSelectionModel().clearSelection();

        txt_pesel.setVisible(true);
        txt_pesel.setEditable(true);
        modify_button.setVisible(true);
        modify_button.setText("Usun pracownika");
        add_employee.setVisible(false);
        edit_employee.setVisible(false);
        delete_employee.setVisible(false);
        return_button.setVisible(true);
        return_button.setText("Powrót");

        users_table.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_button.getText().equals("Usun pracownika")) {
                txt_pesel.setText(newVal.getPesel());
            }
        });
    }

    /**
     * This method is used to show 3 buttons in rental tab (add/delete/edit)
     *
     * @author Piotr Janik
     */

    @FXML
    private void showRentalButtons() {
        add_rental.setVisible(true);
        delete_rental.setVisible(true);
        edit_rental.setVisible(true);
    }
    /**
     * This method is used to hide buttons and textfields in rental tab
     *
     * @author Piotr Janik
     */
    @FXML
    private void hideRentalContent() {
        txt_r_name.setVisible(false);
        txt_city.setVisible(false);
        txt_street.setVisible(false);
        txt_r_id.setVisible(false);
        rental_managers.setVisible(false);
        modify_button2.setVisible(false);
        return_button2.setVisible(false);
    }
    /**
     * This method is used to show 3 buttons in cars tab (add/delete/edit)
     *
     * @author Piotr Janik
     */
    @FXML
    private void showCarsButtons() {
        add_car.setVisible(true);
        delete_car.setVisible(true);
        edit_car.setVisible(true);
    }
    /**
     * This method is used to hide buttons and textfields in cars tab
     *
     * @author Piotr Janik
     */
    @FXML
    private void hideCarsContent() {
        txt_reg.setVisible(false);
        txt_mark.setVisible(false);
        txt_model.setVisible(false);
        txt_year.setVisible(false);
        txt_price.setVisible(false);
        txt_ava.setVisible(false);
        rental_name.setVisible(false);
        modify_button3.setVisible(false);
        return_button3.setVisible(false);
    }
    /**
     * This method is used to show appropriate buttons and textfields required for adding new car to DB
     *
     * @author Piotr Janik
     */
    @FXML
    private void showCarsContentOnAdding() {
        show_cars.getSelectionModel().clearSelection();
        txt_reg.setVisible(true);
        txt_mark.setVisible(true);
        txt_model.setVisible(true);
        txt_year.setVisible(true);
        txt_price.setVisible(true);
        txt_ava.setVisible(true);
        rental_name.setVisible(true);
        modify_button3.setVisible(true);
        modify_button3.setText("Dodaj samochod");
        add_car.setVisible(false);
        edit_car.setVisible(false);
        delete_car.setVisible(false);
        return_button3.setVisible(true);
        return_button3.setText("Powrót");
    }
    /**
     * This method is used to show appropriate buttons and textfields required for deleting car from DB
     *
     * @author Piotr Janik
     */
    @FXML
    private void showRegButtonOnDeleting() {
        show_cars.getSelectionModel().clearSelection();

        txt_reg.setVisible(true);
        modify_button3.setVisible(true);
        modify_button3.setText("Usun samochod");
        add_car.setVisible(false);
        edit_car.setVisible(false);
        delete_car.setVisible(false);
        return_button3.setVisible(true);
        return_button3.setText("Powrót");

        show_cars.getSelectionModel().selectedItemProperty().addListener((show_rentals, oldVal, newVal) -> {
            if (newVal != null && modify_button3.getText().equals("Usun samochod")) {
                txt_reg.setText(newVal.getCar_registration());
            }
        });
    }
    /**
     * This method is used to show appropriate buttons and textfields required for editing car from DB
     *
     * @author Piotr Janik
     */
    @FXML
    private void showCarContentOnEditing(){
        show_cars.getSelectionModel().clearSelection();
        txt_reg.setVisible(true);
        txt_reg.setEditable(false);
        txt_mark.setVisible(true);
        txt_model.setVisible(true);
        txt_year.setVisible(true);
        txt_price.setVisible(true);
        txt_ava.setVisible(true);
        rental_name.setVisible(true);
        modify_button3.setVisible(true);
        modify_button3.setText("Edytuj samochod");
        add_car.setVisible(false);
        edit_car.setVisible(false);
        delete_car.setVisible(false);
        return_button3.setVisible(true);
        return_button3.setText("Powrót");

        show_cars.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_button3.getText().equals("Edytuj samochod")) {
                txt_reg.setText(newVal.getCar_registration());
                txt_mark.setText(newVal.getCar_brand());
                txt_model.setText(newVal.getCar_model());
                txt_year.setText(String.valueOf(newVal.getCar_production()));
                txt_price.setText(String.valueOf(newVal.getCar_price()));
            }
        });
    }
    /**
     * This method is used to show appropriate buttons and textfields required for adding new rental to DB
     *
     * @author Piotr Janik
     */
    @FXML
    private void showRentalContentOnAdding() {
        show_rentals.getSelectionModel().clearSelection();
        txt_r_name.setVisible(true);
        txt_city.setVisible(true);
        txt_street.setVisible(true);
        rental_managers.setVisible(true);
        modify_button2.setVisible(true);
        modify_button2.setText("Dodaj wypozyczalnie");
        add_rental.setVisible(false);
        edit_rental.setVisible(false);
        delete_rental.setVisible(false);
        return_button2.setVisible(true);
        return_button2.setText("Powrót");
    }
    /**
     * This method is used to show appropriate buttons and textfields required for deleting rental from DB
     *
     * @author Piotr Janik
     */
    @FXML
    private void showNameButtonOnDeleting() {
        show_rentals.getSelectionModel().clearSelection();

        txt_r_name.setVisible(true);
        modify_button2.setVisible(true);
        modify_button2.setText("Usun wypozyczalnie");
        add_rental.setVisible(false);
        edit_rental.setVisible(false);
        delete_rental.setVisible(false);
        return_button2.setVisible(true);
        return_button2.setText("Powrót");

        show_rentals.getSelectionModel().selectedItemProperty().addListener((show_rentals, oldVal, newVal) -> {
            if (newVal != null && modify_button2.getText().equals("Usun wypozyczalnie")) {
                txt_r_name.setText(newVal.getRental_name());
            }
        });
    }
    /**
     * This method is used to show appropriate buttons and textfields required for editing rental in DB
     *
     * @author Piotr Janik
     */
    @FXML
    private void showContentAndIdOnEditing() {
        show_rentals.getSelectionModel().clearSelection();

        txt_r_id.setVisible(true);
        txt_r_name.setVisible(true);
        txt_city.setVisible(true);
        txt_street.setVisible(true);
        rental_managers.setVisible(false);
        modify_button2.setVisible(true);
        modify_button2.setText("Edytuj wypozyczalnie");
        add_rental.setVisible(false);
        edit_rental.setVisible(false);
        delete_rental.setVisible(false);
        return_button2.setVisible(true);
        return_button2.setText("Powrót");

        show_rentals.getSelectionModel().selectedItemProperty().addListener((users_list, oldVal, newVal) -> {
            if (newVal != null && modify_button2.getText().equals("Edytuj wypozyczalnie")) {
                txt_r_name.setText(newVal.getRental_name());
                txt_city.setText(newVal.getRental_city());
                txt_street.setText(newVal.getRental_street());
                txt_r_id.setText(String.valueOf(newVal.getRental_id()));
            }
        });

    }

    /**
     * This method is used to add a user to the database
     *
     * @author Patryk Midura
     */
    @FXML
    private void addUserToDB() {
        String pesel = txt_pesel.getText();
        String imie = txt_name.getText();
        String nazwisko = txt_surname.getText();
        String login = txt_login.getText();
        String haslo = txt_password.getText();
        String tel = txt_phone.getText();
        String role = roles_items.getSelectionModel().getSelectedItem();
        String rental = rentals_items.getSelectionModel().getSelectedItem();

        /* Checking all user inputs */
        if (pesel.isEmpty()) {
            Alert null_pesel = new Alert(Alert.AlertType.ERROR);
            null_pesel.setContentText("Proszę wprowadzić pesel!");
            null_pesel.show();
        }
        /* PESEL number validation */
        else if (!peselVal.isValid(pesel)) {
            Alert not_valid_pesel = new Alert(Alert.AlertType.ERROR);
            not_valid_pesel.setContentText("Nieprawidłowy format nr PESEL!\n"
                + "Nr PESEL powinien składać się tylko i wyłącznie z 11 cyfr!");
            not_valid_pesel.show();
        }
        else if (imie.isEmpty()) {
            Alert null_imie = new Alert(Alert.AlertType.ERROR);
            null_imie.setContentText("Proszę wprowadzić imie!");
            null_imie.show();
        }
        /* User name length validation */
        else if (imie.length() > 20) {
            Alert outOfLimit_userName = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userName.setContentText("Przekroczono limit 20 znaków w polu Imię!");
            outOfLimit_userName.show();
        }
        else if (nazwisko.isEmpty()) {
            Alert null_nazwisko = new Alert(Alert.AlertType.ERROR);
            null_nazwisko.setContentText("Proszę wprowadzić nazwisko!");
            null_nazwisko.show();
        }
        /* User surname length validation */
        else if (nazwisko.length() > 20) {
            Alert outOfLimit_userSurname = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userSurname.setContentText("Przekroczono limit 20 znaków w polu Nazwisko!");
            outOfLimit_userSurname.show();
        }
        else if (login.isEmpty()) {
            Alert null_login = new Alert(Alert.AlertType.ERROR);
            null_login.setContentText("Proszę wprowadzić login!");
            null_login.show();
        }
        /* User login length validation */
        else if (login.length() > 30) {
            Alert outOfLimit_userLogin = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userLogin.setContentText("Przekroczono limit 30 znaków w polu Login!");
            outOfLimit_userLogin.show();
        }
        else if (haslo.isEmpty()) {
            Alert null_haslo = new Alert(Alert.AlertType.ERROR);
            null_haslo.setContentText("Proszę wprowadzić haslo!");
            null_haslo.show();
        }
        else if (tel.isEmpty()) {
            Alert null_tel = new Alert(Alert.AlertType.ERROR);
            null_tel.setContentText("Proszę wprowadzić telefon!");
            null_tel.show();
        }
        /* Phone number validation */
        else if (!phoneNumberVal.isValid(tel)) {
            Alert not_valid_phone = new Alert(Alert.AlertType.ERROR);
            not_valid_phone.setContentText("Nieprawidłowy format nr telefonu!\n"
                + "Nr telefonu powinien składać się tylko i wyłącznie z 9 cyfr!");
            not_valid_phone.show();
        }
        else {
            try {
                /* Hashing user password from input */
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(haslo.getBytes(), 0, haslo.length());
                String hashPass = new BigInteger(1, md.digest()).toString(16);

                Connection conn = DBconnect.getConnection();

                /* Getting role id from selected role name */
                PreparedStatement getRoleID = conn.prepareStatement("SELECT role_id FROM roles WHERE "
                        + "role = '"+role+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRoleID = getRoleID.executeQuery();

                /* Getting rental id from selected rental name */
                PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM rentals WHERE "
                        + "rental_name = '"+rental+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRentalID = getRentalID.executeQuery();

                /* Getting PESEL from database */
                PreparedStatement getPESEL = conn.prepareStatement("SELECT pesel FROM users WHERE "
                    + "pesel = '"+pesel+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetPESEL = getPESEL.executeQuery();

                /* Getting phone number from database */
                PreparedStatement getPhone = conn.prepareStatement("SELECT user_phone FROM users WHERE "
                    + "user_phone = '"+tel+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetPhone = getPhone.executeQuery();

                /* Getting user login from database */
                PreparedStatement getLogin = conn.prepareStatement("SELECT login FROM users WHERE "
                    + "login = '"+login+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetLogin = getLogin.executeQuery();

                /* Checking if the role has been selected from the list */
                if (!rsGetRoleID.first()) {
                    Alert null_role = new Alert(Alert.AlertType.ERROR);
                    null_role.setContentText("Proszę wybrać stanowisko!");
                    null_role.show();
                }
                /* Checking if the rental has been selected from the list */
                else if (role.equals("employee") && !rsGetRentalID.first()) {
                    Alert null_rental = new Alert(Alert.AlertType.ERROR);
                    null_rental.setContentText("Proszę wybrać wypożyczalnię!");
                    null_rental.show();
                }
                /* Checking if the PESEL exists in the database */
                else if (rsGetPESEL.first()) {
                    Alert pesel_in_DB = new Alert(Alert.AlertType.ERROR);
                    pesel_in_DB.setContentText("Podany PESEL istnieje już w bazie!");
                    pesel_in_DB.show();
                }
                /* Checking if the Phone Number exists in the database */
                else if (rsGetPhone.first()) {
                    Alert phone_in_DB = new Alert(Alert.AlertType.ERROR);
                    phone_in_DB.setContentText("Podany nr telefonu istnieje już w bazie!");
                    phone_in_DB.show();
                }
                /* Checking if the login exists in the database */
                else if (rsGetLogin.first()) {
                    Alert login_in_DB = new Alert(Alert.AlertType.ERROR);
                    login_in_DB.setContentText("Podany login istnieje już w bazie!");
                    login_in_DB.show();
                }
                else {
                    if (role.equals("manager") && !rsGetRentalID.first()) {
                        Integer role_id = (Integer) rsGetRoleID.getObject("role_id");
                        int rental_id = 0;

                        /* Insert new user to database */
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate("INSERT INTO users (pesel, user_name, user_surname, login, password, user_phone, "
                                + "role_id, rental_id) VALUES ('"+pesel+"', '"+imie+"', '"+nazwisko+"', '"+login+"', '"+hashPass+"', "
                                + "'"+tel+"', "+role_id+", "+rental_id+")");
                    }
                    else if (rsGetRentalID.first()) {
                        Integer role_id = (Integer) rsGetRoleID.getObject("role_id");
                        Integer rental_id = (Integer) rsGetRentalID.getObject("rental_id");

                        /* Insert new user to database */
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate("INSERT INTO users (pesel, user_name, user_surname, login, password, user_phone, "
                                + "role_id, rental_id) VALUES ('"+pesel+"', '"+imie+"', '"+nazwisko+"', '"+login+"', '"+hashPass+"', "
                                + "'"+tel+"', "+role_id+", "+rental_id+")");

                        /* Count employees in rental */
                        PreparedStatement countEmp = conn.prepareStatement("SELECT COUNT(u.user_id) AS empCount FROM users u "
                            + "JOIN rentals r ON u.rental_id = r.rental_id WHERE r.rental_id = '"+rental_id+"' "
                                + "AND u.role_id = '"+role_id+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ResultSet rsCountEmp = countEmp.executeQuery();
                        rsCountEmp.first();
                        int countedEmp = rsCountEmp.getInt("empCount");

                        /* Update rentals table */
                        stmt.executeUpdate("UPDATE rentals SET rental_workers = '"+countedEmp+"' "
                            + "WHERE rental_id = '"+rental_id+"'");
                    }

                    Alert success_add = new Alert(Alert.AlertType.CONFIRMATION);
                    success_add.setContentText("Dodano nowego użytkownika!");
                    success_add.show();

                    txt_pesel.clear();
                    txt_name.clear();
                    txt_surname.clear();
                    txt_login.clear();
                    txt_password.clear();
                    txt_phone.clear();

                    refreshRole();
                    refreshRentals();
                    refreshTable();
                    refreshRentalTable();

                    /* Hide TextFields, ComboBox, Button after successful add */
                    hideTextFieldsComboBoxButtons();

                    /* Show buttons add, edit, delete */
                    showButtonsAddEditDelete();

                    /* Refresh managers after successful add */
                    refreshManagers();
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
     * This method is used to edit user data in the database
     *
     * @author Patryk Midura
     */
    @FXML
    private void editUser() {
        String pesel = txt_pesel.getText();
        String imie = txt_name.getText();
        String nazwisko = txt_surname.getText();
        String login = txt_login.getText();
        String tel = txt_phone.getText();

        /* Checking all user inputs */
        if (pesel.isEmpty() && login.isEmpty() && tel.isEmpty()) {
            Alert not_selected = new Alert(Alert.AlertType.ERROR);
            not_selected.setContentText("Proszę wybrać użytkownika!");
            not_selected.show();
        }
        else if (imie.isEmpty()) {
            Alert null_imie = new Alert(Alert.AlertType.ERROR);
            null_imie.setContentText("Proszę wprowadzić imie!");
            null_imie.show();
        }
        /* User name length validation */
        else if (imie.length() > 20) {
            Alert outOfLimit_userName = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userName.setContentText("Przekroczono limit 20 znaków w polu Imię!");
            outOfLimit_userName.show();
        }
        else if (nazwisko.isEmpty()) {
            Alert null_nazwisko = new Alert(Alert.AlertType.ERROR);
            null_nazwisko.setContentText("Proszę wprowadzić nazwisko!");
            null_nazwisko.show();
        }
        /* User surname length validation */
        else if (nazwisko.length() > 20) {
            Alert outOfLimit_userSurname = new Alert(Alert.AlertType.ERROR);
            outOfLimit_userSurname.setContentText("Przekroczono limit 20 znaków w polu Nazwisko!");
            outOfLimit_userSurname.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                /* Edit user data to database */
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE users SET user_name = '"+imie+"', user_surname = '"+nazwisko+"' "
                    + "WHERE pesel = '"+pesel+"' AND login = '"+login+"' AND user_phone = '"+tel+"'");

                Alert success_edit = new Alert(Alert.AlertType.CONFIRMATION);
                success_edit.setContentText("Dane użytkownika zostały zaktualizowane!");
                success_edit.show();

                txt_pesel.clear();
                txt_name.clear();
                txt_surname.clear();
                txt_login.clear();
                txt_password.clear();
                txt_phone.clear();

                refreshRole();
                refreshRentals();
                refreshTable();
                refreshRentalTable();

                /* Hide TextFields, ComboBox, Button after successful edit */
                hideTextFieldsComboBoxButtons();

                /* Show buttons add, edit, delete */
                showButtonsAddEditDelete();

                conn.close();
            }
            catch (SQLException e) {
                Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * This method is used to remove a user from the database
     *
     * @author Patryk Midura
     */
    @FXML
    private void deleteUserFromDB() {
        String pesel = txt_pesel.getText();

        if (pesel.isEmpty()) {
            Alert emp_pesel = new Alert(Alert.AlertType.ERROR);
            emp_pesel.setContentText("Proszę wybrać użytkownika lub wprowadzić PESEL!");
            emp_pesel.show();
        }
        /* PESEL number validation */
        else if (!peselVal.isValid(pesel)) {
            Alert not_valid_pesel = new Alert(Alert.AlertType.ERROR);
            not_valid_pesel.setContentText("Nieprawidłowy format nr PESEL!\n"
                + "Nr PESEL powinien składać się tylko i wyłącznie z 11 cyfr!");
            not_valid_pesel.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                /* Getting PESEL from database */
                PreparedStatement getPESEL = conn.prepareStatement("SELECT pesel FROM users WHERE "
                        + "pesel = '"+pesel+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetPESEL = getPESEL.executeQuery();

                /* Getting rental ID from database */
                PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                        + "pesel = '"+pesel+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRentalID = getRentalID.executeQuery();
                rsGetRentalID.first();
                int rental_id = rsGetRentalID.getInt("rental_id");

                /* Getting role ID from database */
                PreparedStatement getRoleID = conn.prepareStatement("SELECT role_id FROM users WHERE "
                        + "pesel = '"+pesel+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRoleID = getRoleID.executeQuery();
                rsGetRoleID.first();
                int role_id = rsGetRoleID.getInt("role_id");

                /* Checking if the PESEL exists in the database */
                if (rsGetPESEL.first()) {
                    /* Delete user from database */
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("DELETE FROM users WHERE pesel = '"+pesel+"'");

                    /* Count employees in rental */
                    PreparedStatement countEmp = conn.prepareStatement("SELECT COUNT(u.user_id) AS empCount FROM users u "
                            + "JOIN rentals r ON u.rental_id = r.rental_id WHERE r.rental_id = '"+rental_id+"' "
                            + "AND u.role_id = '"+role_id+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rsCountEmp = countEmp.executeQuery();
                    rsCountEmp.first();
                    int countedEmp = rsCountEmp.getInt("empCount");

                    /* Update rentals table */
                    stmt.executeUpdate("UPDATE rentals SET rental_workers = '"+countedEmp+"' "
                            + "WHERE rental_id = '"+rental_id+"'");

                    Alert success_del = new Alert(Alert.AlertType.CONFIRMATION);
                    success_del.setContentText("Pomyślnie usunięto użytkownika!");
                    success_del.show();

                    txt_pesel.clear();
                    refreshRole();
                    refreshTable();
                    refreshRentalTable();

                    /* Hide TextFields, ComboBox, Button after successful add */
                    hideTextFieldsComboBoxButtons();

                    /* Show buttons add, edit, delete */
                    showButtonsAddEditDelete();

                    conn.close();
                }
                else {
                    Alert pesel_not_found = new Alert(Alert.AlertType.ERROR);
                    pesel_not_found.setContentText("Podany PESEL nie istnieje w bazie!");
                    pesel_not_found.show();
                    conn.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is used to retrieve user roles from the database
     *
     * @author Patryk Midura
     */
    @FXML
    private void getRolesFromDB() {
        try {
            Connection conn = DBconnect.getConnection();
            ResultSet roles = conn.createStatement().executeQuery("SELECT role FROM roles WHERE role_id > 1");

            while (roles.next()) {
                roles_items_list.add(roles.getString("role"));
            }
            roles_items.setItems(roles_items_list);
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to refresh the user role
     *
     * @author Patryk Midura
     */
    @FXML
    private void refreshRole() {
        roles_items_list.clear();
        roles_items.getSelectionModel().clearSelection();
        roles_items.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(roles_items.getPromptText());
                }
                else {
                    setText(item);
                }
            }
        });
        getRolesFromDB();
    }

    /**
     * This method is used to refresh the users table
     *
     * @author Patryk Midura
     */
    @FXML
    private void refreshTable() {
        try {
            users_list.clear();
            Connection conn = DBconnect.getConnection();

            String query = "SELECT pesel, user_name, user_surname, login, password,"
                + "role, user_phone FROM users, roles WHERE users.role_id = roles.role_id";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs_users = ps.executeQuery();

            while (rs_users.next()) {
                users_list.add(new admin_users_table(rs_users.getString("pesel"),
                    rs_users.getString("user_name"),
                        rs_users.getString("user_surname"), rs_users.getString("login"),
                            rs_users.getString("password"), rs_users.getString("role"),
                                rs_users.getString("user_phone")));
            }
            conn.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is used to retrieve rental names from the database
     *
     * @author Piotr Janik
     */
    @FXML
    private void getRentalsFromDB() {
        try {
            Connection conn = DBconnect.getConnection();
            ResultSet rentals = conn.createStatement().executeQuery("SELECT rental_name FROM rentals");

            while (rentals.next()) {
                rentals_items_list.add(rentals.getString("rental_name"));
            }
            rentals_items.setItems(rentals_items_list);
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getRentalsFromDB2() {
        try {
            Connection conn = DBconnect.getConnection();
            ResultSet rentals = conn.createStatement().executeQuery("SELECT rental_name FROM rentals");

            while (rentals.next()) {
                rental_name_list.add(rentals.getString("rental_name"));
            }
            rental_name.setItems(rental_name_list);
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is used to refresh rentals from database
     *
     * @author Piotr Janik
     */
    @FXML
    private void refreshRentals() {
        rentals_items_list.clear();
        rentals_items.getSelectionModel().clearSelection();
        rentals_items.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(rentals_items.getPromptText());
                }
                else {
                    setText(item);
                }
            }
        });
        getRentalsFromDB();
    }
    private void refreshRentals2() {
        rental_name_list.clear();
        rental_name.getSelectionModel().clearSelection();
        rental_name.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(rental_name.getPromptText());
                }
                else {
                    setText(item);
                }
            }
        });
        getRentalsFromDB2();
    }
    /**
     * This method is used to retrieve manager surnames from the database
     *
     * @author Piotr Janik
     */
    @FXML
    private void getManagersFromDB() {
        try {
            Connection conn = DBconnect.getConnection();

            /* Select manager role id from database */
            PreparedStatement getRoleID = conn.prepareStatement("SELECT role_id FROM roles WHERE "
                    + "role = 'manager'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsGetRoleID = getRoleID.executeQuery();

            while (rsGetRoleID.next()) {
                int role_id = rsGetRoleID.getInt(1);
                ResultSet managers = conn.createStatement().executeQuery("SELECT user_surname FROM users WHERE "
                    + "rental_id = 0 AND role_id = '"+role_id+"'");
                while (managers.next()) {
                    rental_managers_list.add(managers.getString("user_surname"));
                }
            }
            rental_managers.setItems(rental_managers_list);
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is used to refresh managers from the database
     *
     * @author Piotr Janik
     */
    @FXML
    private void refreshManagers() {
        rental_managers_list.clear();
        rental_managers.getSelectionModel().clearSelection();
        rental_managers.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(rental_managers.getPromptText());
                }
                else {
                    setText(item);
                }
            }
        });
        getManagersFromDB();
    }
    /**
     * This method is used to add new rental to database
     *
     * @author Piotr Janik
     */
    @FXML
    private void addRentalToDB() {

        String nazwa = txt_r_name.getText();
        String miasto = txt_city.getText();
        String ulica = txt_street.getText();
        String kierownik = rental_managers.getSelectionModel().getSelectedItem();

        //walidacja textfieldow, czy nie sa puste
        if (nazwa.isEmpty()) {
            Alert null_pesel = new Alert(Alert.AlertType.ERROR);
            null_pesel.setContentText("Proszę wprowadzić nazwę wypożyczalni!");
            null_pesel.show();
        }
        else if (miasto.isEmpty()) {
            Alert null_miasto = new Alert(Alert.AlertType.ERROR);
            null_miasto.setContentText("Proszę wprowadzić miasto!");
            null_miasto.show();
        }
        else if (ulica.isEmpty()) {
            Alert null_ulica = new Alert(Alert.AlertType.ERROR);
            null_ulica.setContentText("Proszę wprowadzić ulicę!");
            null_ulica.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                PreparedStatement getManagerID = conn.prepareStatement("SELECT user_id FROM users WHERE "
                        + "user_surname = '"+kierownik+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetManagerID = getManagerID.executeQuery();

                if (!rsGetManagerID.first()) {
                    Alert null_manager = new Alert(Alert.AlertType.ERROR);
                    null_manager.setContentText("Proszę wybrać kierownika!");
                    null_manager.show();
                }
                else {
                    Integer user_id = (Integer) rsGetManagerID.getObject("user_id");

                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO rentals (user_id, rental_name, rental_city, rental_street, "
                        + "rental_workers) VALUES ('"+user_id+"', '"+nazwa+"', '"+miasto+"', '"+ulica+"', "
                            + "'0')");

                    Alert success_add = new Alert(Alert.AlertType.CONFIRMATION);
                    success_add.setContentText("Dodano nową wypożyczalnie!");
                    success_add.show();

                    txt_r_name.clear();
                    txt_city.clear();
                    txt_street.clear();

                    refreshRentalTable();

                    /* Update rental_id in users table */
                    Connection conn2 = DBconnect.getConnection();
                    Statement stmt2 = conn2.createStatement();
                    PreparedStatement getRentalID = conn2.prepareStatement("SELECT rental_id FROM rentals WHERE "
                            + "user_id = '"+user_id+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rsGetRentalID = getRentalID.executeQuery();

                    while (rsGetRentalID.next()) {
                        int rental_id = rsGetRentalID.getInt(1);
                        stmt2.executeUpdate("UPDATE users SET rental_id = '"+rental_id+"' WHERE user_id = '"+user_id+"'");
                    }
                    conn2.close();
                    refreshManagers();
                    refreshRentals();
                }
                conn.close();
            }
            catch (SQLException e) {
                Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, e);
            }

        }

    }
    /**
     * This method is used to delete rental from database
     *
     * @author Piotr Janik
     */
    @FXML
    private void deleteRentalFromDB() {
        String name= txt_r_name.getText();

        if (name.isEmpty()) {
            Alert emp_name = new Alert(Alert.AlertType.ERROR);
            emp_name.setContentText("Proszę wybrać wypożyczalnie lub wprowadzić jej nazwę!");
            emp_name.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                PreparedStatement getName = conn.prepareStatement("SELECT rental_name FROM rentals WHERE "
                        + "rental_name = '"+name+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetName = getName.executeQuery();

                /* Get rental ID from rental_name */
                PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM rentals WHERE "
                    + "rental_name = '"+name+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRentalID = getRentalID.executeQuery();

                if (rsGetName.first() && rsGetRentalID.first()) {
                    Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("UPDATE users SET rental_id = '0' WHERE rental_id = '"+rentalID+"' AND role_id = '3'");
                    stmt.executeUpdate("DELETE FROM cars WHERE rental_id = '"+rentalID+"'");
                    stmt.executeUpdate("DELETE FROM loans WHERE rental_id = '"+rentalID+"'");
                    stmt.executeUpdate("DELETE FROM rentals WHERE rental_name = '"+name+"'");
                    stmt.executeUpdate("DELETE FROM users WHERE rental_id = '"+rentalID+"' AND role_id = '2'");

                    Alert success_del = new Alert(Alert.AlertType.CONFIRMATION);
                    success_del.setContentText("Pomyślnie usunięto wypożyczalnie");
                    success_del.show();

                    txt_r_name.clear();
                    refreshRentalTable();
                    refreshRentals();
                    refreshTable();
                    refreshCarsTable();

                    conn.close();
                }
                else {
                    Alert name_not_found = new Alert(Alert.AlertType.ERROR);
                    name_not_found.setContentText("Podana wypożyczalnia nie istnieje w bazie");
                    name_not_found.show();
                    conn.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    /**
     * This method is used to edit new rental in database
     *
     * @author Piotr Janik
     */
    @FXML
    private void editRental() {
        String nazwa = txt_r_name.getText();
        String miasto = txt_city.getText();
        String ulica = txt_street.getText();
        String rental_id = txt_r_id.getText();

        /* Checking all user inputs */
        if (nazwa.isEmpty()) {
            Alert null_pesel = new Alert(Alert.AlertType.ERROR);
            null_pesel.setContentText("Proszę wprowadzić nazwę wypożyczalni!");
            null_pesel.show();
        }
        else if (miasto.isEmpty()) {
            Alert null_miasto = new Alert(Alert.AlertType.ERROR);
            null_miasto.setContentText("Proszę wprowadzić miasto!");
            null_miasto.show();
        }
        else if (ulica.isEmpty()) {
            Alert null_ulica = new Alert(Alert.AlertType.ERROR);
            null_ulica.setContentText("Proszę wprowadzić ulicę!");
            null_ulica.show();
        }
        else if (rental_id.isEmpty()) {
            Alert null_rentalID = new Alert(Alert.AlertType.ERROR);
            null_rentalID.setContentText("Proszę wybrać wypożyczalnię!");
            null_rentalID.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                //Integer user_id = (Integer) rsGetManagerID.getObject("user_id"); //maybe future functionality to change rental manager

                Statement stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE rentals SET rental_name = '"+nazwa+"', rental_city='"+miasto+"', rental_street='"+ulica+"' "
                        + "WHERE rental_id = '"+rental_id+"' ");

                Alert success_edit = new Alert(Alert.AlertType.CONFIRMATION);
                success_edit.setContentText("Dane wypożyczalni zostały zaktualizowane!");
                success_edit.show();

                txt_r_name.clear();
                txt_city.clear();
                txt_street.clear();
                txt_r_id.clear();

                refreshRentalTable();
                hideRentalContent();
                showRentalButtons();

                conn.close();
            }
            catch (SQLException e) {
                Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    /**
     * This method is used to refresh the rental table
     *
     * @author Piotr Janik
     */
    @FXML
    private void refreshRentalTable() {
        try {
            obs_list2.clear();
            Connection conn = DBconnect.getConnection();

            String query = "SELECT r.rental_id, r.rental_name, r.rental_city, r.rental_street, "
                    + "r.rental_workers, u.user_surname FROM rentals r JOIN users u ON r.user_id = u.user_id";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs_rentals = ps.executeQuery();

            while (rs_rentals.next()) {
                obs_list2.add(new rental_getter_setter(rs_rentals.getInt("rental_id"), rs_rentals.getString("rental_name"),
                        rs_rentals.getString("rental_city"), rs_rentals.getString("rental_street"),
                        rs_rentals.getInt("rental_workers"), rs_rentals.getString("user_surname")));
            }
            conn.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method is used to add new car to database
     *
     * @author Piotr Janik
     */
    @FXML
    private void addCarToCarsList() {
        String reg = txt_reg.getText();
        String mark = txt_mark.getText();
        String model = txt_model.getText();
        String year = txt_year.getText();
        String price = txt_price.getText();
        String ava = txt_ava.getText();
        String rental = rental_name.getSelectionModel().getSelectedItem();

        //walidacja textfieldow, czy nie sa puste
        if (reg.isEmpty()) {
            Alert null_pesel = new Alert(Alert.AlertType.ERROR);
            null_pesel.setContentText("Proszę wprowadzić rejestracje samochodu!");
            null_pesel.show();
        }
        else if (mark.isEmpty()) {
            Alert null_miasto = new Alert(Alert.AlertType.ERROR);
            null_miasto.setContentText("Proszę wprowadzić marke samochodu!");
            null_miasto.show();
        }
        else if (model.isEmpty()) {
            Alert null_ulica = new Alert(Alert.AlertType.ERROR);
            null_ulica.setContentText("Proszę wprowadzić model samochodu!");
            null_ulica.show();
        }
        else {
            try {
                Connection conn = DBconnect.getConnection();

                PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM rentals WHERE "
                        + "rental_name = '"+rental+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetRentalID = getRentalID.executeQuery();

                if (!rsGetRentalID.first()) {
                    Alert null_rental = new Alert(Alert.AlertType.ERROR);
                    null_rental.setContentText("Proszę wybrać wypozyczalnie!");
                    null_rental.show();
                }
                else {
                    Integer rental_id = (Integer) rsGetRentalID.getObject("rental_id");

                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO cars (rental_id, car_registration, car_brand, car_model, car_production, car_price, availability) VALUES ('"+rental_id+"', '"+reg+"', '"+mark+"', '"+model+"', '"+year+"', '"+price.replace(",", ".")+"', '"+ava+"')");

                    Alert success_add = new Alert(Alert.AlertType.CONFIRMATION);
                    success_add.setContentText("Dodano nowy samochod!");
                    success_add.show();

                    txt_reg.clear();
                    txt_mark.clear();
                    txt_model.clear();
                    txt_price.clear();
                    txt_year.clear();
                    txt_ava.clear();

                    refreshCarsTable();
                    refreshRentals();
                    refreshRentals2();

                conn.close();
            }}
            catch (SQLException e) {
                Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, e);
            }

        }

    }
    /**
     * This method is used to delete car from database
     *
     * @author Piotr Janik
     */
    @FXML
    private void deleteCarFromCarsList() {

        String reg = txt_reg.getText();

        if (reg.isEmpty()){
            Alert emp_name = new Alert(Alert.AlertType.ERROR);
            emp_name.setContentText("Proszę wybrać samochod lub wprowadzić jego rejestracje!");
            emp_name.show();
        }else{
            try {
                Connection conn = DBconnect.getConnection();


                PreparedStatement getReg = conn.prepareStatement("SELECT car_registration FROM cars WHERE "
                        + "car_registration  = '"+reg+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetReg = getReg.executeQuery();


                if (rsGetReg.first()) {

                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("DELETE FROM cars WHERE car_registration  = '"+reg+"'");

                    Alert success_del = new Alert(Alert.AlertType.CONFIRMATION);
                    success_del.setContentText("Pomyślnie usunięto samochod");
                    success_del.show();

                    txt_reg.clear();
                    refreshCarsTable();

                    conn.close();
                }
                else {
                    Alert name_not_found = new Alert(Alert.AlertType.ERROR);
                    name_not_found.setContentText("Podana rejestracja nie istnieje w bazie danych");
                    name_not_found.show();
                    conn.close();
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    /**
     * This method is used to edit new rental in database
     *
     * @author Piotr Janik
     */
    @FXML
    private void editCarFromCarsList() {
        String reg = txt_reg.getText();
        String mark = txt_mark.getText();
        String model = txt_model.getText();
        String year = txt_year.getText();
        String price = txt_price.getText();
        String ava = txt_ava.getText();
        String rental = rental_name.getSelectionModel().getSelectedItem();


        try {
            Connection conn = DBconnect.getConnection();

            PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM rentals WHERE "
                    + "rental_name = '"+rental+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsGetRentalID = getRentalID.executeQuery();

            if (!rsGetRentalID.first()) {
                Alert null_rental = new Alert(Alert.AlertType.ERROR);
                null_rental.setContentText("Proszę wybrać wypozyczalnie!");
                null_rental.show();
            }
            else {
                Integer rental_id = (Integer) rsGetRentalID.getObject("rental_id");

                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("UPDATE cars SET car_brand = '"+mark+"', car_model='"+model+"', car_production='"+year+"', car_price='"+price+"', availability = '"+ava+"', rental_id ='"+rental_id+"' "
                            + "WHERE car_registration = '"+reg+"' ");

                    Alert success_edit = new Alert(Alert.AlertType.CONFIRMATION);
                    success_edit.setContentText("Dane samochodu zostały zaktualizowane!");
                    success_edit.show();

                txt_reg.clear();
                txt_mark.clear();
                txt_model.clear();
                txt_price.clear();
                txt_year.clear();
                txt_ava.clear();

                refreshCarsTable();
                refreshRentals();
                refreshRentals2();

                conn.close();
                }
            }
            catch (SQLException e) {
                Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    /**
     * This method is used to refresh cars table
     *
     * @author Piotr Janik
     */
    @FXML
    private void refreshCarsTable() {
        try {
            obs_list.clear();
            Connection conn = DBconnect.getConnection();

            String query = "SELECT car_registration,car_brand,car_model,"
                    + "rental_name,car_production,car_price,availability FROM cars JOIN rentals ON cars.rental_id=rentals.rental_id";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs_cars = ps.executeQuery();

            while (rs_cars.next()) {
                obs_list.add(new admin_getter_setter(rs_cars.getString("car_registration"), rs_cars.getString("car_brand"),
                        rs_cars.getString("car_model"), rs_cars.getString("rental_name"),
                        rs_cars.getInt("car_production"), rs_cars.getDouble("car_price"), rs_cars.getBoolean("availability")));
            }
            conn.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is used to show all cars in tableview
     *
     * @author Piotr Janik
     */
    @FXML
    private void showCars() throws SQLException {
        col_reg.setCellValueFactory(car_registration -> car_registration.getValue().car_registrationProperty());
        col_brand.setCellValueFactory(car_brand -> car_brand.getValue().car_brandProperty());
        col_model.setCellValueFactory(car_model -> car_model.getValue().car_modelProperty());
        col_rent.setCellValueFactory(rental_name -> rental_name.getValue().rental_nameProperty());
        col_year.setCellValueFactory(cellData -> cellData.getValue().car_productionProperty().asObject());
        col_price.setCellValueFactory(cellData -> cellData.getValue().car_priceProperty().asObject());
        col_ava.setCellValueFactory(cellData -> cellData.getValue().availabilityProperty().asObject());

        Connection conn = DBconnect.getConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT car_registration,car_brand,car_model,"
                + "rental_name,car_production,car_price,availability FROM cars JOIN rentals ON cars.rental_id=rentals.rental_id");

        while (rs.next()) {
            obs_list.add(new admin_getter_setter(rs.getString("car_registration"), rs.getString("car_brand"),
                    rs.getString("car_model"), rs.getString("rental_name"), rs.getInt("car_production"),
                    rs.getDouble("car_price"), rs.getBoolean("availability")));
        }
        show_cars.setItems(obs_list);
    }
    /**
     * This method is used to show all rentals in tableview
     *
     * @author Piotr Janik
     */
    @FXML
    private void showRentals() throws SQLException {
        r_id.setCellValueFactory(cellData -> cellData.getValue().rental_idProperty().asObject());
        r_name.setCellValueFactory(r_name -> r_name.getValue().rental_nameProperty());
        r_city.setCellValueFactory(r_city -> r_city.getValue().rental_cityProperty());
        r_street.setCellValueFactory(r_street -> r_street.getValue().rental_streetProperty());
        r_workers.setCellValueFactory(cellData -> cellData.getValue().rental_workersProperty().asObject());
        r_user.setCellValueFactory(r_user -> r_user.getValue().user_idProperty());

        Connection conn = DBconnect.getConnection();
        ResultSet rs2 = conn.createStatement().executeQuery("SELECT r.rental_id, r.rental_name, r.rental_city, r.rental_street, "
                + "r.rental_workers, u.user_surname FROM rentals r JOIN users u ON r.user_id = u.user_id");

        while(rs2.next()){
            obs_list2.add(new rental_getter_setter(rs2.getInt("rental_id"), rs2.getString("rental_name"),
                    rs2.getString("rental_city"), rs2.getString("rental_street"),
                    rs2.getInt("rental_workers"),rs2.getString("user_surname")));
        }
        show_rentals.setItems(obs_list2);
    }
    /**
     * This method is used to show all users in tableview
     *
     * @author Patryk Midura
     */
    @FXML
    private void showAllUsers() throws SQLException {
        Connection conn = DBconnect.getConnection();
        ResultSet rs_users = conn.createStatement().executeQuery("SELECT pesel, user_name, user_surname, login, password,"
                + "role, user_phone FROM users, roles WHERE users.role_id = roles.role_id");

        while (rs_users.next()) {
            users_list.add(new admin_users_table(rs_users.getString("pesel"),
                rs_users.getString("user_name"), rs_users.getString("user_surname"),
                    rs_users.getString("login"), rs_users.getString("password"),
                        rs_users.getString("role"), rs_users.getString("user_phone")));
        }

        col_pesel.setCellValueFactory(pesel -> pesel.getValue().peselProperty());
        col_user_name.setCellValueFactory(imie -> imie.getValue().imieProperty());
        col_user_surname.setCellValueFactory(nazwisko -> nazwisko.getValue().nazwiskoProperty());
        col_login.setCellValueFactory(login -> login.getValue().loginProperty());
        col_password.setCellValueFactory(haslo -> haslo.getValue().hasloProperty());
        col_role.setCellValueFactory(stanowisko -> stanowisko.getValue().stanowiskoProperty());
        col_user_phone.setCellValueFactory(nrTelefonu -> nrTelefonu.getValue().nrTelefonuProperty());

        users_table.setItems(users_list);
    }
    /**
     * This method is used to logout from app
     *
     * @author Patryk Midura
     */
    @FXML
    public void logout() {
        if (logout_button.isArmed()) {
            final double[] xOffset = {0};
            final double[] yOffset = {0};

            try {
                Stage stage = (Stage) logout_button.getScene().getWindow();
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
     * This method is used to close the application
     */
    @FXML
    private void exitApp() {
        System.exit(0);
    }

}
