package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Database instance;
    private static Connection connection;

    private Database(String dbUrl, String dbUser, String dbPass) {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance(String dbUrl, String dbUser, String dbPass) {
        if (instance == null) {
            instance = new Database(dbUrl, dbUser, dbPass);
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
       try {
           connection.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }
}

