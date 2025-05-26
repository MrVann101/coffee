package com.example.project4.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private ImageView logoImage;

    // Initialize any logic if needed when the view loads
    @FXML
    public void initialize() {
        // Example: Set welcome message or image (optional)
        welcomeLabel.setText("Welcome to Breeze Brew Coffee");
        logoImage.setImage(new Image(getClass().getResource("/com/example/project4/Images/Brown Vintage coffee shop logo2.jpg").toExternalForm()));
    }
}
