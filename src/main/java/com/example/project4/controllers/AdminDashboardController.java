package com.example.project4.controllers;

import com.example.project4.models.ProductItem;
import com.example.project4.repositories.OrderHistoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class AdminDashboardController {

    @FXML
    private TableView<ProductItem> adminTableview;

    @FXML
    private TableColumn<ProductItem, String> adminProductId;

    @FXML
    private TableColumn<ProductItem, String> adminProductName;

    @FXML
    private TableColumn<ProductItem, Integer> adminQuantity;

    @FXML
    private TableColumn<ProductItem, Double> adminPrice;

    @FXML
    private TableColumn<ProductItem, String> adminCategory;

    @FXML
    private TableColumn<ProductItem, String> adminDateTime;

    @FXML
    private TableColumn<ProductItem, Void> adminRemove;
    @FXML private Button SignOut;

    @FXML
    private TextField searchField;

    private ObservableList<ProductItem> productList;
    private final OrderHistoryRepository repo = new OrderHistoryRepository();

    @FXML
    public void initialize() {
        productList = FXCollections.observableArrayList(repo.getAllOrders());

        adminProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        adminProductName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        adminQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        adminPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        adminCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        adminDateTime.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());

        setupRemoveButtonColumn();

        adminTableview.setItems(productList);

        // 🔍 Search functionality
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            List<ProductItem> filtered = repo.searchOrders(newVal);
            productList.setAll(filtered);
        });
    }

    private void setupRemoveButtonColumn() {
        Callback<TableColumn<ProductItem, Void>, TableCell<ProductItem, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ProductItem, Void> call(final TableColumn<ProductItem, Void> param) {
                return new TableCell<>() {
                    private final Button removeBtn = new Button("Remove");

                    {
                        removeBtn.setOnAction(event -> {
                            ProductItem item = getTableView().getItems().get(getIndex());
                            repo.removeOrder(item.getProductId());
                            productList.remove(item);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(removeBtn);
                        }
                    }
                };
            }
        };

        adminRemove.setCellFactory(cellFactory);
    }
    @FXML
    private void handleSignOut() {
        try {
            // Load the login or main dashboard scene (adjust path if needed)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project4/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) SignOut.getScene().getWindow(); // current window
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
