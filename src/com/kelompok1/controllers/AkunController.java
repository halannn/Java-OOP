package com.kelompok1.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import com.kelompok1.models.Akun;
import com.kelompok1.models.User;
import com.kelompok1.types.JenisAkun;
import com.kelompok1.types.QueryOptions;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class AkunController extends BaseController implements Initializable {

    /* Attributes for Akun Table page */
    @FXML
    private TextField searchInput;

    @FXML
    private TableView<Akun> akunTable;

    @FXML
    private TableColumn<Akun, String> namaAkunCol;

    @FXML
    private TableColumn<Akun, String> jenisAkunCol;

    @FXML
    private TableColumn<Akun, Void> aksiCol;

    @FXML
    private Button prevBtn;

    @FXML
    private Button nextBtn;

    private int currentPage = 1;
    private int pageTotal = 1;
    private int limitVal = 50;
    /* End of attributes for Akun Table page */

    /* Attributes for Tambah/Ubah Akun Form page */
    @FXML
    private TextField namaAkunInput;

    @FXML
    private ComboBox<JenisAkun> jenisAkunInput;
    /* End of attributes for Tambah/Ubah Akun Form page */

    /* Attributes for Ubah Form Page */

    private ObjectProperty<Akun> selectedAkun = new SimpleObjectProperty<>();

    /* End of attributes for Ubah Form Page */

    public Akun getSelectedAkun() {
        return selectedAkun.get();
    }

    public void setSelectedAkun(Akun selectedAkun) {
        this.selectedAkun.set(selectedAkun);
    }

    public ObjectProperty<Akun> selectedAkunProperty() {
        return selectedAkun;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String filename = (new File(location.toURI().getPath())).getName();
            if (filename.equals("Akun.fxml")) {
                namaAkunCol.setCellValueFactory(new PropertyValueFactory<Akun, String>("namaAkun"));
                jenisAkunCol.setCellValueFactory(new PropertyValueFactory<Akun, String>("jenisAkun"));
                aksiCol.setCellFactory((new Callback<TableColumn<Akun, Void>, TableCell<Akun, Void>>() {
                    @Override
                    public TableCell<Akun, Void> call(final TableColumn<Akun, Void> param) {
                        final TableCell<Akun, Void> cell = new TableCell<Akun, Void>() {

                            private final HBox actionWrapper = new HBox();

                            private final Button ubahBtn = new Button("Ubah");
                            private final Button hapusBtn = new Button("Hapus");

                            {
                                ubahBtn.getStyleClass().add("secondary-btn");
                                hapusBtn.getStyleClass().add("danger-btn");
                                actionWrapper.getChildren().addAll(ubahBtn, hapusBtn);
                                ubahBtn.setOnAction((ActionEvent event) -> {
                                    Akun data = getTableView().getItems().get(getIndex());
                                    handleUbahBtn(data);
                                });
                                hapusBtn.setOnAction((ActionEvent event) -> {
                                    Akun data = getTableView().getItems().get(getIndex());
                                    hapusAkun(data);
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(actionWrapper);
                                }
                            }
                        };
                        return cell;
                    }
                }));
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
            } else if (filename.equals("TambahAkun.fxml")) {
                jenisAkunInput.getItems().addAll(JenisAkun.values());
            } else if (filename.equals("UbahAkun.fxml")) {
                jenisAkunInput.getItems().addAll(JenisAkun.values());
                selectedAkun.addListener(new ChangeListener<Akun>() {
                    @Override
                    public void changed(ObservableValue<? extends Akun> observable, Akun oldValue, Akun newValue) {
                        if (newValue == null) {
                            selectedAkunProperty().removeListener(this);
                        }
                        namaAkunInput.setText(newValue.getNamaAkun());
                        jenisAkunInput.setValue(newValue.getJenisAkun());
                    }
                });
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        QueryOptions options = new QueryOptions();
        options.setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());
        options.setLimit(OptionalInt.of(limitVal));
        if (searchInput.getText().length() > 0) {
            options.setSearch(Optional.of(searchInput.getText()));
        }
        options.setCurrentPage(OptionalInt.of(currentPage));
        int itemTotal = Akun.getAllCount(options);
        pageTotal = (int) Math.ceil((double) itemTotal / limitVal);
        if (itemTotal == 0) {
            currentPage = 0;
            pageTotal = 0;
        }
        ObservableList<Akun> data = Akun.getAll(options);
        akunTable.setItems(data);

        prevBtn.setDisable(false);
        nextBtn.setDisable(false);

        if(currentPage<=1){
            prevBtn.setDisable(true);
        }

        if(currentPage>=pageTotal){
            nextBtn.setDisable(true);
        }
    }

    public void handleUbahBtn(Akun selectedAkun) {
        try {
            AkunController controller = (AkunController) switchView("./views/UbahAkun.fxml");
            controller.setSelectedAkun(selectedAkun);
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

    public void tambahAkun(ActionEvent event) {
        String namaAkun = namaAkunInput.getText();
        String jenisAkun = jenisAkunInput.getValue() == null ? "" : jenisAkunInput.getValue().toString();
        if (namaAkun.equals("")) {
            Alert alt = new Alert(Alert.AlertType.ERROR);
            alt.setContentText("Nama Akun tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (jenisAkun.equals("")) {
            Alert alt = new Alert(Alert.AlertType.ERROR);
            alt.setContentText("Jenis Akun tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        Akun akun = new Akun(
                namaAkunInput.getText(),
                jenisAkunInput.getValue());
        akun.setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());
        akun.tambah();

        try {
            switchView("./views/Akun.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ubahAkun(ActionEvent event) {
        String namaAkun = namaAkunInput.getText();
        String jenisAkun = jenisAkunInput.getValue() == null ? "" : jenisAkunInput.getValue().toString();
        if (namaAkun.equals("")) {
            Alert alt = new Alert(Alert.AlertType.ERROR);
            alt.setContentText("Nama Akun tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (jenisAkun.equals("")) {
            Alert alt = new Alert(Alert.AlertType.ERROR);
            alt.setContentText("Jenis Akun tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        selectedAkun.get().setNamaAkun(namaAkun);
        selectedAkun.get().setJenisAkun(jenisAkunInput.getValue());
        selectedAkun.get().ubah();

        try {
            switchView("./views/Akun.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hapusAkun(Akun selectedAkun) {
        selectedAkun.hapus();
        refresh();
    }
}