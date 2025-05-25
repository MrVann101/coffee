package com.example.project4.repositories;

import com.example.project4.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final String DB_URL = "jdbc:sqlite:products.db";

    public ProductRepository() {
        createTableIfNotExists(); 
        seedSampleDataIfEmpty();
    }

    // Create products table if it doesn't exist
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "id TEXT PRIMARY KEY, " +
                "name TEXT, " +
                "price REAL, " +
                "category TEXT, " +
                "image_path TEXT, " +
                "quantity INTEGER)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Seed default products if table is empty
    private void seedSampleDataIfEmpty() {
        if (getAllProducts().isEmpty()) {
            addProduct(new Product("1", "Coconut", 123.0, "Coffee", "/com/example/project4/Images/homepage.jpg", 0));
            addProduct(new Product("2", "Red Horse", 456.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            addProduct(new Product("3", "Coke", 78.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            addProduct(new Product("4", "Cake", 78.0, "Pastry", "/com/example/project4/Images/default.jpg", 0));
            addProduct(new Product("5", "Muffin", 78.0, "Pastry", "/com/example/project4/Images/default.jpg", 0));
            addProduct(new Product("6", "Hotdog", 78.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            addProduct(new Product("7", "Matcha", 85.0, "Coffee", "/com/example/project4/Images/default.jpg", 0));
        }
    }

    // Add or update a product
    public void addProduct(Product product) {
        String sql = "INSERT OR REPLACE INTO products (id, name, price, category, image_path, quantity) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setString(4, product.getCategory());
            pstmt.setString(5, product.getImagePath());
            pstmt.setInt(6, product.getQuantity());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all products from the database
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getString("image_path"),
                        rs.getInt("quantity")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    // Remove a product by ID
    public void removeProduct(Product product) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
