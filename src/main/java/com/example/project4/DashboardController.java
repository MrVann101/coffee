package com.example.project4;

import com.example.project4.controllers.ProductController;
import com.example.project4.database.Database;
import com.example.project4.models.Product;
import com.example.project4.repositories.ProductRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import com.example.project4.models.ProductItem;
import com.example.project4.repositories.OrderHistoryRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DashboardController {

    private final ProductRepository repository = new ProductRepository();

    @FXML
    private FlowPane flowProductList;
    @FXML
    public BorderPane rootPane;
    @FXML
    private TableView<Product> orderSummary;
    @FXML
    private TableColumn<Product, String> tableItemList;
    @FXML
    private TableColumn<Product, Integer> tableQuantityList;
    @FXML
    private TableColumn<Product, Double> tablePriceList;
    @FXML
    private TableColumn<Product, Void> tableRemoveList;
    @FXML
    private Label totalLabel;
    @FXML
    private Button clearButton;
    @FXML
    private Button orderButton;
    @FXML
    private ComboBox<String> adminOptionComboBox;

    @FXML
    protected void initialize() {
        // Load default products
        loadProductsByCategory("Coffee");

        // Initialize order summary table columns
        tableItemList.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableQuantityList.setCellValueFactory(cellData -> new SimpleIntegerProperty((int) cellData.getValue().getQuantity()).asObject());
        tablePriceList.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        // Add remove button per row
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

        // Initialize combo box
        adminOptionComboBox.getItems().add("Admin Option");
        adminOptionComboBox.setPromptText("Admin");
    }

    private void loadProductsByCategory(String category) {
        flowProductList.getChildren().clear();

        List<Product> productList = repository.getAll().stream()
                .filter(product -> product.getCategory().equals(category))
                .toList();

        for (Product product : productList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("product-temp.fxml"));
                Node node = loader.load();

                // Get the controller from the loaded FXML
                ProductController controller = loader.getController();
                controller.labelProductName.setText(product.getName());

                // Set the product image
                controller.productImage.setImage(new Image(getClass().getResource(product.getImagePath()).toExternalForm()));

                // Set the product price
                controller.productPrice.setText(String.format("₱%.2f", product.getPrice()));

                // Handle button click action
                controller.buttonClick.setOnAction(e -> {
                    addToOrder(product, controller.getQuantity());  // Add to the order
                    controller.resetQuantity();  // Reset quantity to 1
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

    @FXML
    private void onClickCoffee() {
        loadProductsByCategory("Coffee");
    }

    @FXML
    private void onClickNonCoffee() {
        loadProductsByCategory("Non-Coffee");
    }

    @FXML
    private void onClickPastry() {
        loadProductsByCategory("Pastry");
    }

    @FXML
    private void clearOrder() {
        orderSummary.getItems().clear();
        updateTotal();
    }

    @FXML
    private void placeOrder() {
        if (orderSummary.getItems().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No items", "Your order is empty.");
            return;
        }

        OrderHistoryRepository repo = new OrderHistoryRepository();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = LocalDateTime.now().format(formatter);

        for (Product p : orderSummary.getItems()) {
            ProductItem item = new ProductItem(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory(),
                    dateTime
            );
            repo.addOrder(item);
        }

        showAlert(Alert.AlertType.INFORMATION, "Order placed", "Thank you! Your order has been placed.");
        clearOrder();
    }



    private void saveOrderToDatabase() {
        try (Connection conn = Database.connect()) {
            String sql = "INSERT INTO orders (id, name, price, category, imagePath, quantity) VALUES (?, ?, ?, ?, ?, ?)";
            for (Product product : orderSummary.getItems()) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, product.getId());
                    stmt.setString(2, product.getName());
                    stmt.setDouble(3, product.getPrice());
                    stmt.setString(4, product.getCategory());
                    stmt.setString(5, product.getImagePath());
                    stmt.setInt(6, product.getQuantity());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onClickAdmin() {
        String selected = adminOptionComboBox.getValue();
        if ("Admin Option".equals(selected)) {
            loadAdminView();
            adminOptionComboBox.getSelectionModel().clearSelection();
        }
    }

    private void loadAdminView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml")); // adjust path if needed
            Scene newScene = new Scene(loader.load());

            // Get the current stage
            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.setTitle("Admin Panel");
            currentStage.setWidth(1295);  // Set width to 800px
            currentStage.setHeight(690); // Set height to 600px
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load the admin scene.");
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
