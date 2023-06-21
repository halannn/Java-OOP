package com.kelompok1.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import com.kelompok1.models.Akun;
import com.kelompok1.models.Klien;
import com.kelompok1.models.Transaksi;
import com.kelompok1.models.User;
import com.kelompok1.types.Permissions;
import com.kelompok1.types.PosisiAkun;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TransaksiController extends BaseController implements Initializable {
    /* Attributes for Role Table page */
    @FXML
    private DatePicker fromInput;

    @FXML
    private DatePicker toInput;

    @FXML
    private Button goBtn;

    @FXML
    private TableView<Transaksi> transaksiTable;

    @FXML
    private TableColumn<Transaksi, LocalDate> tanggalCol;

    @FXML
    private TableColumn<Transaksi, Akun> akunCol;

    @FXML
    private TableColumn<Transaksi, PosisiAkun> posisiAkunCol;

    @FXML
    private TableColumn<Transaksi, Klien> klienCol;

    @FXML
    private TableColumn<Transaksi, String> keteranganCol;

    @FXML
    private TableColumn<Transaksi, Integer> nominalCol;

    @FXML
    private TableColumn<Transaksi, Void> aksiCol;

    /* End of attributes for Role Table page */
    /* Attributes for Tambah/Ubah Role Form page */
    @FXML
    private DatePicker tanggalInput;

    @FXML
    private ComboBox<Akun> akunInput;

    @FXML
    private ComboBox<PosisiAkun> posisiAkunInput;

    @FXML
    private ComboBox<Klien> klienInput;

    @FXML
    private TextField keteranganInput;

    @FXML
    private TextField nominalInput;

    /* End of attributes for Tambah/Ubah Role Form page */
    /* Attributes for Ubah Form Page */
    private ObjectProperty<Transaksi> selectedTransaksi = new SimpleObjectProperty<>();
    /* End of attributes for Ubah Form Page */

    public Transaksi getSelectedTransaksi() {
        return selectedTransaksi.get();
    }

    public void setSelectedTransaksi(Transaksi selectedTransaksi) {
        this.selectedTransaksi.set(selectedTransaksi);
    }

    public ObjectProperty<Transaksi> selectedTransaksiProperty() {
        return selectedTransaksi;
    }

    public void initialize(URL location, ResourceBundle resources) {

        try {
            String filename = (new File(location.toURI().toString())).getName();
            if (filename.equals("Transaksi.fxml")) {
                tanggalCol.setCellValueFactory(new PropertyValueFactory<Transaksi, LocalDate>("tanggal"));
                akunCol.setCellValueFactory(arg0 -> {
                    return new SimpleObjectProperty<Akun>(arg0.getValue().akun());
                });
                akunCol.setCellFactory(new Callback<TableColumn<Transaksi, Akun>, TableCell<Transaksi, Akun>>() {
                    @Override
                    public TableCell<Transaksi, Akun> call(TableColumn<Transaksi, Akun> arg0) {
                        return new TableCell<Transaksi, Akun>() {
                            @Override
                            protected void updateItem(Akun item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item == null || empty) {
                                    setText(null);
                                } else {
                                    setText(item.getNamaAkun());
                                }
                            }
                        };
                    }
                });
                posisiAkunCol.setCellValueFactory(new PropertyValueFactory<Transaksi, PosisiAkun>("posisiAkun"));
                klienCol.setCellValueFactory(arg0 -> {
                    return new SimpleObjectProperty<Klien>(arg0.getValue().klien());
                });
                klienCol.setCellFactory(new Callback<TableColumn<Transaksi, Klien>, TableCell<Transaksi, Klien>>() {
                    @Override
                    public TableCell<Transaksi, Klien> call(TableColumn<Transaksi, Klien> arg0) {
                        return new TableCell<Transaksi, Klien>() {
                            @Override
                            protected void updateItem(Klien item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item == null || empty) {
                                    setText(null);
                                } else {
                                    setText(item.getNama());
                                }
                            }
                        };
                    }
                });
                keteranganCol.setCellValueFactory(new PropertyValueFactory<Transaksi, String>("keterangan"));
                nominalCol.setCellValueFactory(new PropertyValueFactory<Transaksi, Integer>("nominal"));
                aksiCol.setCellFactory(new Callback<TableColumn<Transaksi, Void>, TableCell<Transaksi, Void>>() {
                    @Override
                    public TableCell<Transaksi, Void> call(TableColumn<Transaksi, Void> param) {
                        return new TableCell<Transaksi, Void>() {
                            private final HBox actionWrapper = new HBox();

                            private final Button ubahBtn = new Button("Ubah");
                            private final Button hapusBtn = new Button("Hapus");

                            {
                                ubahBtn.getStyleClass().add("secondary-btn");
                                hapusBtn.getStyleClass().add("danger-btn");
                                actionWrapper.getChildren().addAll(ubahBtn, hapusBtn);
                                ubahBtn.setOnAction((event) -> {
                                    Transaksi user = getTableView().getItems().get(getIndex());
                                    handleUbahBtn(user);
                                });
                                hapusBtn.setOnAction((event) -> {
                                    Transaksi user = getTableView().getItems().get(getIndex());
                                    hapusTransaksi(user);
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
                
                fromInput.setValue(LocalDate.now());
                toInput.setValue(LocalDate.now());

                goBtn.addEventHandler(ActionEvent.ACTION, event -> {
                    refresh();
                });
            } else if (filename.equals("TambahTransaksi.fxml")) {
                userProperty().addListener(new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                        if (newValue == null) {
                            userProperty().removeListener(this);
                        }
                        posisiAkunInput.getItems().add(PosisiAkun.Debit);
                        posisiAkunInput.getItems().add(PosisiAkun.Kredit);

                        QueryOptions options = new QueryOptions();
                        options.setLimit(OptionalInt.of(Integer.MAX_VALUE));
                        options.setCurrentPage(OptionalInt.of(1));
                        options.setIdOwnPerusahaan(newValue.getIdOwnPerusahaan());
                        ObservableList<Akun> akuns = Akun.getAll(options);
                        akunInput.setItems(akuns);
                        akunInput.setConverter(new StringConverter<Akun>() {
                            @Override
                            public String toString(Akun akun) {
                                return akun.getNamaAkun();
                            }

                            @Override
                            public Akun fromString(String akun) {
                                return null;
                            }
                        });
                        posisiAkunInput.setConverter(new StringConverter<PosisiAkun>() {
                            @Override
                            public String toString(PosisiAkun posisiAkun) {
                                return posisiAkun.toString();
                            }

                            @Override
                            public PosisiAkun fromString(String posisiAkun) {
                                return PosisiAkun.fromString(posisiAkun);
                            }
                        });
                        ObservableList<Klien> kliens = Klien.getAll(options);
                        kliens.add(0, null);
                        klienInput.setItems(kliens);
                        klienInput.setConverter(new StringConverter<Klien>() {
                            @Override
                            public String toString(Klien klien) {
                                return klien.getNama();
                            }

                            @Override
                            public Klien fromString(String klien) {
                                return null;
                            }
                        });
                    }
                });
            } else if (filename.equals("UbahTransaksi.fxml")) {

                selectedTransaksiProperty().addListener(new ChangeListener<Transaksi>() {
                    @Override
                    public void changed(ObservableValue<? extends Transaksi> observable, Transaksi oldValue,
                            Transaksi newValue) {
                        if (newValue == null) {
                            selectedTransaksi.removeListener(this);
                        }

                        tanggalInput.setValue(newValue.getTanggal());
                        nominalInput.setText(String.valueOf(newValue.getNominal()));
                        keteranganInput.setText(newValue.getKeterangan());

                        posisiAkunInput.getItems().add(PosisiAkun.Debit);
                        posisiAkunInput.getItems().add(PosisiAkun.Kredit);

                        posisiAkunInput.setValue(newValue.getPosisiAkun());

                        QueryOptions options = new QueryOptions();
                        options.setLimit(OptionalInt.of(Integer.MAX_VALUE));
                        options.setCurrentPage(OptionalInt.of(1));
                        options.setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());
                        ObservableList<Akun> akuns = Akun.getAll(options);
                        akunInput.setItems(akuns);
                        akunInput.setConverter(new StringConverter<Akun>() {
                            @Override
                            public String toString(Akun akun) {
                                return akun.getNamaAkun();
                            }

                            @Override
                            public Akun fromString(String akun) {
                                return null;
                            }
                        });

                        for (Akun akun : akuns) {
                            if (akun.getId() == newValue.getIdAkun()) {
                                akunInput.setValue(akun);
                                break;
                            }
                        }

                        posisiAkunInput.setConverter(new StringConverter<PosisiAkun>() {
                            @Override
                            public String toString(PosisiAkun posisiAkun) {
                                return posisiAkun.toString();
                            }

                            @Override
                            public PosisiAkun fromString(String posisiAkun) {
                                return PosisiAkun.fromString(posisiAkun);
                            }
                        });

                        ObservableList<Klien> kliens = Klien.getAll(options);
                        kliens.add(0, null);
                        klienInput.setItems(kliens);
                        klienInput.setConverter(new StringConverter<Klien>() {
                            @Override
                            public String toString(Klien klien) {
                                return klien.getNama();
                            }

                            @Override
                            public Klien fromString(String klien) {
                                return null;
                            }
                        });

                        for (Klien klien : kliens) {
                            if (klien != null && klien.getId() == newValue.getIdKlien()) {
                                klienInput.setValue(klien);
                                break;
                            }
                        }
                    }
                });
            }
        } catch (

        URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        if(fromInput.getValue()==null){
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Periode Awal tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if(toInput.getValue()==null){
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Periode Akhir tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if(fromInput.getValue().compareTo(toInput.getValue())>0){
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Periode Awal tidak boleh lebih besar dari Periode Akhir");
            alt.showAndWait();
            return;
        }
        ObservableList<Transaksi> transaksi = Transaksi.getRanged(getUser().getIdOwnPerusahaan(), fromInput.getValue(), toInput.getValue());
        transaksiTable.setItems(transaksi);
    }

    public void handleUbahBtn(Transaksi selectedTransaksi) {
        try {
            TransaksiController controller = (TransaksiController) switchView("./views/UbahTransaksi.fxml");
            controller.setSelectedTransaksi(selectedTransaksi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tambahTransaksi(ActionEvent event) {
        if (tanggalInput.getValue() == null) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Tanggal tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        if (akunInput.getValue() == null) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Akun tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        if (posisiAkunInput.getValue() == null) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Posisi Akun tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        if (nominalInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Nominal tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        try {
            Double.parseDouble(nominalInput.getText());
        } catch (NumberFormatException e) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Nominal harus angka");
            alt.showAndWait();
            return;
        }

        Transaksi transaksi = new Transaksi(
                tanggalInput.getValue(),
                akunInput.getValue().getId(),
                posisiAkunInput.getValue(),
                klienInput.getValue() == null ? -1 : klienInput.getValue().getId(),
                keteranganInput.getText(),
                Double.parseDouble(nominalInput.getText()));
        
        transaksi.setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());

        transaksi.tambah();

        try {
            switchView("./views/Transaksi.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ubahTransaksi(ActionEvent event) {
        if (tanggalInput.getValue() == null) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Tanggal tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        if (akunInput.getValue() == null) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Akun tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        if (posisiAkunInput.getValue() == null) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Posisi Akun tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        if (nominalInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Nominal tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        try {
            Double.parseDouble(nominalInput.getText());
        } catch (NumberFormatException e) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Nominal harus angka");
            alt.showAndWait();
            return;
        }

        selectedTransaksi.get().setTanggal(tanggalInput.getValue());
        selectedTransaksi.get().setIdAkun(akunInput.getValue().getId());
        selectedTransaksi.get().setPosisiAkun(posisiAkunInput.getValue());
        selectedTransaksi.get().setIdKlien(klienInput.getValue() == null ? -1 : klienInput.getValue().getId());
        selectedTransaksi.get().setKeterangan(keteranganInput.getText());
        selectedTransaksi.get().setNominal(Double.parseDouble(nominalInput.getText()));
        selectedTransaksi.get().setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());
        selectedTransaksi.get().ubah();

        try {
            switchView("./views/Transaksi.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hapusTransaksi(Transaksi selectedTransaksi) {
        if((getUser().role().getPermissionsFlag() & Permissions.MENGHAPUS_TRANSAKSI) == 0){
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Anda tidak memiliki akses untuk menghapus transaksi");
            alt.showAndWait();
            return;
        }
        selectedTransaksi.hapus();
        refresh();
    }
}