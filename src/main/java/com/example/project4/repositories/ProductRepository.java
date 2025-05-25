package com.example.project4.repositories;

import com.example.project4.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        if (products.isEmpty()) {
            // Coffee
            products.add(new Product("1", "Iced Americano", 120.0, "Coffee", "/com/example/project4/Images/4f4e38de-050f-4e1c-bd7e-89a5927c9090.jpeg", 0));
            products.add(new Product("2", "Caramel Macchiato", 145.0, "Coffee", "/com/example/project4/Images/Caramel Macchiato.jpeg", 0));
            products.add(new Product("3", "Vanilla Latte", 135.0, "Coffee", "/com/example/project4/Images/Vanilla Latte.jpeg", 0));
            products.add(new Product("4", "Mocha Frappe", 150.0, "Coffee", "/com/example/project4/Images/Mocha Frappe.jpeg", 0));
            products.add(new Product("5", "Espresso Shot", 95.0, "Coffee", "/com/example/project4/Images/Espresso Shot.jpg", 0));
            products.add(new Product("16", "Spanish Latte", 130.0, "Coffee", "/com/example/project4/Images/Spanish Latte.jpeg", 0));
            products.add(new Product("17", "White Mocha", 140.0, "Coffee", "/com/example/project4/Images/White Mocha (2).jpeg", 0));
            products.add(new Product("18", "Salted Caramel", 140.0, "Coffee", "/com/example/project4/Images/Starbucks.jpg", 0));

// Non-Coffee
            products.add(new Product("6", "Classic Milk Tea", 110.0, "Non-Coffee", "/com/example/project4/Images/Classic milk tea.jpeg", 0));
            products.add(new Product("7", "Matcha Green Tea Latte", 130.0, "Non-Coffee", "/com/example/project4/Images/Matcha Green Tea Latte.jpg", 0));
            products.add(new Product("8", "Taro Milk Tea", 115.0, "Non-Coffee", "/com/example/project4/Images/Taro Milk Tea.jpeg", 0));
            products.add(new Product("9", "Strawberry Smoothie", 125.0, "Non-Coffee", "/com/example/project4/Images/Strawberry Smoothie.jpg", 0));
            products.add(new Product("10", "Mango Yakult", 120.0, "Non-Coffee", "/com/example/project4/Images/Mango Yakult.jpeg", 0));
            products.add(new Product("19", "Okinawa Milk Tea", 115.0, "Non-Coffee", "/com/example/project4/Images/Okinawa Milk Tea.jpeg", 0));
            products.add(new Product("20", "Wintermelon Tea", 105.0, "Non-Coffee", "/com/example/project4/Images/Wintermelon Tea.jpeg", 0));
            products.add(new Product("21", "Choco Mousse Frappe", 135.0, "Non-Coffee", "/com/example/project4/Images/Choco Mousse frappe.jpeg", 0));

// Pastry
            products.add(new Product("11", "Butter Croissant", 65.0, "Pastry", "/com/example/project4/Images/Butter Croissant (2).jpeg", 0));
            products.add(new Product("12", "Chocolate Chip Muffin", 75.0, "Pastry", "/com/example/project4/Images/Chocolate Chip Muffin (2).jpeg", 0));
            products.add(new Product("13", "Berry Moofeen", 95.0, "Pastry", "/com/example/project4/Images/Blueberry Muffins.jpg", 0));
            products.add(new Product("14", "Cinnamon Roll", 70.0, "Pastry", "/com/example/project4/Images/Cinnamon Roll pastry.jpeg", 0));
            products.add(new Product("15", "Ham and Cheese Bun", 85.0, "Pastry", "/com/example/project4/Images/Ham and Cheese Bun.jpeg", 0));
            products.add(new Product("22", "Cream Cheese Danish", 80.0, "Pastry", "/com/example/project4/Images/Cream Cheese Danish.jpeg", 0));
            products.add(new Product("23", "Red Velvet Cupcake", 75.0, "Pastry", "/com/example/project4/Images/Red Velvet Cupcake.jpeg", 0));
            products.add(new Product("24", "Peach Tart", 90.0, "Pastry", "/com/example/project4/Images/Peach Tart.jpeg", 0));

        }
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
    public void removeProduct(Product product) {
        products.removeIf(p -> p.getId().equals(product.getId()));
    }

}