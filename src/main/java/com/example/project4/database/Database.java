package com.example.project4.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:database/orders.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialize() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS orders (
                    id TEXT,
                    name TEXT,
                    price REAL,
                    category TEXT,
                    imagePath TEXT,
                    quantity INTEGER,
                    order_time TEXT DEFAULT (datetime('now'))
                );
            """;
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
