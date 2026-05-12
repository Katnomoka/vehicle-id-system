package com.vehicle.identification.vehicleidsystem.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;
    private static final String PROPERTIES_FILE = "db.properties";

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            Properties properties = new Properties();
            InputStream inputStream = DBConnection.class.getClassLoader()
                    .getResourceAsStream(PROPERTIES_FILE);

            if (inputStream != null) {
                properties.load(inputStream);

                String driver = properties.getProperty("db.driver");
                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");

                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("✅ Connected to PostgreSQL database successfully!");
            } else {
                System.err.println("❌ Could not find db.properties file");
            }
        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔌 Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}