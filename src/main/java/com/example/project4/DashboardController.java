package com.example.project4;

import com.example.project4.controllers.ProductController;
import com.example.project4.repositories.ProductRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class DashboardController {

    private ProductRepository repository = new ProductRepository();

    @FXML
    private FlowPane flowProductList;

    @FXML
    private BorderPane rootPane;

    @FXML
    protected void initialize() {
        var productList = repository.getAll();

        for (var product: productList) {
            try {
                var item = new FXMLLoader(getClass().getResource("product-item.fxml"));
                Node node = item.load();

                var controller = (ProductController)item.getController();
                controller.labelProductName.setText(product.name());
                controller.buttonClick.setOnAction( e -> {
                    System.out.println("Clicked: " + product.name());
                });

                flowProductList.getChildren().add(node);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void onClickHome() {
//        loadPage("");  // Home page, currently empty or add as needed
    }

    @FXML
    private void onClickMenu() {
//        loadPage("SecondDashboard.fxml");
    }

    @FXML
    private void onClickTran() {
//        loadPage("");  // Transaction page, currently empty or add as needed
    }
//
//    private void loadPage(String fxml) {
//        try {
//            var root = FXMLLoader.load(getClass().getResource(fxml));
//            rootPane.setCenter((Node) root);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}