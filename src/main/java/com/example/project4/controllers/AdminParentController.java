package com.example.project4.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminParentController implements Initializable {

    @FXML
    private FlowPane adminParentFlowPane;

    private ParentController parentController;

    public void setParentController(ParentController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Automatically load AdminDashboard.fxml when adminParent.fxml is shown
        loadFXMLIntoFlowPane("/com/example/project4/AdminDashboard.fxml");
    }

    @FXML
    void onClickAdminHistory(ActionEvent event) {
        loadFXMLIntoFlowPane("/com/example/project4/AdminDashboard.fxml");
    }

    @FXML
    void onClickAdminInventory(ActionEvent event) {
        loadFXMLIntoFlowPane("/com/example/project4/AdminInventory.fxml");
    }

    @FXML
    void onClickSignOut(ActionEvent event) {
        parentController.loadFXMLIntoFlowPane("/com/example/project4/Login.fxml");
    }

    public void loadFXMLIntoFlowPane(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Region content = loader.load();

            adminParentFlowPane.getChildren().clear();

            content.prefWidthProperty().bind(adminParentFlowPane.widthProperty());
            content.prefHeightProperty().bind(adminParentFlowPane.heightProperty());

            adminParentFlowPane.getChildren().add(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
