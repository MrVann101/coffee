package com.example.project4.controllers;

import com.example.project4.models.ProductItem;
import com.example.project4.repositories.OrderHistoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class AdminDashboardController {

    private static AdminDashboardController instance;

    public AdminDashboardController() {
        instance = this;
    }

    public static AdminDashboardController getInstance() {
        return instance;
    }

    @FXML private TableView<ProductItem> adminTableview;
    @FXML private TableColumn<ProductItem, String> adminProductId;
    @FXML private TableColumn<ProductItem, String> adminProductName;
    @FXML private TableColumn<ProductItem, Integer> adminQuantity;
    @FXML private TableColumn<ProductItem, Double> adminPrice;
    @FXML private TableColumn<ProductItem, String> adminCategory;
    @FXML private TableColumn<ProductItem, String> adminDateTime;
    @FXML private TableColumn<ProductItem, Double> adminTotalPrice;  // Total Price column

    @FXML private TextField searchField;

    private final OrderHistoryRepository repo = new OrderHistoryRepository();
    private ObservableList<ProductItem> productList;

    @FXML
    public void initialize() {
        productList = FXCollections.observableArrayList(repo.getAllOrders());

        adminProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        adminProductName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        adminQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        adminPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        adminCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        adminDateTime.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());

        // Set the total price column to price * quantity
        adminTotalPrice.setCellValueFactory(cellData -> {
            ProductItem item = cellData.getValue();
            double total = item.getPrice() * item.getQuantity();
            return new javafx.beans.property.SimpleDoubleProperty(total).asObject();
        });

        adminTableview.setItems(productList);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            List<ProductItem> filtered = repo.searchOrders(newVal);
            productList.setAll(filtered);
        });
    }

    public void refreshTable() {
        productList.setAll(repo.getAllOrders());
    }
}
