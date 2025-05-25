package com.example.project4.repositories;

import com.example.project4.models.ProductItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryRepository {
    private static final String DB_URL = "jdbc:sqlite:orders.db";

    public OrderHistoryRepository() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS orders (
                id TEXT PRIMARY KEY,
                name TEXT,
                price REAL,
                quantity INTEGER,
                category TEXT,
                date_time TEXT
            )
            """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(ProductItem item) {
        String sql = "INSERT INTO orders (id, name, price, quantity, category, date_time) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getProductId());
            pstmt.setString(2, item.getProductName());
            pstmt.setDouble(3, item.getPrice());
            pstmt.setInt(4, item.getQuantity());
            pstmt.setString(5, item.getCategory());
            pstmt.setString(6, item.getDateTime());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProductItem> getAllOrders() {
        List<ProductItem> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ProductItem item = new ProductItem(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category"),
                        rs.getString("date_time")
                );
                orders.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<ProductItem> searchOrders(String keyword) {
        List<ProductItem> results = new ArrayList<>();
        String sql = """
            SELECT * FROM orders WHERE
            LOWER(name) LIKE ? OR LOWER(id) LIKE ? OR LOWER(category) LIKE ?
            """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String kw = "%" + keyword.toLowerCase() + "%";
            pstmt.setString(1, kw);
            pstmt.setString(2, kw);
            pstmt.setString(3, kw);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductItem item = new ProductItem(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category"),
                        rs.getString("date_time")
                );
                results.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public void removeOrder(String id) {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
