package org.example.service;

import org.example.connection.Database;
import org.example.prefs.Prefs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseDropService {
    public static void main(String[] args) {
        Prefs prefs = new Prefs();

        String dbUrl =  prefs.getString(Prefs.DB_JDBC_CONNECTION_URL);
        String dbUser = prefs.getString(Prefs.DB_JDBC_USERNAME);
        String dbPass = prefs.getString(Prefs.DB_JDBC_PASSWORD);

        try(Connection connection = Database.getInstance(dbUrl, dbUser, dbPass).getConnection()) {
            String sql = "DROP TABLE IF EXISTS CLIENT, PROJECT, PROJECT_WORKER, WORKER";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

