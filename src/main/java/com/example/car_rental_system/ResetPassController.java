package com.example.car_rental_system;

import com.example.car_rental_system.DBconnect.DBconnect;
import com.example.car_rental_system.Validators.peselVal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is for Reset Password Controller
 *
 * @author Patryk Midura
 */
public class ResetPassController implements Initializable {
    @FXML
    private TextField txt_reset_login;
    @FXML
    private PasswordField txt_new_pass;
    @FXML
    private PasswordField txt_repeat_pass;
    @FXML
    private TextField txt_pesel;
    @FXML
    private Button back_to_login;
    @FXML
    private Button btn_reset_pass;

    /**
     * This method is for redirect to Login Panel
     */
    @FXML
    private void goToLoginPanel() {
        /* Drag and drop */
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        try {
            Stage stage = (Stage) back_to_login.getScene().getWindow();
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

    /**
     * This method is for changing user Password
     */
    @FXML
    private void changePassword() {
        String login = txt_reset_login.getText();
        String new_pass = txt_new_pass.getText();
        String repeat_pass = txt_repeat_pass.getText();
        String pesel = txt_pesel.getText();

        /* Checking all user inputs */
        if (login.isEmpty()) {
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
        else if (new_pass.isEmpty()) {
            Alert null_new_pass = new Alert(Alert.AlertType.ERROR);
            null_new_pass.setContentText("Proszę wprowadzić nowe hasło!");
            null_new_pass.show();
        }
        else if (repeat_pass.isEmpty()) {
            Alert null_repeat_pass = new Alert(Alert.AlertType.ERROR);
            null_repeat_pass.setContentText("Proszę wprowadzić nowe hasło ponownie!");
            null_repeat_pass.show();
        }
        /* Passwords matches validation */
        else if (!new_pass.equals(repeat_pass)) {
            Alert pass_not_matched = new Alert(Alert.AlertType.ERROR);
            pass_not_matched.setContentText("Podane hasła nie są takie same!");
            pass_not_matched.show();
        }
        else if (pesel.isEmpty()) {
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
        else {
            try {
                /* Hashing user password from input */
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(new_pass.getBytes(), 0, new_pass.length());
                String hashPass = new BigInteger(1, md.digest()).toString(16);

                Connection conn = DBconnect.getConnection();

                /* Getting user login and PESEL from database */
                PreparedStatement getLoginPESEL = conn.prepareStatement("SELECT login, pesel FROM users WHERE "
                    + "login = '"+login+"' AND pesel = '"+pesel+"'",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rsGetLoginPESEL = getLoginPESEL.executeQuery();

                /* Checking if the user exists in the database */
                if (!rsGetLoginPESEL.first()) {
                    Alert wrong_data = new Alert(Alert.AlertType.ERROR);
                    wrong_data.setContentText("Podany login lub PESEL jest nieprawidłowy!");
                    wrong_data.show();
                }
                else {
                    /* Update user password */
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("UPDATE users SET password = '"+hashPass+"' WHERE "
                        + "login = '"+login+"' AND pesel = '"+pesel+"'");

                    Alert success_update = new Alert(Alert.AlertType.CONFIRMATION);
                    success_update.setContentText("Hasło zostało pomyślnie zmienione!");
                    success_update.show();

                    txt_reset_login.clear();
                    txt_new_pass.clear();
                    txt_repeat_pass.clear();
                    txt_pesel.clear();
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
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
