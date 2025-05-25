package com.example.project4.models;

public class Product {
    private String id;
    private String name;
    private double price;
    private String category;
    private String imagePath;
    private int quantity;  // quantity to track ordered amount

    public Product(String id, String name, double price, String category, String imagePath, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
        this.quantity = quantity;
    }

    // Getters and setters for all fields
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
