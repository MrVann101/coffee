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
            // Coffee
            addProduct(new Product("1", "Iced Americano", 120.0, "Coffee", "/com/example/project4/Images/4f4e38de-050f-4e1c-bd7e-89a5927c9090.jpeg", 0));
            addProduct(new Product("2", "Caramel Macchiato", 145.0, "Coffee", "/com/example/project4/Images/Caramel Macchiato.jpeg", 0));
            addProduct(new Product("3", "Vanilla Latte", 135.0, "Coffee", "/com/example/project4/Images/Vanilla Latte.jpeg", 0));
            addProduct(new Product("4", "Mocha Frappe", 150.0, "Coffee", "/com/example/project4/Images/Mocha Frappe.jpeg", 0));
            addProduct(new Product("5", "Espresso Shot", 95.0, "Coffee", "/com/example/project4/Images/Espresso Shot.jpg", 0));
            addProduct(new Product("16", "Spanish Latte", 130.0, "Coffee", "/com/example/project4/Images/Spanish Latte.jpeg", 0));
            addProduct(new Product("17", "White Mocha", 140.0, "Coffee", "/com/example/project4/Images/White Mocha (2).jpeg", 0));
            addProduct(new Product("18", "Salted Caramel", 140.0, "Coffee", "/com/example/project4/Images/Starbucks.jpg", 0));

            // Non-Coffee
            addProduct(new Product("6", "Classic Milk Tea", 110.0, "Non-Coffee", "/com/example/project4/Images/Classic milk tea.jpeg", 0));
            addProduct(new Product("7", "Matcha Green Tea Latte", 130.0, "Non-Coffee", "/com/example/project4/Images/Matcha Green Tea Latte.jpg", 0));
            addProduct(new Product("8", "Taro Milk Tea", 115.0, "Non-Coffee", "/com/example/project4/Images/Taro Milk Tea.jpeg", 0));
            addProduct(new Product("9", "Strawberry Smoothie", 125.0, "Non-Coffee", "/com/example/project4/Images/Strawberry Smoothie.jpg", 0));
            addProduct(new Product("10", "Mango Yakult", 120.0, "Non-Coffee", "/com/example/project4/Images/Mango Yakult.jpeg", 0));
            addProduct(new Product("19", "Okinawa Milk Tea", 115.0, "Non-Coffee", "/com/example/project4/Images/Okinawa Milk Tea.jpeg", 0));
            addProduct(new Product("20", "Wintermelon Tea", 105.0, "Non-Coffee", "/com/example/project4/Images/Wintermelon Tea.jpeg", 0));
            addProduct(new Product("21", "Choco Mousse Frappe", 135.0, "Non-Coffee", "/com/example/project4/Images/Choco Mousse frappe.jpeg", 0));

            // Pastry
            addProduct(new Product("11", "Butter Croissant", 65.0, "Pastry", "/com/example/project4/Images/Butter Croissant (2).jpeg", 0));
            addProduct(new Product("12", "Chocolate Chip Muffin", 75.0, "Pastry", "/com/example/project4/Images/Chocolate Chip Muffin (2).jpeg", 0));
            addProduct(new Product("13", "Berry Moofeen", 95.0, "Pastry", "/com/example/project4/Images/Blueberry Muffins.jpg", 0));
            addProduct(new Product("14", "Cinnamon Roll", 70.0, "Pastry", "/com/example/project4/Images/Cinnamon Roll pastry.jpeg", 0));
            addProduct(new Product("15", "Ham and Cheese Bun", 85.0, "Pastry", "/com/example/project4/Images/Ham and Cheese Bun.jpeg", 0));
            addProduct(new Product("22", "Cream Cheese Danish", 80.0, "Pastry", "/com/example/project4/Images/Cream Cheese Danish.jpeg", 0));
            addProduct(new Product("23", "Red Velvet Cupcake", 75.0, "Pastry", "/com/example/project4/Images/Red Velvet Cupcake.jpeg", 0));
            addProduct(new Product("24", "Peach Tart", 90.0, "Pastry", "/com/example/project4/Images/Peach Tart.jpeg", 0));
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

    // Update an existing product (optional if using addProduct as upsert)
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, category = ?, image_path = ?, quantity = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setString(3, product.getCategory());
            pstmt.setString(4, product.getImagePath());
            pstmt.setInt(5, product.getQuantity());
            pstmt.setString(6, product.getId());

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

    // Get products by category
    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

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
