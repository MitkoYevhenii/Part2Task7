package org.example.service;


import org.example.connection.Database;
import org.example.prefs.Prefs;
import org.example.prefs.SqlFileReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitService {
    public static void main(String[] args) {
        Prefs prefs = new Prefs();

        String dbUrl =  prefs.getString(Prefs.DB_JDBC_CONNECTION_URL);
        String dbUser = prefs.getString(Prefs.DB_JDBC_USERNAME);
        String dbPass = prefs.getString(Prefs.DB_JDBC_PASSWORD);
        String sqlPath = prefs.getString(Prefs.INIT_DB_FILE_PATH);

        try(Connection connection = Database.getInstance(dbUrl, dbUser, dbPass).getConnection()) {
            String sql = SqlFileReader.readSqlFile(sqlPath + "\\init_db.sql");
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


