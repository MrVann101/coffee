package com.example.project4;

import com.example.project4.controllers.ProductController;
import com.example.project4.controllers.ReceiptController;
import com.example.project4.database.Database;
import com.example.project4.models.Product;
import com.example.project4.repositories.ProductRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DashboardController {

    private final ProductRepository repository = new ProductRepository();

    @FXML private FlowPane flowProductList;
    @FXML public BorderPane rootPane;
    @FXML private TableView<Product> orderSummary;
    @FXML private TableColumn<Product, String> tableItemList;
    @FXML private TableColumn<Product, Integer> tableQuantityList;
    @FXML private TableColumn<Product, Double> tablePriceList;
    @FXML private TableColumn<Product, Void> tableRemoveList;
    @FXML private Label totalLabel;
    @FXML private Button clearButton;
    @FXML private Button orderButton;

    @FXML
    protected void initialize() {
        // Load Coffee products initially
        loadProductsByCategory("Coffee");

        tableItemList.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableQuantityList.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        tablePriceList.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        tableRemoveList.setCellFactory(col -> new TableCell<>() {
            private final Button removeButton = new Button("REMOVE");
            {
                removeButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    removeButton.setOnAction(e -> {
                        Product selected = getTableView().getItems().get(getIndex());
                        orderSummary.getItems().remove(selected);
                        updateTotal();
                    });
                    setGraphic(removeButton);
                }
            }
        });
    }

    private void loadProductsByCategory(String category) {
        flowProductList.getChildren().clear();

        List<Product> productList = repository.getAllProducts().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .toList();

        for (Product product : productList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("product-temp.fxml"));
                Node node = loader.load();

                ProductController controller = loader.getController();
                controller.labelProductName.setText(product.getName());
                controller.productImage.setImage(new Image(getClass().getResource(product.getImagePath()).toExternalForm()));
                controller.productPrice.setText(String.format("₱%.2f", product.getPrice()));

                controller.buttonClick.setOnAction(e -> {
                    addToOrder(product, controller.getQuantity());
                    controller.resetQuantity();
                });

                flowProductList.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addToOrder(Product product, int quantity) {
        if (quantity <= 0) return;

        for (Product p : orderSummary.getItems()) {
            if (p.getId().equals(product.getId())) {
                p.setQuantity(p.getQuantity() + quantity);
                orderSummary.refresh();
                updateTotal();
                return;
            }
        }

        Product ordered = new Product(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getImagePath(),
                quantity
        );
        orderSummary.getItems().add(ordered);
        updateTotal();
    }

    private void updateTotal() {
        double total = orderSummary.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        totalLabel.setText(String.format("Total: ₱%.2f", total));
    }

    @FXML private void onClickCoffee() {
        loadProductsByCategory("Coffee");
    }

    @FXML private void onClickNonCoffee() {
        loadProductsByCategory("Non-Coffee");
    }

    @FXML private void onClickPastry() {
        loadProductsByCategory("Pastry");
    }

    @FXML private void clearOrder() {
        orderSummary.getItems().clear();
        updateTotal();
    }

    @FXML private void placeOrder() {
        if (orderSummary.getItems().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No items", "Your order is empty.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("receipt.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Order Confirmation");
            stage.initModality(Modality.APPLICATION_MODAL);

            ReceiptController receiptController = loader.getController();

            orderSummary.getItems().forEach(item ->
                    receiptController.addItemToReceipt(item.getName(), item.getQuantity(), item.getPrice())
            );

            stage.showAndWait();

            clearOrder();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
