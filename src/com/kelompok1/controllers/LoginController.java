package com.kelompok1.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class LoginController extends BaseController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label fgPass;

    @FXML
    private Button signInBtn;

    public void signIn(ActionEvent event) throws Exception {

        if (username.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Username tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (password.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Password tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        switchView("./views/MainDesktop.fxml");
    }

}
