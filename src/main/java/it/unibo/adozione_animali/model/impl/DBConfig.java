package it.unibo.adozione_animali.model.impl;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DBConfig {
    public static final String URL = "jdbc:mysql://localhost:3306/adozione_animali?ssl=false";
    public static final String USER = "root";
    public static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
