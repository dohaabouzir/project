package com.clinique.soap.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/clinique_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "18RA@1YA2@1@n";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
