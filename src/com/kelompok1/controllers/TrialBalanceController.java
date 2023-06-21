package com.kelompok1.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.kelompok1.models.TrialBalance;
import com.kelompok1.models.User;
import com.kelompok1.types.ILaporanItem;
import com.kelompok1.types.TrialBalanceItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TrialBalanceController extends BaseController {

    @FXML
    private Button goBtn;

    @FXML
    private DatePicker fromInput;

    @FXML
    private DatePicker toInput;

    @FXML
    private TableView<TrialBalanceItem> trialBalanceTable;

    @FXML
    private TableColumn<TrialBalanceItem, String> akunCol;

    @FXML
    private TableColumn<TrialBalanceItem, Double> debitCol;

    @FXML
    private TableColumn<TrialBalanceItem, Double> kreditCol;

    @FXML
    private Label debitTotal;

    @FXML
    private Label kreditTotal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        akunCol.setCellValueFactory(new PropertyValueFactory<TrialBalanceItem, String>("namaAkun"));
        debitCol.setCellValueFactory(new PropertyValueFactory<TrialBalanceItem, Double>("debit"));
        kreditCol.setCellValueFactory(new PropertyValueFactory<TrialBalanceItem, Double>("kredit"));

        userProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if (newValue == null) {
                    userProperty().removeListener(this);
                }
                refresh();
            }
        });

        goBtn.setOnAction(e -> {
            refresh();
        });
    }

    public void refresh() {
        if (fromInput.getValue() == null) {
            fromInput.setValue(LocalDate.now());
        }
        if (toInput.getValue() == null) {
            toInput.setValue(LocalDate.now());
        }
        TrialBalance tb = new TrialBalance();
        tb.queryData(fromInput.getValue(), toInput.getValue());
        ObservableList<ILaporanItem> dataUncast = tb.processData();
        ObservableList<TrialBalanceItem> data = FXCollections.observableArrayList();
        double debitTotalValue = 0;
        double kreditTotalValue = 0;
        for (ILaporanItem item : dataUncast) {
            TrialBalanceItem castedItem = (TrialBalanceItem) item;
            data.add(castedItem);
            debitTotalValue += castedItem.getDebit();
            kreditTotalValue += castedItem.getKredit();
        }
        trialBalanceTable.setItems(data);
        debitTotal.setText("Rp." + String.format("%.2f", debitTotalValue));
        kreditTotal.setText("Rp." + String.format("%.2f", kreditTotalValue));
    }
}
