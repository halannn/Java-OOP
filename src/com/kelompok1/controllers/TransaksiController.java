package com.kelompok1.controllers;

import com.kelompok1.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TransaksiController {
    private Stage stage;
    private Scene scene;

    public void backToLogin(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("./views/Login.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        // String css = this.getClass().getResource("MainScene.css").toExternalForm();
        // scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public void menuHome(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("./views/MainDesktop.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        // String css = this.getClass().getResource("MainScene.css").toExternalForm();
        // scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public void menuTransaksi(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("./views/MainDesktop - Transaksi.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        // String css = this.getClass().getResource("MainScene.css").toExternalForm();
        // scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }
}