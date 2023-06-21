package com.kelompok1.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import com.kelompok1.models.Klien;
import com.kelompok1.models.User;
import com.kelompok1.types.Permissions;
import com.kelompok1.types.QueryOptions;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class KlienController extends BaseController {
    /* Attributes for Klien Table page */
    @FXML
    private TextField searchInput;

    @FXML
    private TableView<Klien> klienTable;

    @FXML
    private TableColumn<Klien, String> namaCol;

    @FXML
    private TableColumn<Klien, String> noTelpCol;

    @FXML
    private TableColumn<Klien, String> emailCol;

    @FXML
    private TableColumn<Klien, String> perusahaanCol;

    @FXML
    TableColumn<Klien, String> alamatCol;

    @FXML
    private TableColumn<Klien, Void> aksiCol;

    @FXML
    private Button prevBtn;

    @FXML
    private Button nextBtn;

    private int currentPage = 1;
    private int pageTotal = 1;
    private int limitVal = 50;
    /* End of attributes for Klien Table page */
    /* Attributes for Tambah/Ubah Klien Form page */
    @FXML
    private TextField namaInput;

    @FXML
    private TextField noTelpInput;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField perusahaanInput;

    @FXML
    private TextField alamatInput;

    /* End of attributes for Tambah/Ubah Klien Form page */
    /* Attributes for Ubah Form Page */
    private ObjectProperty<Klien> selectedKlien = new SimpleObjectProperty<>();
    /* End of attributes for Ubah Form Page */

    public Klien getSelectedKlien() {
        return selectedKlien.get();
    }

    public void setSelectedKlien(Klien selectedKlien) {
        this.selectedKlien.set(selectedKlien);
    }

    public ObjectProperty<Klien> selectedKlienProperty() {
        return selectedKlien;
    }

    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        try {
            String filename = (new File(location.toURI().toString())).getName();
            if (filename.equals("Klien.fxml")) {
                namaCol.setCellValueFactory(new PropertyValueFactory<Klien, String>("nama"));
                noTelpCol.setCellValueFactory(new PropertyValueFactory<Klien, String>("noTelp"));
                emailCol.setCellValueFactory(new PropertyValueFactory<Klien, String>("email"));
                perusahaanCol.setCellValueFactory(new PropertyValueFactory<Klien, String>("perusahaan"));
                alamatCol.setCellValueFactory(new PropertyValueFactory<Klien, String>("alamat"));
                aksiCol.setCellFactory(new Callback<TableColumn<Klien, Void>, TableCell<Klien, Void>>() {
                    @Override
                    public TableCell<Klien, Void> call(TableColumn<Klien, Void> param) {
                        return new TableCell<Klien, Void>() {
                            private final HBox actionWrapper = new HBox();

                            private final Button ubahBtn = new Button("Ubah");
                            private final Button hapusBtn = new Button("Hapus");

                            {
                                ubahBtn.getStyleClass().add("secondary-btn");
                                hapusBtn.getStyleClass().add("danger-btn");
                                actionWrapper.getChildren().addAll(ubahBtn, hapusBtn);
                                ubahBtn.setOnAction((event) -> {
                                    Klien klien = getTableView().getItems().get(getIndex());
                                    handleUbahBtn(klien);
                                });
                                hapusBtn.setOnAction((event) -> {
                                    Klien klien = getTableView().getItems().get(getIndex());
                                    hapusKlien(klien);
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(actionWrapper);
                                }
                            }
                        };
                    }
                });
                userProperty().addListener(new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                        if (newValue == null) {
                            userProperty().removeListener(this);
                        }
                        refresh();
                    }
                });
                searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                    refresh();
                });
            } else if (filename.equals("TambahKlien.fxml")) {

            } else if (filename.equals("UbahKlien.fxml")) {
                selectedKlien.addListener(new ChangeListener<Klien>() {

                    @Override
                    public void changed(ObservableValue<? extends Klien> observable, Klien oldValue, Klien newValue) {
                        if (newValue == null) {
                            selectedKlien.removeListener(this);
                            return;
                        }
                        namaInput.setText(newValue.getNama());
                        noTelpInput.setText(newValue.getNoTelp());
                        emailInput.setText(newValue.getEmail());
                        perusahaanInput.setText(newValue.getPerusahaan());
                        alamatInput.setText(newValue.getAlamat());
                    }

                });

            }
        } catch (

        URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        if (currentPage == 0) {
            currentPage = 1;
        }
        QueryOptions options = new QueryOptions();
        options.setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());
        options.setLimit(OptionalInt.of(limitVal));
        if (searchInput.getText().length() > 0) {
            options.setSearch(Optional.of(searchInput.getText()));
        }
        options.setCurrentPage(OptionalInt.of(currentPage));
        int itemTotal = Klien.getAllCount(options);
        pageTotal = (int) Math.ceil((double) itemTotal / limitVal);
        if (itemTotal == 0) {
            currentPage = 0;
            pageTotal = 0;
        }
        ObservableList<Klien> data = Klien.getAll(options);
        klienTable.setItems(data);

        prevBtn.setDisable(false);
        nextBtn.setDisable(false);

        if (currentPage <= 1) {
            prevBtn.setDisable(true);
        }

        if (currentPage >= pageTotal) {
            nextBtn.setDisable(true);
        }
    }

    public void handleUbahBtn(Klien selectedKlien) {
        try {
            KlienController controller = (KlienController) switchView("./views/UbahKlien.fxml");
            controller.setSelectedKlien(selectedKlien);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prevPage(ActionEvent event) {
        currentPage--;
        refresh();
    }

    public void nextPage(ActionEvent event) {
        currentPage++;
        refresh();
    }

    public void tambahKlien(ActionEvent event) {
        if (namaInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Nama tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (noTelpInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("No Telp tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (emailInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Email tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (perusahaanInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Perusahaan tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (alamatInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Alamat tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        Klien klien = new Klien(namaInput.getText(), noTelpInput.getText(), emailInput.getText(),
                perusahaanInput.getText(), alamatInput.getText());
        klien.setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());
        klien.tambah();

        try {
            switchView("./views/Klien.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ubahKlien(ActionEvent event) {
        if (namaInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Nama tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (noTelpInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("No Telp tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (emailInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Email tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (perusahaanInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Perusahaan tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (alamatInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Alamat tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        selectedKlien.get().setNama(namaInput.getText());
        selectedKlien.get().setNoTelp(noTelpInput.getText());
        selectedKlien.get().setEmail(emailInput.getText());
        selectedKlien.get().setPerusahaan(perusahaanInput.getText());
        selectedKlien.get().setAlamat(alamatInput.getText());
        selectedKlien.get().ubah();

        try {
            switchView("./views/Klien.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hapusKlien(Klien selectedKlien) {
        if ((getUser().role().getPermissionsFlag() & Permissions.MENGHAPUS_KLIEN) == 0) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Anda tidak memiliki akses untuk menghapus klien");
            alt.showAndWait();
            return;
        }
        selectedKlien.hapus();
        refresh();
    }
}
