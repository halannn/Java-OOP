package com.kelompok1.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.kelompok1.App;
import com.kelompok1.models.User;
import com.kelompok1.types.Permissions;

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
        URL pathURL = App.class.getResource(viewPath);

        try {
            if (user.get() != null) {
                int permissionsFlag = user.get().role().getPermissionsFlag();
                String filename = (new File(pathURL.toURI())).getName();
                switch (filename) {
                    case "Transaksi.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_TRANSAKSI) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "JurnalUmum.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_LAPORAN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "TrialBalance.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_LAPORAN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "BukuBesar.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_LAPORAN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "LabaRugi.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_LAPORAN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "NeracaKeuangan.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_LAPORAN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "Akun.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_AKUN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "Klien.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_KLIEN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "User.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_USER) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "Role.fxml":
                        if ((permissionsFlag & Permissions.MELIHAT_ROLE) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "TambahTransaksi.fxml":
                        if ((permissionsFlag & Permissions.MENAMBAH_TRANSAKSI) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "TambahAkun.fxml":
                        if ((permissionsFlag & Permissions.MENAMBAH_AKUN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "TambahKlien.fxml":
                        if ((permissionsFlag & Permissions.MENAMBAH_KLIEN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "TambahUser.fxml":
                        if ((permissionsFlag & Permissions.MENAMBAH_USER) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "TambahRole.fxml":
                        if ((permissionsFlag & Permissions.MENAMBAH_ROLE) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "UbahTransaksi.fxml":
                        if ((permissionsFlag & Permissions.MENGUBAH_TRANSAKSI) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "UbahAkun.fxml":
                        if ((permissionsFlag & Permissions.MENGUBAH_AKUN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "UbahKlien.fxml":
                        if ((permissionsFlag & Permissions.MENGUBAH_KLIEN) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "UbahUser.fxml":
                        if ((permissionsFlag & Permissions.MENGUBAH_USER) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    case "UbahRole.fxml":
                        if ((permissionsFlag & Permissions.MENGUBAH_ROLE) == 0) {
                            return switchView("./views/Unauthorized.fxml");
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(pathURL);
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
        setUser(null);
        switchView("./views/Login.fxml");
    }

}
