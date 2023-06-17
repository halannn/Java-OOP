package com.kelompok1;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    public static Dotenv dotenv;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./views/Login.fxml"));

        Scene scene = new Scene(root);

        // String css = this.getClass().getResource("MainScene.css").toExternalForm();
        // scene.getStylesheets().add(css);

        Image icon = new Image(getClass().getResourceAsStream("resources/salary.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Sistem Informasi Akutansi");
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        dotenv = Dotenv.load();
        launch(args);
    }
}