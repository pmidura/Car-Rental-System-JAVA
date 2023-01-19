package com.example.car_rental_system;

import com.example.car_rental_system.DBconnect.DBconnect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.ibatis.jdbc.ScriptRunner;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * This class is needed to start the application and redirect to the Login Panel
 *
 * @author Patryk Midura
 */
public class MainApplication extends Application {
    /* Drag and drop */
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * This method is for starting the application
     *
     * @param stage Stage
     * @throws IOException Exception handling
     * @throws SQLException Exception handling
     * @throws InterruptedException Exception handling
     */
    @Override
    public void start(Stage stage) throws IOException, SQLException, InterruptedException {
        /* Start XAMPP MySQL */
        String path = System.getenv("SystemDrive"); //This print drive letter
        Runtime.getRuntime().exec(path + "/xampp/xampp_start.exe"); //Start XAMPP
        Runtime.getRuntime().exec(path + "/xampp/mysql/bin/mysqld.exe"); //Start MySQL

        /* Wait for XAMPP and MySQL start */
        TimeUnit.SECONDS.sleep(1);
        
        /* Create DB if not exists */
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
        String sql = "CREATE DATABASE IF NOT EXISTS car_rental";
        int i = conn.createStatement().executeUpdate(sql);

        /* if database is created, import sql file */
        if (i > 0) {
            Alert chooseDB = new Alert(Alert.AlertType.CONFIRMATION);
            chooseDB.setTitle("Wybór bazy danych");
            chooseDB.setContentText("Czy chcesz zaimportować bazę z przykładowymi danymi?");
            ButtonType okButton = new ButtonType("Tak", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("Nie", ButtonBar.ButtonData.NO);
            chooseDB.getButtonTypes().setAll(okButton, noButton);
            chooseDB.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    try {
                        Connection connDemoDB = DBconnect.getConnection();
                        ScriptRunner scriptRunner = new ScriptRunner(connDemoDB);
                        Reader reader = new BufferedReader(new FileReader("SQL_database/car_rental_demo.sql"));
                        scriptRunner.runScript(reader);
                        connDemoDB.close();
                    } catch (SQLException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if (type == noButton) {
                    try {
                        Connection connEmptyDB = DBconnect.getConnection();
                        ScriptRunner scriptRunner = new ScriptRunner(connEmptyDB);
                        Reader reader = new BufferedReader(new FileReader("SQL_database/car_rental_empty.sql"));
                        scriptRunner.runScript(reader);
                        connEmptyDB.close();
                    } catch (SQLException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        Connection connEmptyDB = DBconnect.getConnection();
                        ScriptRunner scriptRunner = new ScriptRunner(connEmptyDB);
                        Reader reader = new BufferedReader(new FileReader("SQL_database/car_rental_empty.sql"));
                        scriptRunner.runScript(reader);
                        connEmptyDB.close();
                    } catch (SQLException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        conn.close();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginPanel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    /**
     * This method is for launching the application
     *
     * @param args Method arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
