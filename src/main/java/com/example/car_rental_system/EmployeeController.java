package com.example.car_rental_system;

import com.example.car_rental_system.DBconnect.DBconnect;
import com.example.car_rental_system.Validators.peselVal;
import com.example.car_rental_system.Validators.phoneNumberVal;
import com.example.car_rental_system.admin_getter_setter.admin_users_table;
import com.example.car_rental_system.employee_getter_setter.cars_list;
import com.example.car_rental_system.employee_getter_setter.rental_list;
import com.example.car_rental_system.employee_getter_setter.return_list;
import com.example.car_rental_system.employee_getter_setter.wished_cars;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mycompany.PDFgenerator.PDF;

/**
 * This class is for Employee Controller
 *
 * @author Maciej Ziółkowski
 * @author Patryk Midura
 */
public class EmployeeController {

//TextField's
    @FXML
        private DatePicker dni;
    @FXML
        private TextField cena;
    @FXML
        private TextField cena_final;
    @FXML
        private TextField days_textfield;
    @FXML
        private TextField price_per_day;
    @FXML
        private TextField plate;
    @FXML
        private TextField brand;
    @FXML
        private TextField model;
    @FXML
        private TextField name;
    @FXML
        private TextField surname;
    @FXML
        private TextField pesel;
    @FXML
        private TextField phone_nr;
    @FXML
        private TextField plate2;
    @FXML
        private TextField brand2;
    @FXML
        private TextField model2;
    @FXML
        private TextField year;
    @FXML
        private TextField rental2;
    @FXML
        private TextField price;
    @FXML
        private TextField return_id;
    @FXML
        private TextField return_name;
    @FXML
        private TextField return_surname;
    @FXML
        private TextField return_pesel;
    @FXML
        private TextField return_phone;
    @FXML
        private TextField return_days;
    @FXML
        private TextField car_id;
    @FXML
        private TextField ava;
    //Button's
    @FXML
        private Button logout;
    @FXML
        private Button btn_invoiceAndRent;
//ObservableList's
    @FXML
        ObservableList<cars_list> obs_list = FXCollections.observableArrayList();
    @FXML
        ObservableList<rental_list> obs_rent_list = FXCollections.observableArrayList();
    @FXML
        ObservableList<wished_cars> obs_wished_cars = FXCollections.observableArrayList();
    @FXML
        ObservableList<String> rent_items_list = FXCollections.observableArrayList();
    @FXML
        ObservableList<return_list> return_list = FXCollections.observableArrayList();
//TableView wished cars
    @FXML
        private TableView<wished_cars> show_wished_cars;
    @FXML
        private TableColumn<wished_cars, Integer> col_wished_id;
    @FXML
        private TableColumn<wished_cars, String> col_name;
    @FXML
        private TableColumn<wished_cars, String> col_surname;
    @FXML
        private TableColumn<wished_cars, String> col_pesel;
    @FXML
        private TableColumn<wished_cars, String> col_phone_nr;
    @FXML
        private TableColumn<wished_cars, Integer> col_peroid;
    @FXML
    private TableColumn<wished_cars, Integer> col_id_car;
//TableView list cars
    @FXML
        private TableView<cars_list> show_cars;
    @FXML
        private TableColumn<cars_list, String> col_reg;
    @FXML
        private TableColumn<cars_list, String> col_brand;
    @FXML
        private TableColumn<cars_list, String> col_model;
    @FXML
        private TableColumn<cars_list, String> col_rent;
    @FXML
        private TableColumn<cars_list, Integer> col_year;
    @FXML
        private TableColumn<cars_list, Double> col_price;
    @FXML
        private TableColumn<cars_list, Boolean> col_ava;
//TableView rent cars
    @FXML
        private TableView<rental_list> rent_show_cars;
    @FXML
        private TableColumn<rental_list, String> col_rent_reg;
    @FXML
        private TableColumn<rental_list, String> col_rent_brand;
    @FXML
        private TableColumn<rental_list, String> col_rent_model;
    @FXML
        private TableColumn<rental_list, String> col_rent_rent;
    @FXML
        private TableColumn<rental_list, Double> col_rent_price;
//Others
   // @FXML
      //  private ComboBox<String> combo_rentals;
    @FXML
        private ToggleButton toggle_available;
    @FXML
        private ToggleButton toggle_not_available;
    @FXML
        private ToggleButton toggle_clear;

    /* Global alert */
    Alert date_not_selected = new Alert(Alert.AlertType.ERROR);

    /* Get current user_name */
    String tempUsername;

    /**
     * This method is used to initialize the appropriate startup items
     *
     * @param uName User Login
     * @throws SQLException Exception handling
     */
    @FXML
    public void initialize(String uName) throws SQLException {
        tempUsername = uName;

        showCarList();
        showCarsForRent();
        showCarsForReturn();
        insertCarList();
        insertCarsForRent();
        insertCarsForReturn();


        /* Clear DatePicker, daysOfRent, finalPrice when row is selected */
        rent_show_cars.setOnMouseClicked(event -> {
            dni.setValue(null);
            days_textfield.clear();
            cena_final.clear();
        });

        /* Ograniczenie możliwości wybrania daty wcześniejszej niż aktualna i aktualnej (dni wypożyczenia = 0) */
        dni.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 1);
            }
        });

        /* Aktualizacja ceny wypożyczenia po wybraniu daty zwrotu */
        dni.valueProperty().addListener((obsVal, oldDate, newDate) -> {
            /* Check if date is selected */
            if (dni.getValue() == null) {
                date_not_selected.setContentText("Proszę wybrać datę zwrotu!");
                date_not_selected.show();
            }
            /* Check if car is selected */
            else if (cena.getText().isEmpty()) {
                Alert null_cena = new Alert(Alert.AlertType.ERROR);
                null_cena.setContentText("Proszę wybrać auto!");
                null_cena.show();
            }
            else {
                /* Count days of rent */
                long daysOfRent = ChronoUnit.DAYS.between(LocalDate.now(), newDate);
                cena.setText(price_per_day.getText());
                days_textfield.setText(String.valueOf(daysOfRent));

                /* Counting final price */
                double price = Double.parseDouble(cena.getText());
                int days = Integer.parseInt(days_textfield.getText());
                double final_price = (price * days);

                /* Replace comma with dot and set 2 decimal places in final price */
                String finalPriceString = String.format("%.2f", final_price);
                finalPriceString = finalPriceString.replace(",", ".");

                /* Set final total price in TextField */
                cena_final.setText(finalPriceString);
            }
        });
    }

/* Display available cars in TableView from DataBase */
    /**
     * This method is used to display available cars from database in TableView (CarsForRent)
     */
    @FXML
    private void showCarsForRent() throws SQLException {
        col_rent_reg.setCellValueFactory(car_registration -> car_registration.getValue().car_registration2Property());
        col_rent_brand.setCellValueFactory(car_brand -> car_brand.getValue().car_brand2Property());
        col_rent_model.setCellValueFactory(car_model -> car_model.getValue().car_model2Property());
        col_rent_rent.setCellValueFactory(rental_name -> rental_name.getValue().rental_name2Property());
        col_rent_price.setCellValueFactory(cellData -> cellData.getValue().car_price2Property().asObject());

        Connection conn2 = DBconnect.getConnection();

        /* Get current rentalID */
        PreparedStatement getRentalID = conn2.prepareStatement("SELECT rental_id FROM users WHERE "
                + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetRentalID = getRentalID.executeQuery();
        rsGetRentalID.first();
        Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

        ResultSet rs2 = conn2.createStatement().executeQuery(
                "SELECT c.car_registration, " +
                        "c.car_brand, " +
                        "c.car_model, " +
                        "r.rental_name, " +
                        "c.car_price " +
                        "FROM cars c JOIN rentals r ON " +
                        "c.rental_id = r.rental_id " +
                        "WHERE c.availability = 1 AND r.rental_id = '"+rentalID+"'"
        );
        while (rs2.next()) {
            obs_rent_list.add(new rental_list(
                    rs2.getString("car_registration"),
                    rs2.getString("car_brand"),
                    rs2.getString("car_model"),
                    rs2.getString("rental_name"),
                    rs2.getDouble("car_price")));
        }
        rent_show_cars.setItems(obs_rent_list);
    }

/* Display car list in TableView from DataBase */
    /**
     * This method is used to display database records in TableView (CarList)
     */
    public void showCarList() throws SQLException {
        col_reg.setCellValueFactory(car_registration -> car_registration.getValue().car_registrationProperty());
        col_brand.setCellValueFactory(car_brand -> car_brand.getValue().car_brandProperty());
        col_model.setCellValueFactory(car_model -> car_model.getValue().car_modelProperty());
        col_rent.setCellValueFactory(rental_name -> rental_name.getValue().rental_nameProperty());
        col_year.setCellValueFactory(cellData -> cellData.getValue().car_productionProperty().asObject());
        col_price.setCellValueFactory(cellData -> cellData.getValue().car_priceProperty().asObject());
        col_ava.setCellValueFactory(cellData -> cellData.getValue().availabilityProperty().asObject());


        Connection conn = DBconnect.getConnection();

        /* Get current rentalID */
        PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
            + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetRentalID = getRentalID.executeQuery();
        rsGetRentalID.first();
        Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

        ResultSet rs = conn.createStatement().executeQuery(
                "SELECT c.car_registration," +
                        "c.car_brand," +
                        "c.car_model," +
                        "r.rental_name," +
                        "c.car_production," +
                        "c.car_price," +
                        "c.availability " +
                        "FROM cars c, rentals r " +
                        "WHERE c.rental_id = r.rental_id AND r.rental_id = '"+rentalID+"'");

        while (rs.next()) {
            obs_list.add(new cars_list(
                    rs.getString("car_registration"),
                    rs.getString("car_brand"),
                    rs.getString("car_model"),
                    rs.getString("rental_name"),
                    rs.getInt("car_production"),
                    rs.getDouble("car_price"),
                    rs.getBoolean("availability")));
        }
        show_cars.setItems(obs_list);
    }

/* Display wished cars in TableView from DataBase (return car) */
    /**
     * This method is used to display wished cars in TableView from DataBase (ReturnCar)
     */
    private void showCarsForReturn() throws SQLException {
        col_wished_id.setCellValueFactory(cellData -> cellData.getValue().wished_idProperty().asObject());
        col_name.setCellValueFactory(name -> name.getValue().nameProperty());
        col_surname.setCellValueFactory(surname -> surname.getValue().surnameProperty());
        col_pesel.setCellValueFactory(pesel -> pesel.getValue().peselProperty());
        col_phone_nr.setCellValueFactory(phone_nr -> phone_nr.getValue().phone_nrProperty());
        col_peroid.setCellValueFactory(cellData -> cellData.getValue().peroidProperty().asObject());
        col_id_car.setCellValueFactory(cellData -> cellData.getValue().car_idProperty().asObject());

        Connection conn2 = DBconnect.getConnection();

        /* Get current rentalID */
        PreparedStatement getRentalID = conn2.prepareStatement("SELECT rental_id FROM users WHERE "
                + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetRentalID = getRentalID.executeQuery();
        rsGetRentalID.first();
        Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

        ResultSet rs2 = conn2.createStatement().executeQuery(
                "SELECT * FROM loans WHERE rental_id = '"+rentalID+"'"
        );
        while (rs2.next()) {
            obs_wished_cars.add(new wished_cars(
                    rs2.getInt("loan_id"),
                    rs2.getString("client_name"),
                    rs2.getString("client_surname"),
                    rs2.getString("client_pesel"),
                    rs2.getString("client_phone"),
                    rs2.getInt("period"),
                    rs2.getInt("car_id")));
        }
        show_wished_cars.setItems(obs_wished_cars);
    }

/* Select car and display in TextField's from TableView (car's list) */
    /**
     * This method is used to display cars from TableView (CarList)
     */
    private void insertCarList() {
            show_cars.getSelectionModel().selectedItemProperty().addListener((obs_list, oldVal, newVal) ->
            {
                if (newVal != null) {
                    plate.setText(newVal.getCar_registration());
                    brand.setText(newVal.getCar_brand());
                    model.setText(newVal.getCar_model());
                    //combo_rentals.setValue(newVal.getRental_name());
                    year.setText(String.valueOf(newVal.getCar_production()));
                    price.setText(String.valueOf(newVal.getCar_price()));
                }
            });
        }

    /**
     * This method is used to delete car from database using unique car registration
     */
/* Delete car from car list */
    @FXML
    private void deleteFromCarList() throws SQLException {
            String platedb = plate.getText();
            Connection conn = DBconnect.getConnection();

                    PreparedStatement pst = conn.prepareStatement("SELECT car_registration FROM cars WHERE "
                    + "car_registration = '" + platedb + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = pst.executeQuery();

            if (rs.first()) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM cars WHERE car_registration = '" + platedb + "'");

                Alert success_del = new Alert(Alert.AlertType.CONFIRMATION);
                success_del.setContentText("Pomyślnie usunięto auto!");
                success_del.show();

                plate.clear();
                brand.clear();
                model.clear();
                year.clear();
                price.clear();
                refreshCarList();
            }
        }
    /**
     * This method is used to refresh pane's
     */
    @FXML
    private void refreshPane() {
        try {
            obs_list.clear();
            Connection conn = DBconnect.getConnection();

            String query = ("SELECT car_registration," +
                    "car_brand," +
                    "car_model," +
                    "rental_name," +
                    "car_production," +
                    "car_price," +
                    "availability " +
                    "FROM cars JOIN rentals ON " +
                    "cars.rental_id=rentals.rental_id");
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rsSET = ps.executeQuery();

            while (rsSET.next()) {
                obs_list.add(new cars_list(
                        rsSET.getString("car_registration"),
                        rsSET.getString("car_brand"),
                        rsSET.getString("car_model"),
                        rsSET.getString("rental_name"),
                        rsSET.getInt("car_production"),
                        rsSET.getDouble("car_price"),
                        rsSET.getBoolean("availability")));
            }
            conn.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(admin_users_table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

/* Select car and display in TextField's from TableView (rent cars) */
    /**
     * This method is used to display cars from TableView (CarsForRent)
     */
    private void insertCarsForRent() {
        rent_show_cars.getSelectionModel().selectedItemProperty().addListener((obs_rent_list, oldVal, newVal) ->
        {
            if (newVal != null) {
                plate2.setText(newVal.getCar_registration2());
                brand2.setText(newVal.getCar_brand2());
                model2.setText(newVal.getCar_model2());
                rental2.setText(newVal.getRental_name2());
                price_per_day.setText(String.valueOf(newVal.getCar_price2()));
                cena.setText(String.valueOf(newVal.getCar_price2()));
            }
        });
    }

/* Select loan and display in TextField's from TableView (return car) */
    /**
     * This method is used to display loan in TextField's from TableView (ReturnCar)
     */
    private void insertCarsForReturn() {
        show_wished_cars.getSelectionModel().selectedItemProperty().addListener((obs_rent_list, oldVal, newVal) ->
        {
            if (newVal != null) {
                return_id.setText(String.valueOf(newVal.getWished_id()));
                return_name.setText(newVal.getName());
                return_surname.setText(newVal.getSurname());
                return_pesel.setText(newVal.getPesel());
                return_phone.setText(newVal.getPhone_nr());
                return_days.setText(String.valueOf(newVal.getPeroid()));
                car_id.setText(String.valueOf(newVal.getCar_id()));
            }
        });
    }

/* Add new car to carlist */
    /**
     * This method is used to add new car to Database (CarList)
     */
    @FXML
    private void insertNewCar() throws SQLException {
        //String rentalText = combo_rentals.getValue();
        String plateText = plate.getText();
        String brandText = brand.getText();
        String modelText = model.getText();
        String yearText = year.getText();
        String priceText = price.getText();
        String availableText = ava.getText();
        if (availableText.equals("Dostępny")) {
            availableText = "1";
        } else if (availableText.equals("Niedostępny")) {
            availableText = "0";
        } else {
            System.out.println("error");
        }

        Connection conn = DBconnect.getConnection();
        PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetRentalID = getRentalID.executeQuery();

        PreparedStatement getPlate = conn.prepareStatement("SELECT car_registration FROM cars WHERE "
                + "car_registration = '"+plateText+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetPlate = getPlate.executeQuery();

       if(plateText.isEmpty()){
            Alert null_av = new Alert(Alert.AlertType.ERROR);
            null_av.setContentText("Proszę podać numer rejestracyjny!");
            null_av.show();
        }else if(plateText.length()>8) {
           Alert null_av = new Alert(Alert.AlertType.ERROR);
           null_av.setContentText("Numer rejestracyjny jest za długi! \n"
                   + "Nie może przekraczać limitu 8 znaków!");
           null_av.show();
       }else if (rsGetPlate.first()) {
           Alert login_in_DB = new Alert(Alert.AlertType.ERROR);
           login_in_DB.setContentText("Podana rejestracja istnieje już w bazie!");
           login_in_DB.show();
        }else if(brandText.isEmpty()) {
           Alert null_av = new Alert(Alert.AlertType.ERROR);
           null_av.setContentText("Proszę podać markę!");
           null_av.show();
        }else if(brandText.length()>30){
           Alert null_av = new Alert(Alert.AlertType.ERROR);
           null_av.setContentText("Nazwa marki jest za długa! \n"
                   +"Nie może być dłuższa niż 30 znaków!");
           null_av.show();
        }else if(modelText.isEmpty()) {
           Alert null_av = new Alert(Alert.AlertType.ERROR);
           null_av.setContentText("Proszę podać model!");
           null_av.show();
       }else if(modelText.length()>30) {
           Alert null_av = new Alert(Alert.AlertType.ERROR);
           null_av.setContentText("Nazwa modelu jest za długa! \n"
                   + "Nie może być dłuższa niż 30 znaków!");
           null_av.show();
       }else if (yearText.isEmpty()) {
           Alert null_rok = new Alert(Alert.AlertType.ERROR);
           null_rok.setContentText("Proszę wprowadzić rok!");
           null_rok.show();
       }else if (yearText.length() > 4) {
           Alert outOfLimit_rok = new Alert(Alert.AlertType.ERROR);
           outOfLimit_rok.setContentText("Przekroczono limit 4 znaków w polu rok!");
           outOfLimit_rok.show();
       }else if (priceText.isEmpty()) {
           Alert null_rok = new Alert(Alert.AlertType.ERROR);
           null_rok.setContentText("Proszę wprowadzić cenę za dzień!");
           null_rok.show();
       }else if(availableText.isEmpty()) {
            Alert null_av = new Alert(Alert.AlertType.ERROR);
            null_av.setContentText("Proszę określić dostępność!");
            null_av.show();
        }else if (rsGetRentalID.first()) {
            Integer rental_id = (Integer) rsGetRentalID.getObject("rental_id");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO cars (rental_id, car_registration, car_brand, car_model, car_production, car_price, availability) "
                    + "VALUES ('"+rental_id+"',  '" + plateText + "', '" + brandText + "', '" + modelText + "', '" + yearText + "', '" + priceText.replace(",",".") + "', '" + availableText + "')");
        }
        refreshCarList();
        refreshCarForRent();
    }

/* Edit car (car list) */
    /**
     * This method is used to edit exist car (CarList)
     */
    @FXML
    private void editCarList() throws SQLException {

       // String rentalText = combo_rentals.getValue();
        String plateText = plate.getText();
        String brandText = brand.getText();
        String modelText = model.getText();
        String yearText = year.getText();
        String priceText = price.getText();
        String availableText = ava.getText();
        if (availableText.equals("Dostępny")) {
            availableText = "1";
        } else if (availableText.equals("Niedostępny")) {
            availableText = "0";
        } else {
            System.out.println("error");
        }

        Connection conn = DBconnect.getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE cars SET car_brand = '" + brandText + "', car_model = '" + modelText + "', car_production = '" + yearText + "', car_price = '" + priceText + "',availability = '" + availableText + "'"
                +"WHERE car_registration = '"+plateText+"'");

        Alert success_edit = new Alert(Alert.AlertType.CONFIRMATION);
        success_edit.setContentText("Dane auta zostały zaktualizowane!");
        success_edit.show();
        refreshCarList();
        refreshCarForRent();
    }

/* Rental name for ComboBox handling */
    /**
     * This method is used to get specific rental
     */
    private void getRentalsDB() {
        try {
            Connection conn = DBconnect.getConnection();
            ResultSet rentals = conn.createStatement().executeQuery("SELECT rental_name FROM rentals");

            while (rentals.next()) {
                rent_items_list.add(rentals.getString("rental_name"));
            }
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

/* Method for refreshing TableView and clearing TextFields in car list */
    /**
     * This method is used to refresh CarList Pane
     */
    @FXML
    private void refreshCarList() throws SQLException {
        obs_list.clear();
        Connection conn = DBconnect.getConnection();
        PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                + "login = '" + tempUsername + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetRentalID = getRentalID.executeQuery();
        rsGetRentalID.first();
        Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

        ResultSet rs = conn.createStatement().executeQuery(
                "SELECT c.car_registration," +
                        "c.car_brand," +
                        "c.car_model," +
                        "r.rental_name," +
                        "c.car_production," +
                        "c.car_price," +
                        "c.availability " +
                        "FROM cars c, rentals r " +
                        "WHERE c.rental_id = r.rental_id AND r.rental_id = '" + rentalID + "'");

        while (rs.next()) {
            obs_list.add(new cars_list(
                    rs.getString("car_registration"),
                    rs.getString("car_brand"),
                    rs.getString("car_model"),
                    rs.getString("rental_name"),
                    rs.getInt("car_production"),
                    rs.getDouble("car_price"),
                    rs.getBoolean("availability")));
        }
        show_cars.setItems(obs_list);
    }

/* Method for refreshing TableView and clearing TextFields in car rent */
    /**
     * This method is used to refresh CarForRent Pane
     */
    @FXML
    private void refreshCarForRent() throws SQLException {
        obs_rent_list.clear();
        Connection conn = DBconnect.getConnection();

        /* Get current rentalID */
        PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetRentalID = getRentalID.executeQuery();
        rsGetRentalID.first();
        Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

        ResultSet rs = conn.createStatement().executeQuery(
            "SELECT c.car_registration, " +
                "c.car_brand, " +
                "c.car_model, " +
                "r.rental_name, " +
                "c.car_price " +
                "FROM cars c JOIN rentals r ON " +
                "c.rental_id = r.rental_id " +
                "WHERE c.availability = 1 AND r.rental_id = '"+rentalID+"'");

        while (rs.next()) {
            obs_rent_list.add(new rental_list(
                rs.getString("car_registration"),
                rs.getString("car_brand"),
                rs.getString("car_model"),
                rs.getString("rental_name"),
                rs.getDouble("car_price")));
        }
        rent_show_cars.setItems(obs_rent_list);
    }

/* Method for refreshing TableView in return car tab */
    /**
     * This method is used to refresh CarForReturn Pane
     */
    @FXML
    private void refreshCarsForReturn() throws SQLException {
        obs_wished_cars.clear();
        Connection conn = DBconnect.getConnection();

        /* Get current rentalID */
        PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rsGetRentalID = getRentalID.executeQuery();
        rsGetRentalID.first();
        Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

        ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM loans WHERE rental_id = '"+rentalID+"'");
        while (rs2.next()) {
            obs_wished_cars.add(new wished_cars(
                    rs2.getInt("loan_id"),
                    rs2.getString("client_name"),
                    rs2.getString("client_surname"),
                    rs2.getString("client_pesel"),
                    rs2.getString("client_phone"),
                    rs2.getInt("period"),
                    rs2.getInt("car_id")));
        }
        show_wished_cars.setItems(obs_wished_cars);
    }
    /**
     * This method is used to refresh rentals
     */
    @FXML
    private void refreshRentals() {
       /* combo_rentals.getSelectionModel().clearSelection();
        combo_rentals.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(combo_rentals.getPromptText());
                }
                else {
                    setText(item);
                }
            }
        });*/
        rent_items_list.clear();
        plate.clear();
        brand.clear();
        model.clear();

        getRentalsDB();
    }

/* Generate invoice */
    /**
     * This method is used to generate an invoice based on customer data
     *
     * @throws SQLException Exception handling
     * @throws MalformedURLException Exception handling
     * @throws FileNotFoundException Exception handling
     */
    @FXML
    private void generateInvoiceAndRentCar() throws SQLException, MalformedURLException, FileNotFoundException {
        /* Checking all user inputs */
        if (plate2.getText().isEmpty() || rental2.getText().isEmpty() || price_per_day.getText().isEmpty()) {
            Alert not_selected_car_to_rent = new Alert(Alert.AlertType.ERROR);
            not_selected_car_to_rent.setContentText("Proszę wybrać auto do wypożyczenia!");
            not_selected_car_to_rent.show();
        }
        else if (name.getText().isEmpty()) {
            Alert null_imie = new Alert(Alert.AlertType.ERROR);
            null_imie.setContentText("Proszę wprowadzić imię klienta!");
            null_imie.show();
        }
        else if (surname.getText().isEmpty()) {
            Alert null_nazwisko = new Alert(Alert.AlertType.ERROR);
            null_nazwisko.setContentText("Proszę wprowadzić nazwisko klienta!");
            null_nazwisko.show();
        }
        else if (pesel.getText().isEmpty()) {
            Alert null_pesel = new Alert(Alert.AlertType.ERROR);
            null_pesel.setContentText("Proszę wprowadzić PESEL klienta!");
            null_pesel.show();
        }
        /* PESEL number validation */
        else if (!peselVal.isValid(pesel.getText())) {
            Alert not_valid_pesel = new Alert(Alert.AlertType.ERROR);
            not_valid_pesel.setContentText("Nieprawidłowy format nr PESEL!\n"
                    + "Nr PESEL powinien składać się tylko i wyłącznie z 11 cyfr!");
            not_valid_pesel.show();
        }
        else if (days_textfield.getText().isEmpty() || dni.getValue() == null) {
            Alert not_selected_return_date = new Alert(Alert.AlertType.ERROR);
            not_selected_return_date.setContentText("Proszę wybrać datę zwrotu!");
            not_selected_return_date.show();
        }
        else if (phone_nr.getText().isEmpty()) {
            Alert null_phone = new Alert(Alert.AlertType.ERROR);
            null_phone.setContentText("Proszę wprowadzić telefon klienta!");
            null_phone.show();
        }
        /* Phone number validation */
        else if (!phoneNumberVal.isValid(phone_nr.getText())) {
            Alert not_valid_phone = new Alert(Alert.AlertType.ERROR);
            not_valid_phone.setContentText("Nieprawidłowy format nr telefonu!\n"
                    + "Nr telefonu powinien składać się tylko i wyłącznie z 9 cyfr!");
            not_valid_phone.show();
        }
        else {
            /* Replace "-" with "/" in return date */
            String returnDate = String.valueOf(dni.getValue());
            returnDate = returnDate.replace("-", "/");

            /* Connect to database */
            Connection conn = DBconnect.getConnection();

            /* Getting rental_city from rentals */
            PreparedStatement getRentalCity = conn.prepareStatement("SELECT rental_city FROM rentals WHERE "
                + "rental_name = '"+rental2.getText()+"'",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsGetRentalCity = getRentalCity.executeQuery();
            rsGetRentalCity.first();
            String rentalCity = rsGetRentalCity.getString("rental_city");

            /* Getting rental_street from rentals */
            PreparedStatement getRentalStreet = conn.prepareStatement("SELECT rental_street FROM rentals WHERE "
                + "rental_name = '"+rental2.getText()+"'",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsGetRentalStreet = getRentalStreet.executeQuery();
            rsGetRentalStreet.first();
            String rentalStreet = rsGetRentalStreet.getString("rental_street");

            /* Generate PDF invoice */
            PDF.generate(plate2.getText(), rental2.getText(), price_per_day.getText(), name.getText(), surname.getText(),
                pesel.getText(), days_textfield.getText(), phone_nr.getText(), returnDate, rentalCity, rentalStreet);

            /* Get current rentalID */
            PreparedStatement getRentalID = conn.prepareStatement("SELECT rental_id FROM users WHERE "
                + "login = '"+tempUsername+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsGetRentalID = getRentalID.executeQuery();
            rsGetRentalID.first();
            Integer rentalID = (Integer) rsGetRentalID.getObject("rental_id");

            /* Get carID */
            PreparedStatement getCarID = conn.prepareStatement("SELECT car_id FROM cars WHERE "
                + "car_registration = '"+plate2.getText()+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsGetCarID = getCarID.executeQuery();
            rsGetCarID.first();
            Integer carID = (Integer) rsGetCarID.getObject("car_id");

            /* Rent a car for customer */
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO loans (rental_id, car_id, client_name, client_surname, client_pesel, "
                + "client_phone, period) VALUES ('"+rentalID+"', '"+carID+"', '"+name.getText()+"', '"+surname.getText()+"', "
                    + "'"+pesel.getText()+"', '"+phone_nr.getText()+"', '"+days_textfield.getText()+"')");

            Statement stmt2 = conn.createStatement();
            stmt2.executeUpdate("UPDATE cars SET availability = 0 WHERE car_registration ='"+plate2.getText()+"'");

            Alert successRent = new Alert(Alert.AlertType.CONFIRMATION);
            successRent.setContentText("Pomyślnie wypożyczono samochód!");
            successRent.show();

            plate2.clear();
            brand2.clear();
            model2.clear();
            rental2.clear();
            price_per_day.clear();
            name.clear();
            surname.clear();
            pesel.clear();
            days_textfield.clear();
            phone_nr.clear();
            dni.setValue(null);
            cena.clear();
            cena_final.clear();
            date_not_selected.hide();
            rent_show_cars.getSelectionModel().clearSelection();

            refreshCarList();
            refreshCarForRent();
            refreshCarsForReturn();
        }
    }

/* Available ToggleButton handling */
    /**
     * This method is used to select the availability of the car
     */
    @FXML
    public void availableButton() {
        if(toggle_available.isArmed()){
            ava.setText("Dostępny");

        }else if(toggle_not_available.isArmed()) {
            ava.setText("Niedostępny");
        }else if(toggle_clear.isArmed()){
            plate.clear();
            brand.clear();
            model.clear();
            year.clear();
            price.clear();
            ava.clear();
        }else{
            System.out.println("error");
        }
    }

/* Return car row DELETE from DB */
    /**
     * This method is used to remove the returned car from the database
     */
    @FXML
    public void returnCar() throws SQLException {
        String id = return_id.getText();
        Connection conn = DBconnect.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT loan_id FROM loans WHERE "
                + "loan_id = '" + id + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = pst.executeQuery();
        returnCarAvailability();
        if (rs.first()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM loans WHERE loan_id = '" + id + "'");
        }
        return_id.clear();
        car_id.clear();
        return_name.clear();
        return_surname.clear();
        return_pesel.clear();
        return_phone.clear();
        return_days.clear();
        refreshCarsForReturn();
        refreshCarList();
        refreshCarForRent();
    }
/* Return car available car UPDATE */
    /**
     * This method is used to change the availability of the car after the customer returns it
     */
    @FXML
    public void returnCarAvailability() throws SQLException {
        String id_car = car_id.getText();
        Connection conn = DBconnect.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT car_id FROM loans WHERE "
                + "car_id = '" + id_car + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = pst.executeQuery();

        if (rs.first()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE cars SET availability = 1 WHERE car_id ='"+id_car+"'");
        }
        refreshCarsForReturn();
        refreshCarList();
        refreshCarForRent();
    }

/* Logout from employee panel */
    /**
     * This method is used to log the user out of the system
     */
    @FXML
    public void logout() {
        if (logout.isArmed()) {
            final double[] xOffset = {0};
            final double[] yOffset = {0};
            try {
                Stage stage = (Stage) logout.getScene().getWindow();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
