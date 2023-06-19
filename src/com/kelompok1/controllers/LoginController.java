package com.kelompok1.controllers;

import com.kelompok1.models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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
        User pggn = User.getByUsername(username.getText());
        if (pggn==null){
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("username atau password salah");
            alt.showAndWait();
            return;
        }

        if (pggn.verifyPassword(password.getText()) == false){
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("username atau password salah");
            alt.showAndWait();
            return;
        }

        setUser(pggn);


        switchView("./views/Home.fxml");
    }

}
