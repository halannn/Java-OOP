package com.kelompok1.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.kelompok1.models.BukuBesar;
import com.kelompok1.models.User;
import com.kelompok1.types.BukuBesarItem;
import com.kelompok1.types.ILaporanItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class BukuBesarController extends BaseController {
    

    @FXML
    private Button goBtn;

    @FXML
    private DatePicker fromInput;

    @FXML
    private DatePicker toInput;

    @FXML
    private TableView<BukuBesarItem> bukuBesarTable;

    @FXML
    private TableColumn<BukuBesarItem,String> tanggalCol;

    @FXML
    private TableColumn<BukuBesarItem,String> akunCol;

    @FXML
    private TableColumn<BukuBesarItem,String> keteranganCol;

    @FXML
    private TableColumn<BukuBesarItem,Double> debitCol;

    @FXML
    private TableColumn<BukuBesarItem,Double> kreditCol;

    @FXML
    private TableColumn<BukuBesarItem,Double> saldoCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        tanggalCol.setCellValueFactory(new PropertyValueFactory<BukuBesarItem,String>("tanggal"));
        akunCol.setCellValueFactory(new PropertyValueFactory<BukuBesarItem,String>("namaAkun"));
        akunCol.setCellFactory(new Callback<TableColumn<BukuBesarItem,String>,TableCell<BukuBesarItem,String>>() {
            private String prevNamaAkun = null;
            
            @Override
            public TableCell<BukuBesarItem, String> call(TableColumn<BukuBesarItem, String> arg0) {
                return new TableCell<BukuBesarItem,String>(){

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            if(item.equals(prevNamaAkun)){
                                setText("");
                            }else{
                                setText(item);
                            }
                        }else{
                            setText("");
                        }
                        prevNamaAkun = item;
                    }
                };
            }
            
        });
        keteranganCol.setCellValueFactory(new PropertyValueFactory<BukuBesarItem,String>("keterangan"));
        debitCol.setCellValueFactory(new PropertyValueFactory<BukuBesarItem,Double>("debit"));
        kreditCol.setCellValueFactory(new PropertyValueFactory<BukuBesarItem,Double>("kredit"));
        saldoCol.setCellValueFactory(new PropertyValueFactory<BukuBesarItem,Double>("saldo"));

        userProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {                
                if(newValue == null) {
                    userProperty().removeListener(this);
                }
                refresh();
            }
        });

        goBtn.setOnAction(e->{
            refresh();
        });
    }

    public void refresh(){
        if(fromInput.getValue()==null){
            fromInput.setValue(LocalDate.now());
        }
        if(toInput.getValue()==null){
            toInput.setValue(LocalDate.now());
        }
        BukuBesar bb = new BukuBesar();
        bb.queryData(fromInput.getValue(), toInput.getValue());
        ObservableList<ILaporanItem> dataUncast = bb.processData();
        ObservableList<BukuBesarItem> data = FXCollections.observableArrayList();
        for(ILaporanItem item : dataUncast){
            BukuBesarItem castedItem = (BukuBesarItem) item;
            data.add(castedItem);
        }
        bukuBesarTable.setItems(data);
    }
    
}
