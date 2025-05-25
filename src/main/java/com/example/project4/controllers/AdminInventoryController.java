    package com.example.project4.controllers;

    import com.example.project4.models.Product;
    import com.example.project4.repositories.ProductRepository;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;

    public class AdminInventoryController {

        @FXML
        private ListView<Product> productListView;

        @FXML
        private TextField productIDAdminInventory;

        @FXML
        private TextField productNameAdminInventory;

        @FXML
        private TextField priceAdminInventory;

        @FXML
        private ChoiceBox<String> categoryAdminInventory;

        @FXML
        private ChoiceBox<String> imageChoice;

        private final ProductRepository productRepository = new ProductRepository();
        private ObservableList<Product> products;

        @FXML
        public void initialize() {
            // Initialize categories and image choices
            categoryAdminInventory.setItems(FXCollections.observableArrayList("Coffee", "Non-Coffee", "Pastry"));
            imageChoice.setItems(FXCollections.observableArrayList(
                    "/com/example/project4/Images/homepage.jpg",
                    "/com/example/project4/Images/default.jpg"
            ));

            // Load existing products into ObservableList
            products = FXCollections.observableArrayList(productRepository.getAllProducts());
            productListView.setItems(products);

            // Use toString of Product to display in ListView (overridden in Product class)
            productListView.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            });
        }

        @FXML
        private void onClickAdd() {
            // Validate inputs
            String id = productIDAdminInventory.getText().trim();
            String name = productNameAdminInventory.getText().trim();
            String priceText = priceAdminInventory.getText().trim();
            String category = categoryAdminInventory.getValue();
            String image = imageChoice.getValue();

            if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || category == null || image == null) {
                showAlert(Alert.AlertType.ERROR, "Please fill in all fields.");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Price must be a valid number.");
                return;
            }

            // Create new product and add to repository
            Product newProduct = new Product(id, name, price, category, image, 0);
            productRepository.addProduct(newProduct);

            // Update observable list (and thus the ListView)
            products.setAll(productRepository.getAllProducts());

            clearForm();
        }

        @FXML
        private void onClickDelete() {
            Product selectedProduct = productListView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                showAlert(Alert.AlertType.WARNING, "Please select a product to delete.");
                return;
            }
            productRepository.removeProduct(selectedProduct);
            products.setAll(productRepository.getAllProducts());
        }

        private void clearForm() {
            productIDAdminInventory.clear();
            productNameAdminInventory.clear();
            priceAdminInventory.clear();
            categoryAdminInventory.getSelectionModel().clearSelection();
            imageChoice.getSelectionModel().clearSelection();
        }

        private void showAlert(Alert.AlertType alertType, String message) {
            Alert alert = new Alert(alertType);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
