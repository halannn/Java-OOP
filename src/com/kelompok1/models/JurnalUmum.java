package com.kelompok1.models;

import java.util.List;

import com.kelompok1.types.JurnalUmumItem;
import com.kelompok1.types.ILaporanItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JurnalUmum extends Laporan {

    @Override
    public ObservableList<ILaporanItem> processData() {
        ObservableList<ILaporanItem> processedData = FXCollections.observableArrayList();

        List<Transaksi> rawData = this.getData();

        for (Transaksi transaksi : rawData) {
            Akun akun = transaksi.akun();
            double debit = 0;
            if(transaksi.getPosisiAkun()==PosisiAkun.Debit) {
                debit = transaksi.getNominal();
            }
            double kredit = 0;
            if(transaksi.getPosisiAkun()==PosisiAkun.Kredit) {
                kredit = transaksi.getNominal();
            }
            processedData.add(new JurnalUmumItem(transaksi.getTanggal().toString(), akun.getNamaAkun(), transaksi.getKeterangan(), debit, kredit));
        }

        return processedData;
    }
}
