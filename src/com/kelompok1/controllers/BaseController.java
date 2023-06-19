package com.kelompok1.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.kelompok1.App;
import com.kelompok1.models.User;

public class BaseController {
    private Stage stage;
    private ObjectProperty<User> user = new SimpleObjectProperty<>();

    public User getUser() {
        return user.get();
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public ObjectProperty<User> userProperty() {
        return user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public BaseController switchView(String viewPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(viewPath));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        BaseController controller = loader.getController();

        String css = App.class.getResource("./resources/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        controller.setStage(stage);
        controller.setUser(user.get());
        stage.show();
        return controller;
    }

    public void switchViewFromEvent(Event event) throws IOException {
        Node sourceNode = (Node) event.getSource();
        String viewPath = (String) sourceNode.getUserData();

        switchView(viewPath);
    }

    public void signOut() throws IOException {
        user = null;
        switchView("./views/Login.fxml");
    }

}
