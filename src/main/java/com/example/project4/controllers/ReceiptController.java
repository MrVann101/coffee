package com.example.project4.controllers;

import com.example.project4.models.ProductItem;
import com.example.project4.repositories.OrderHistoryRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
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
    @FXML private ChoiceBox<String> paymentType;

    @FXML private Label totalCostLabel;
    @FXML private TextField customerPaymentField;
    @FXML private Label changeLabel;

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

        paymentType.getItems().addAll("Cash");
        paymentType.getSelectionModel().selectFirst();

        placeOrderButton.setOnAction(e -> placeOrder());
        backButton.setOnAction(e -> goBack());

        tableView.getItems().addListener((ListChangeListener<OrderItem>) c -> updateTotalCost());
        customerPaymentField.textProperty().addListener((obs, oldText, newText) -> updateChange());

        updateTotalCost();
        updateChange();
    }

    public void addItemToReceipt(String productName, int quantity, double price) {
        tableView.getItems().add(new OrderItem(productName, quantity, price));
        updateTotalCost();
        updateChange();
    }

    private void placeOrder() {
        double customerPayment;
        try {
            customerPayment = Double.parseDouble(customerPaymentField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number for Customer Payment.");
            return;
        }

        double total = calculateTotalCost();
        if (customerPayment < total && paymentType.getValue().equals("Cash")) {
            showAlert(Alert.AlertType.ERROR, "Insufficient Payment", "Customer payment is less than total cost.");
            return;
        }

        String selectedPayment = paymentType.getValue();
        String selectedOrderType = orderType.getValue();
        String dateTimeNow = getCurrentDateTime();

        String customerId = generateUniqueId();
        StringBuilder receipt = new StringBuilder();

        // Header
        receipt.append("Breeze Brew\n");
        receipt.append("Valencia City PH: (609) 555-5678\n\n");

        receipt.append("Date: ").append(dateTimeNow.split(" ")[0])
                .append("   Time: ").append(dateTimeNow.split(" ")[1]).append("\n");
        receipt.append("Customer ID: ").append(customerId).append("\n");
        receipt.append("Cashier: Admin\n\n");

        // Items
        double subtotal = 0;
        for (OrderItem orderItem : tableView.getItems()) {
            double itemTotal = orderItem.getPrice() * orderItem.getQuantity();
            receipt.append(orderItem.getQuantity()).append("x ")
                    .append(orderItem.getProductName()).append("  ₱")
                    .append(String.format("%.2f", itemTotal)).append("\n");

            subtotal += itemTotal;

            ProductItem product = new ProductItem(
                    customerId,
                    orderItem.getProductName(),
                    orderItem.getPrice(),
                    orderItem.getQuantity(),
                    selectedOrderType + " / " + selectedPayment,
                    dateTimeNow
            );
            repo.addOrder(product);
        }

        // Removed tax calculation

        double change = customerPayment - subtotal;

        // Removed tax line from receipt
        receipt.append("Total: ₱").append(String.format("%.2f", subtotal)).append("\n");
        receipt.append("Payment: ₱").append(String.format("%.2f", customerPayment)).append("\n");
        receipt.append("Change: ₱").append(String.format("%.2f", change)).append("\n\n");

        receipt.append("Thank you for visiting!\n");

        // Show receipt alert
        showAlert(Alert.AlertType.INFORMATION, "Order Receipt", receipt.toString());

        // Refresh admin table if open
        AdminDashboardController adminController = AdminDashboardController.getInstance();
        if (adminController != null) {
            adminController.refreshTable();
        }

        goBack();
    }

    private double calculateTotalCost() {
        return tableView.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    private void updateTotalCost() {
        double total = calculateTotalCost();
        totalCostLabel.setText("₱" + String.format("%.2f", total));
        updateChange();
    }

    private void updateChange() {
        double total = calculateTotalCost();
        double payment;

        try {
            payment = Double.parseDouble(customerPaymentField.getText());
        } catch (NumberFormatException e) {
            changeLabel.setText("₱0.00");
            return;
        }

        double change = payment - total;
        changeLabel.setText(change < 0 ? "₱0.00" : "₱" + String.format("%.2f", change));
    }

    private void goBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    private String generateUniqueId() {
        return "ORD-" + System.currentTimeMillis();
    }

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
