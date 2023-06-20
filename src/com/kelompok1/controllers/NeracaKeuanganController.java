package com.kelompok1.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.kelompok1.models.NeracaKeuangan;
import com.kelompok1.models.User;
import com.kelompok1.types.ILaporanItem;
import com.kelompok1.types.KelompokNeraca;
import com.kelompok1.types.NeracaKeuanganItem;

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

public class NeracaKeuanganController extends BaseController implements Initializable {
    
    @FXML
    private Button goBtn;

    @FXML
    private DatePicker fromInput;

    @FXML
    private DatePicker toInput;

    @FXML
    private TableView<NeracaKeuanganItem> neracaKeuanganTable;

    @FXML
    private TableColumn<NeracaKeuanganItem,KelompokNeraca> kelompokCol;

    @FXML
    private TableColumn<NeracaKeuanganItem,String> akunCol;

    @FXML
    private TableColumn<NeracaKeuanganItem,Double> saldoCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kelompokCol.setCellValueFactory(new PropertyValueFactory<NeracaKeuanganItem,KelompokNeraca>("kelompok"));
        akunCol.setCellValueFactory(new PropertyValueFactory<NeracaKeuanganItem,String>("namaAkun"));
        saldoCol.setCellValueFactory(new PropertyValueFactory<NeracaKeuanganItem,Double>("nominal"));

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
        NeracaKeuangan nk = new NeracaKeuangan();
        nk.queryData(fromInput.getValue(), toInput.getValue());
        ObservableList<ILaporanItem> dataUncast = nk.processData();
        ObservableList<NeracaKeuanganItem> data = FXCollections.observableArrayList();
        for(ILaporanItem item : dataUncast){
            data.add((NeracaKeuanganItem)item);
        }
        neracaKeuanganTable.setItems(data);
    }

}
