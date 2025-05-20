package com.example.project4.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import java.io.IOException;

public class ParentController {

    @FXML
    private FlowPane parentFlowPane;

    @FXML
    void onClickHome(ActionEvent event) {
        loadFXMLIntoFlowPane("");
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

            // Pass reference of ParentController to child controllers
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
