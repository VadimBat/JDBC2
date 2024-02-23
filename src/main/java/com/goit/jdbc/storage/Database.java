package com.goit.jdbc.storage;

import com.goit.jdbc.property.PropertyReader;

import java.sql.*;

public class Database {
    private static final Database INSTANCE = new Database();
    private static Connection connection;

    private Database() {
        try {
            String sqlConnectionUrl = PropertyReader.getPostgresConnectionUrl();
            this.connection = DriverManager.getConnection(sqlConnectionUrl,
                    PropertyReader.getPostgresUser(),
                    PropertyReader.getPostgresPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public int executeUpdate(String sql) {
        try (Statement st = connection.createStatement()) {
            return st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
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
