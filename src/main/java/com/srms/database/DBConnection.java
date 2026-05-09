package com.srms.database;

import com.srms.exceptions.DatabaseConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String PROPERTIES_FILE = "/db.properties";

    private DBConnection() {
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        try (InputStream inputStream = DBConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new DatabaseConnectionException("db.properties not found on classpath");
            }
            properties.load(inputStream);
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            return DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException exception) {
            throw new DatabaseConnectionException("Unable to connect to database", exception);
        }
    }
}
