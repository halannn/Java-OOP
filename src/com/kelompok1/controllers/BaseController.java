package com.kelompok1.controllers;

import javafx.event.Event;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Alert.AlertType;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.PasswordField;
// import javafx.scene.control.TextField;
// import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import com.kelompok1.App;
import com.kelompok1.models.User;

public class BaseController {
    private Stage stage;
    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void switchView(String viewPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(viewPath));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        BaseController controller = loader.getController();

        // String css = this.getClass().getResource("MainScene.css").toExternalForm();
        // scene.getStylesheets().add(css);

        stage.setScene(scene);
        controller.setStage(stage);
        controller.setUser(user);
        stage.show();
    }

    public void switchViewFromEvent(Event event) throws IOException {
        Node sourceNode = (Node) event.getSource();
        String viewPath = (String) sourceNode.getUserData();

        switchView(viewPath);
    }

    public void signOut() throws IOException{
        user = null;
        switchView("./views/Login.fxml");
    }


}
