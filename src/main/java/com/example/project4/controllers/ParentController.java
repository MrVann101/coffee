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

public class ParentController implements Initializable {

    @FXML
    private FlowPane parentFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Automatically load the dashboard view on start
        loadFXMLIntoFlowPane("/com/example/project4/dashboard.fxml");
    }

    @FXML
    void onClickHome(ActionEvent event) {
        loadFXMLIntoFlowPane("/com/example/project4/home.fxml");
    }

    @FXML
    void onClickMenu(ActionEvent event) {
        loadFXMLIntoFlowPane("/com/example/project4/dashboard.fxml");
    }

    @FXML
    void onClickAdmin(ActionEvent event) {
        loadFXMLIntoFlowPane("/com/example/project4/Login.fxml");
    }

    public void loadFXMLIntoFlowPane(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Region content = loader.load();

            parentFlowPane.getChildren().clear();

            content.prefWidthProperty().bind(parentFlowPane.widthProperty());
            content.prefHeightProperty().bind(parentFlowPane.heightProperty());

            parentFlowPane.getChildren().add(content);

            // Optional: Set parent reference in child controllers
            Object controller = loader.getController();
            if (controller instanceof AdminLoginController) {
                ((AdminLoginController) controller).setParentController(this);
            } else if (controller instanceof AdminParentController) {
                ((AdminParentController) controller).setParentController(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
