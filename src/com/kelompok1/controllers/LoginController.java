package com.kelompok1.controllers;

import com.kelompok1.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {

    private Stage stage;
    private Scene scene;
    
    @FXML
    private TextField username;
    
    @FXML 
    private PasswordField password;

    @FXML
    private Label fgPass;

    @FXML
    private Button signInBtn;

    public void signIn(ActionEvent event) throws Exception {

        if (username.getText() == ""){
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("username tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (password.getText() == ""){
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("password tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        

        Parent root = FXMLLoader.load(App.class.getResource("./views/MainScene.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        // String css = this.getClass().getResource("MainScene.css").toExternalForm();
        // scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }


}
