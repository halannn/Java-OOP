package com.kelompok1.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.kelompok1.App;
import com.kelompok1.models.User;
import com.kelompok1.types.Permissions;

public class BaseController implements Initializable {
    private Stage stage;
    private ObjectProperty<User> user = new SimpleObjectProperty<>();

    @FXML
    private BaseController sidebarController;

    @FXML
    private Label homeMenu;

    @FXML
    private Label transaksiMenu;

    @FXML
    private Label jurnalUmumMenu;

    @FXML
    private Label trialBalanceMenu;

    @FXML
    private Label bukuBesarMenu;

    @FXML
    private Label labaRugiMenu;

    @FXML
    private Label neracaKeuanganMenu;

    @FXML
    private Label akunMenu;

    @FXML
    private Label klienMenu;

    @FXML
    private Label userMenu;

    @FXML
    private Label roleMenu;

    @FXML
    private Label keluarMenu;

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

        Scene scene = stage.getScene();
        scene.setRoot(root);
        BaseController controller = loader.getController();

        String css = App.class.getResource("./resources/main.css").toExternalForm();
        scene.getStylesheets().add(css);

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (sidebarController instanceof BaseController) {
            userProperty().addListener((observable, oldValue, newValue) -> {
                sidebarController.setUser(userProperty().get());
                sidebarController.setStage(getStage());
            });

            homeMenu = ((BaseController) sidebarController).homeMenu;
            transaksiMenu = ((BaseController) sidebarController).transaksiMenu;
            jurnalUmumMenu = ((BaseController) sidebarController).jurnalUmumMenu;
            trialBalanceMenu = ((BaseController) sidebarController).trialBalanceMenu;
            bukuBesarMenu = ((BaseController) sidebarController).bukuBesarMenu;
            labaRugiMenu = ((BaseController) sidebarController).labaRugiMenu;
            neracaKeuanganMenu = ((BaseController) sidebarController).neracaKeuanganMenu;
            akunMenu = ((BaseController) sidebarController).akunMenu;
            klienMenu = ((BaseController) sidebarController).klienMenu;
            userMenu = ((BaseController) sidebarController).userMenu;
            roleMenu = ((BaseController) sidebarController).roleMenu;

            homeMenu.getStyleClass().remove("active");
            transaksiMenu.getStyleClass().remove("active");
            jurnalUmumMenu.getStyleClass().remove("active");
            trialBalanceMenu.getStyleClass().remove("active");
            bukuBesarMenu.getStyleClass().remove("active");
            labaRugiMenu.getStyleClass().remove("active");
            neracaKeuanganMenu.getStyleClass().remove("active");
            akunMenu.getStyleClass().remove("active");
            klienMenu.getStyleClass().remove("active");
            userMenu.getStyleClass().remove("active");
            roleMenu.getStyleClass().remove("active");

            String filename = (new File(location.getPath())).getName();

            switch (filename) {
                case "Home.fxml":
                    homeMenu.getStyleClass().add("active");
                    break;
                case "Transaksi.fxml":
                    transaksiMenu.getStyleClass().add("active");
                    break;
                case "JurnalUmum.fxml":
                    jurnalUmumMenu.getStyleClass().add("active");
                    break;
                case "TrialBalance.fxml":
                    trialBalanceMenu.getStyleClass().add("active");
                    break;
                case "BukuBesar.fxml":
                    bukuBesarMenu.getStyleClass().add("active");
                    break;
                case "LabaRugi.fxml":
                    labaRugiMenu.getStyleClass().add("active");
                    break;
                case "NeracaKeuangan.fxml":
                    neracaKeuanganMenu.getStyleClass().add("active");
                    break;
                case "Akun.fxml":
                    akunMenu.getStyleClass().add("active");
                    break;
                case "Klien.fxml":
                    klienMenu.getStyleClass().add("active");
                    break;
                case "User.fxml":
                    userMenu.getStyleClass().add("active");
                    break;
                case "Role.fxml":
                    roleMenu.getStyleClass().add("active");
                    break;
                default:
                    break;
            }
        }
    }

}
