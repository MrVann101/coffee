package com.example.project4.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("admin".equals(username) && "admin".equals(password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project4/AdminDashboard.fxml"));
                Scene scene = new Scene(loader.load());

                // Get the current stage (window)
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene to the current stage
                stage.setScene(scene);

                // Set the width and height of the stage (window)
                stage.setWidth(1295);  // Set width to 1024px (you can change this)
                stage.setHeight(685);  // Set height to 768px (you can change this)

                // Show the updated scene
                stage.show();

            } catch (IOException e) {
                e.printStackTrace(); // print full stack trace
                showAlert("Failed to load dashboard: " + e.getMessage());
            }
        } else {
            showAlert("Incorrect username or password.");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
