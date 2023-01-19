package com.example.car_rental_system.DBconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is for connection to database
 *
 * @author Patryk Midura
 */
public class DBconnect {
    /**
     * This method is for getting connection
     *
     * @return Connection
     * @throws SQLException Exception handling
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental", "root", "");
        return conn;
    }
}
