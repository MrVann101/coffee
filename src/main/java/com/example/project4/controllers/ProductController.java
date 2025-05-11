package com.example.project4.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import com.example.project4.models.Product;

public class ProductController {

    @FXML
    public Label labelProductName;
    @FXML
    public Button buttonClick;
    @FXML
    public Button decrementButton;
    @FXML
    public Label quantityLabel;
    @FXML
    public Button incrementButton;
    @FXML
    public ImageView productImage;
    @FXML
    public VBox productBox;
    @FXML
    public Label productPrice;

    private int quantity = 1; // Default quantity is 1

    @FXML
    private void initialize() {
        quantityLabel.setText(String.valueOf(quantity));

        decrementButton.setOnAction(event  -> {
            if (quantity > 1) {
                quantity--;
                quantityLabel.setText(String.valueOf(quantity));
            }
        });

        incrementButton.setOnAction(event -> {
            quantity++;
            quantityLabel.setText(String.valueOf(quantity));
        });
    }

    public int getQuantity() {
        return quantity;
    }

    public void resetQuantity() {
        quantity = 1;
        quantityLabel.setText(String.valueOf(quantity));
    }
}
