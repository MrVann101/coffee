package com.example.project4.repositories;

import com.example.project4.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final static List<Product> products = new ArrayList<>();

    public ProductRepository() {
        if (products.isEmpty()) {
            products.add(new Product("1", "Coconut", 123.0, "Coffee", "/com/example/project4/Images/homepage.jpg", 0));
            products.add(new Product("2", "Red Horse", 456.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            products.add(new Product("3", "Coke", 78.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            products.add(new Product("4", "Cake", 78.0, "Pastry", "/com/example/project4/Images/default.jpg", 0));
            products.add(new Product("5", "Muffin", 78.0, "Pastry", "/com/example/project4/Images/default.jpg", 0));
            products.add(new Product("7", "Hotdog", 78.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            products.add(new Product("8", "Hotdog", 78.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            products.add(new Product("9", "Hotdog", 78.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            products.add(new Product("10", "Hotdog", 78.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            products.add(new Product("11", "Hotdog", 78.0, "Non-Coffee", "/com/example/project4/Images/default.jpg", 0));
            products.add(new Product("12", "macha", 85.0, "Coffee", "/com/example/project4/Images/default.jpg", 0));
        }
    }

    public List<Product> getAll() {
        return products;
    }

    public Product getById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // This method allows you to update the image path of a product by its ID
    public void updateProductImage(String productId, String newImagePath) {
        Product product = getById(productId);
        if (product != null) {
            product.setImagePath(newImagePath);  // Update image path in product
        }
    }
}
