package com.example.project4.controllers;

import com.example.project4.models.Product;
import com.example.project4.repositories.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminInventoryController {

    @FXML private TextField productID;
    @FXML private TextField productName;
    @FXML private TextField productPrice;
    @FXML private ChoiceBox<String> productCategory;
    @FXML private ChoiceBox<String> productImage;
    @FXML private ListView<String> inventoryListView;

    private final ObservableList<String> inventoryItems = FXCollections.observableArrayList();
    private final ProductRepository repository = new ProductRepository();

    @FXML
    public void initialize() {
        productCategory.getItems().addAll("Coffee", "Non-Coffee", "Pastry");
        productImage.getItems().addAll(
                "/com/example/project4/Images/homepage.jpeg",
                "/com/example/project4/Images/default.jpeg"
        );

        inventoryListView.setItems(inventoryItems);
        loadProductsToListView();
    }

    private void loadProductsToListView() {
        inventoryItems.clear();
        for (Product p : repository.getAllProducts()) {
            inventoryItems.add(p.toString());
        }
    }

    @FXML
    private void handleProductAdd() {
        String id = productID.getText().trim();
        String name = productName.getText().trim();
        String priceText = productPrice.getText().trim();
        String category = productCategory.getValue();
        String imagePath = productImage.getValue();

        if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || category == null || imagePath == null) {
            showAlert("Please fill all fields.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showAlert("Invalid price format.");
            return;
        }

        // Check if product ID already exists in repository
        for (Product p : repository.getAllProducts()) {
            if (p.getId().equals(id)) {
                showAlert("Product ID already exists.");
                return;
            }
        }

        Product newProduct = new Product(id, name, price, category, imagePath, 0);
        repository.addProduct(newProduct);
        inventoryItems.add(newProduct.toString());
        clearFields();
        showAlert("Product added successfully!");
    }

    @FXML
    private void handleProductDelete() {
        String selected = inventoryListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select a product to delete.");
            return;
        }

        // Extract name from list string
        String name = selected.split(" - ")[0];

        // Find product by name
        Product productToDelete = null;
        for (Product p : repository.getAllProducts()) {
            if (p.getName().equals(name)) {
                productToDelete = p;
                break;
            }
        }

        if (productToDelete == null) {
            showAlert("Product not found.");
            return;
        }

        repository.removeProduct(productToDelete);
        inventoryItems.remove(selected);
        showAlert("Product deleted successfully!");
    }

    private void clearFields() {
        productID.clear();
        productName.clear();
        productPrice.clear();
        productCategory.setValue(null);
        productImage.setValue(null);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inventory");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
