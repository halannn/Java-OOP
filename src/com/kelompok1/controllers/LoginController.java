package com.kelompok1.controllers;

import com.kelompok1.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    private Stage stage;
    private Scene scene;

    public void switchToMain(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("./views/MainScene.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        // String css = this.getClass().getResource("MainScene.css").toExternalForm();
        // scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }
    
}
