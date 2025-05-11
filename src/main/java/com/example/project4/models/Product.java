package com.example.project4.models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private String id;
    private String name;
    private double price;
    private String category;
    private StringProperty imagePath; // Changed to a StringProperty
    private int quantity;

    public Product(String id, String name, double price, String category, String imagePath, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imagePath = new SimpleStringProperty(imagePath); // Initialize imagePath as a property
        this.quantity = quantity;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);  // Change the image path and update the property
    }

    public StringProperty imagePathProperty() {
        return imagePath; // Returns the image path property
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
