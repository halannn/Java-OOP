package com.kelompok1.models;

import java.util.List;

import com.kelompok1.types.ILaporanItem;
import com.kelompok1.types.JenisAkun;
import com.kelompok1.types.LabaRugiItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LabaRugi extends Laporan {

    @Override
    public ObservableList<ILaporanItem> processData() {
        ObservableList<ILaporanItem> processedData = FXCollections.observableArrayList();

        List<Transaksi> rawData = getData();

        for (Transaksi transaksi : rawData) {
            Akun akun = transaksi.akun();
            if (akun.getJenisAkun() == JenisAkun.Pengeluaran) {
                for(ILaporanItem item : processedData) {
                    LabaRugiItem labaRugiItem = (LabaRugiItem) item;
                    if(labaRugiItem.getNamaAkun().equals(akun.getNamaAkun())) {
                        labaRugiItem.setNominal(labaRugiItem.getNominal() + transaksi.getNominal());
                        break;
                    }
                }
                processedData.add(new LabaRugiItem("pengeluaran", akun.getNamaAkun(), transaksi.getNominal()));
            } else if (akun.getJenisAkun() == JenisAkun.Pendapatan) {
                for(ILaporanItem item : processedData) {
                    LabaRugiItem labaRugiItem = (LabaRugiItem) item;
                    if(labaRugiItem.getNamaAkun().equals(akun.getNamaAkun())) {
                        labaRugiItem.setNominal(labaRugiItem.getNominal() + transaksi.getNominal());
                        break;
                    }
                }
                processedData.add(new LabaRugiItem("pendapatan", akun.getNamaAkun(), transaksi.getNominal()));
            }
        }

        processedData.sort((a, b) -> {
            LabaRugiItem aItem = (LabaRugiItem) a;
            LabaRugiItem bItem = (LabaRugiItem) b;
            return !(aItem.getKelompok().equals(bItem.getKelompok())) ? 1 : 0;
        });

        return processedData;
    }

}
