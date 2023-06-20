package com.kelompok1.models;

import java.util.List;

import com.kelompok1.types.ILaporanItem;
import com.kelompok1.types.PosisiAkun;
import com.kelompok1.types.TrialBalanceItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrialBalance extends Laporan {

    @Override
    public ObservableList<ILaporanItem> processData() {
        ObservableList<ILaporanItem> processedData = FXCollections.observableArrayList();

        List<Transaksi> rawData = getData();

        for(Transaksi transaksi: rawData){
            Akun akun = transaksi.akun();
            double debit = 0;
            if(transaksi.getPosisiAkun()==PosisiAkun.Debit) {
                debit = transaksi.getNominal();
            }
            double kredit = 0;
            if(transaksi.getPosisiAkun()==PosisiAkun.Kredit) {
                kredit = transaksi.getNominal();
            }
            processedData.add(new TrialBalanceItem(akun.getNamaAkun(), debit, kredit));
        }

        return processedData;
    }

}
