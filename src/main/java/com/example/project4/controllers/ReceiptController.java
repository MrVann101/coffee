package com.example.project4.controllers;

import com.example.project4.models.ProductItem;
import com.example.project4.repositories.OrderHistoryRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptController {

    @FXML private TableView<OrderItem> tableView;
    @FXML private TableColumn<OrderItem, String> products;
    @FXML private TableColumn<OrderItem, Integer> quantity;
    @FXML private TableColumn<OrderItem, Double> price;
    @FXML private ChoiceBox<String> orderType;
    @FXML private Button placeOrderButton;
    @FXML private Button backButton;

    private final OrderHistoryRepository repo = new OrderHistoryRepository();

    @FXML
    public void initialize() {
        products.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));
        quantity.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        price.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        orderType.getItems().addAll("Dine In", "Take Out");
        orderType.getSelectionModel().selectFirst();

        placeOrderButton.setOnAction(e -> placeOrder());
        backButton.setOnAction(e -> goBack());
    }

    public void addItemToReceipt(String productName, int quantity, double price) {
        tableView.getItems().add(new OrderItem(productName, quantity, price));
    }

    private void placeOrder() {
        // Loop through all items in receipt table, save to order history repo
        for (OrderItem orderItem : tableView.getItems()) {
            ProductItem product = new ProductItem(
                    generateUniqueId(),
                    orderItem.getProductName(),
                    orderItem.getPrice(),
                    orderItem.getQuantity(),
                    "Order",  // default category
                    getCurrentDateTime()
            );
            repo.addOrder(product);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Thank you! Your order has been finalized.");
        alert.showAndWait();

        goBack();
    }

    private void goBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    // Helper method to generate a unique product/order ID
    private String generateUniqueId() {
        return "ORD-" + System.currentTimeMillis();
    }

    // Helper method to get current date-time as string
    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static class OrderItem {
        private final String productName;
        private final int quantity;
        private final double price;

        public OrderItem(String productName, int quantity, double price) {
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }

        public String getProductName() { return productName; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }
    }
}
