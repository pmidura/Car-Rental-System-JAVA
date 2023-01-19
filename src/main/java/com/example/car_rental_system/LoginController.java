package com.example.car_rental_system;

import com.example.car_rental_system.DBconnect.DBconnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Login Controller class for redirect the user to the appropriate panel
 *
 * @author Patryk Midura
 */
public class LoginController implements Initializable {
    @FXML
    private TextField txt_login;
    @FXML
    private PasswordField txt_pass;
    @FXML
    private Button login_btn;
    @FXML
    private Button forget_pass_btn;

    /**
     * This method is for redirect to Reset Password Panel
     */
    @FXML
    private void goToResetPassword() {
        /* Drag and drop */
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        try {
            Stage stage = (Stage) forget_pass_btn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ResetPassword.fxml"))));
            scene.setOnMousePressed(mouseEvent -> {
                xOffset[0] = mouseEvent.getSceneX();
                yOffset[0] = mouseEvent.getSceneY();
            });
            scene.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getScreenX() - xOffset[0]);
                stage.setY(mouseEvent.getScreenY() - yOffset[0]);
            });
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is for redirect to the appropriate panel
     */
    @FXML
    private void Login() {
        try {
            Connection conn = DBconnect.getConnection();

            String username = txt_login.getText();
            String password = txt_pass.getText();

            /* Hashing user password from input */
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(), 0, password.length());
            String hashPassFromInput = new BigInteger(1, md.digest()).toString(16);

            /* Selecting a user role (manager, employee) from database */
            PreparedStatement ps = conn.prepareStatement("SELECT role FROM users, roles "
                + "WHERE users.role_id = roles.role_id AND login = '"+username+"' AND password = '"+hashPassFromInput+"'",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();

            /* Selecting an encrypted user password (manager, employee) from database */
            PreparedStatement selectHashPass = conn.prepareStatement("SELECT password FROM users "
                + "WHERE login = '"+username+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsHashPassFromDB = selectHashPass.executeQuery();

            /* Selecting an admin role from database */
            PreparedStatement psAdmin = conn.prepareStatement("SELECT role FROM admins, roles "
                + "WHERE admins.role_id = roles.role_id AND login = '"+username+"' AND password = '"+hashPassFromInput+"'",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsAdmin = psAdmin.executeQuery();

            /* Selecting an encrypted admin password from database */
            PreparedStatement selectHashPassAdmin = conn.prepareStatement("SELECT password FROM admins "
                    + "WHERE login = '"+username+"'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsHashPassAdminFromDB = selectHashPassAdmin.executeQuery();

            /* Login TextField validation */
            if (txt_login.getText().isEmpty() && !rs.first() && !rsAdmin.first()) {
                Alert access_denied = new Alert(Alert.AlertType.ERROR);
                access_denied.setContentText("Proszę wprowadzić login!");
                access_denied.show();
            }
            /* PasswordField validation */
            else if (txt_pass.getText().isEmpty() && !rs.first() && !rsAdmin.first()) {
                Alert access_denied = new Alert(Alert.AlertType.ERROR);
                access_denied.setContentText("Proszę wprowadzić hasło!");
                access_denied.show();
            }
            /* Admin Panel Login */
            else if (rsAdmin.first() && rsAdmin.getString("role").equals("admin")
                    && rsHashPassAdminFromDB.first()
                    && rsHashPassAdminFromDB.getString("password").equals(hashPassFromInput)) {
                try {
                    /* Drag and drop */
                    final double[] xOffset = {0};
                    final double[] yOffset = {0};

                    Stage stage = (Stage) login_btn.getScene().getWindow();
                    Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPanel.fxml"))));
                    scene.setOnMousePressed(mouseEvent -> {
                        xOffset[0] = mouseEvent.getSceneX();
                        yOffset[0] = mouseEvent.getSceneY();
                    });
                    scene.setOnMouseDragged(mouseEvent -> {
                        stage.setX(mouseEvent.getScreenX() - xOffset[0]);
                        stage.setY(mouseEvent.getScreenY() - yOffset[0]);
                    });
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /* Manager Panel Login */
            else if (rs.first() && rs.getString("role").equals("manager")
                    && rsHashPassFromDB.first()
                    && rsHashPassFromDB.getString("password").equals(hashPassFromInput)) {
                try {
                    /* Drag and drop */
                    final double[] xOffset = {0};
                    final double[] yOffset = {0};
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerPanel.fxml"));
                    Parent root = loader.load();

                    /* Pass user_name to ManagerController */
                    ManagerController managerController = loader.getController();
                    managerController.initialize(username);

                    Stage stage = (Stage) login_btn.getScene().getWindow();

                    Scene scene = new Scene(root);
                    scene.setOnMousePressed(mouseEvent -> {
                        xOffset[0] = mouseEvent.getSceneX();
                        yOffset[0] = mouseEvent.getSceneY();
                    });
                    scene.setOnMouseDragged(mouseEvent -> {
                        stage.setX(mouseEvent.getScreenX() - xOffset[0]);
                        stage.setY(mouseEvent.getScreenY() - yOffset[0]);
                    });
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /* Employee Panel Login */
            else if (rs.first() && rs.getString("role").equals("employee")
                    && rsHashPassFromDB.first()
                    && rsHashPassFromDB.getString("password").equals(hashPassFromInput)) {
                try {
                    /* Drag and drop */
                    final double[] xOffset = {0};
                    final double[] yOffset = {0};
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePanel.fxml"));
                    Parent root = loader.load();

                    /* Pass user_name to EmployeeController */
                    EmployeeController employeeController = loader.getController();
                    employeeController.initialize(username);

                    Stage stage = (Stage) login_btn.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.setOnMousePressed(mouseEvent -> {
                        xOffset[0] = mouseEvent.getSceneX();
                        yOffset[0] = mouseEvent.getSceneY();
                    });
                    scene.setOnMouseDragged(mouseEvent -> {
                        stage.setX(mouseEvent.getScreenX() - xOffset[0]);
                        stage.setY(mouseEvent.getScreenY() - yOffset[0]);
                    });
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Alert access_denied = new Alert(Alert.AlertType.ERROR);
                access_denied.setContentText("Wprowadzono niepoprawne dane!");
                access_denied.show();
            }
            conn.close();
        }
        catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used for an action on the Enter Key
     */
    @FXML
    private void enterKeyPressed() {
        txt_login.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                Login();
            }
        });

        txt_pass.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                Login();
            }
        });
    }

    /**
     * This method is used to close the application
     */
    @FXML
    private void Close() {
        System.exit(0);
    }

    /**
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(event -> Login());
    }
}
