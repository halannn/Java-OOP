package com.kelompok1.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.kelompok1.models.JurnalUmum;
import com.kelompok1.models.User;
import com.kelompok1.types.ILaporanItem;
import com.kelompok1.types.JurnalUmumItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class JurnalUmumController extends BaseController implements Initializable {

    @FXML
    private Button goBtn;

    @FXML
    private DatePicker fromInput;

    @FXML
    private DatePicker toInput;

    @FXML
    private TableView<JurnalUmumItem> jurnalUmumTable;

    @FXML
    private TableColumn<JurnalUmumItem,String> tanggalCol;

    @FXML
    private TableColumn<JurnalUmumItem,String> akunCol;

    @FXML
    private TableColumn<JurnalUmumItem,String> keteranganCol;

    @FXML
    private TableColumn<JurnalUmumItem,Double> debitCol;

    @FXML
    private TableColumn<JurnalUmumItem,Double> kreditCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tanggalCol.setCellValueFactory(new PropertyValueFactory<JurnalUmumItem,String>("tanggal"));
        akunCol.setCellValueFactory(new PropertyValueFactory<JurnalUmumItem,String>("namaAkun"));
        keteranganCol.setCellValueFactory(new PropertyValueFactory<JurnalUmumItem,String>("keterangan"));
        debitCol.setCellValueFactory(new PropertyValueFactory<JurnalUmumItem,Double>("debit"));
        kreditCol.setCellValueFactory(new PropertyValueFactory<JurnalUmumItem,Double>("kredit"));

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
        JurnalUmum ju = new JurnalUmum();
        ju.queryData(fromInput.getValue(), toInput.getValue());
        ObservableList<ILaporanItem> dataUncast = ju.processData();
        ObservableList<JurnalUmumItem> data = FXCollections.observableArrayList();
        for(ILaporanItem item : dataUncast){
            data.add((JurnalUmumItem)item);
        }
        jurnalUmumTable.setItems(data);
    }
    
}
