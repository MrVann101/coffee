package com.example.project4.models;

import javafx.beans.property.*;

public class ProductItem {
    private final StringProperty productId;
    private final StringProperty productName;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    private final StringProperty category;
    private final StringProperty dateTime;

    /** Default constructor initializing empty/default values */
    public ProductItem() {
        this("", "", 0.0, 0, "", "");
    }

    /** Full constructor */
    public ProductItem(String productId, String productName, double price, int quantity, String category, String dateTime) {
        this.productId = new SimpleStringProperty(productId);
        this.productName = new SimpleStringProperty(productName);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.category = new SimpleStringProperty(category);
        this.dateTime = new SimpleStringProperty(dateTime);
    }

    // Getters
    public String getProductId() { return productId.get(); }
    public String getProductName() { return productName.get(); }
    public double getPrice() { return price.get(); }
    public int getQuantity() { return quantity.get(); }
    public String getCategory() { return category.get(); }
    public String getDateTime() { return dateTime.get(); }

    // Properties for JavaFX bindings
    public StringProperty productIdProperty() { return productId; }
    public StringProperty productNameProperty() { return productName; }
    public DoubleProperty priceProperty() { return price; }
    public IntegerProperty quantityProperty() { return quantity; }
    public StringProperty categoryProperty() { return category; }
    public StringProperty dateTimeProperty() { return dateTime; }

    // Setters
    public void setProductId(String value) { productId.set(value); }
    public void setProductName(String value) { productName.set(value); }
    public void setPrice(double value) { price.set(value); }
    public void setQuantity(int value) { quantity.set(value); }
    public void setCategory(String value) { category.set(value); }
    public void setDateTime(String value) { dateTime.set(value); }

    @Override
    public String toString() {
        return "ProductItem{" +
                "productId=" + getProductId() +
                ", productName=" + getProductName() +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", category=" + getCategory() +
                ", dateTime=" + getDateTime() +
                '}';
    }
}
