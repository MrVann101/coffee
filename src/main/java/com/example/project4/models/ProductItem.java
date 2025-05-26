package com.example.project4.models;

import javafx.beans.property.*;

public class ProductItem {
    private final StringProperty productId;
    private final StringProperty productName;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    private final StringProperty category;
    private final StringProperty dateTime;
    private final StringProperty imagePath;

    // Full constructor
    public ProductItem(String productId, String productName, double price, int quantity, String category, String dateTime, String imagePath) {
        this.productId = new SimpleStringProperty(productId);
        this.productName = new SimpleStringProperty(productName);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.category = new SimpleStringProperty(category);
        this.dateTime = new SimpleStringProperty(dateTime);
        this.imagePath = new SimpleStringProperty(imagePath);
    }

    // Constructor without dateTime (optional)
    public ProductItem(String productId, String productName, double price, int quantity, String category, String imagePath) {
        this(productId, productName, price, quantity, category, "", imagePath);
    }

    // Getters
    public String getProductId() { return productId.get(); }
    public String getProductName() { return productName.get(); }
    public double getPrice() { return price.get(); }
    public int getQuantity() { return quantity.get(); }
    public String getCategory() { return category.get(); }
    public String getDateTime() { return dateTime.get(); }
    public String getImagePath() { return imagePath.get(); }

    // Properties (for JavaFX bindings)
    public StringProperty productIdProperty() { return productId; }
    public StringProperty productNameProperty() { return productName; }
    public DoubleProperty priceProperty() { return price; }
    public IntegerProperty quantityProperty() { return quantity; }
    public StringProperty categoryProperty() { return category; }
    public StringProperty dateTimeProperty() { return dateTime; }
    public StringProperty imagePathProperty() { return imagePath; }

    // Setters
    public void setProductId(String value) { productId.set(value); }
    public void setProductName(String value) { productName.set(value); }
    public void setPrice(double value) { price.set(value); }
    public void setQuantity(int value) { quantity.set(value); }
    public void setCategory(String value) { category.set(value); }
    public void setDateTime(String value) { dateTime.set(value); }
    public void setImagePath(String value) { imagePath.set(value); }
}
