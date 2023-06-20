package com.kelompok1.models;

import java.util.List;

import com.kelompok1.types.ILaporanItem;
import com.kelompok1.types.JenisAkun;
import com.kelompok1.types.NeracaKeuanganItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NeracaKeuangan extends Laporan {

    @Override
    public ObservableList<ILaporanItem> processData() {
        ObservableList<ILaporanItem> processedData = FXCollections.observableArrayList();

        List<Transaksi> rawData = getData();

        for (Transaksi transaksi : rawData) {
            Akun akun = transaksi.akun();
            if (akun.getJenisAkun() == JenisAkun.Aset || akun.getJenisAkun() == JenisAkun.Piutang) {
                for (ILaporanItem item : processedData) {
                    NeracaKeuanganItem neracaKeuanganItem = (NeracaKeuanganItem) item;
                    if (neracaKeuanganItem.getNamaAkun().equals(akun.getNamaAkun())) {
                        neracaKeuanganItem.setNominal(neracaKeuanganItem.getNominal() + transaksi.getNominal());
                        break;
                    }
                }
                processedData.add(new NeracaKeuanganItem("aktiva", akun.getNamaAkun(), transaksi.getNominal()));
            } else if (akun.getJenisAkun() == JenisAkun.Modal || akun.getJenisAkun() == JenisAkun.Hutang) {
                for (ILaporanItem item : processedData) {
                    NeracaKeuanganItem neracaKeuanganItem = (NeracaKeuanganItem) item;
                    if (neracaKeuanganItem.getNamaAkun().equals(akun.getNamaAkun())) {
                        neracaKeuanganItem.setNominal(neracaKeuanganItem.getNominal() + transaksi.getNominal());
                        break;
                    }
                }
                processedData.add(new NeracaKeuanganItem("pasiva", akun.getNamaAkun(), transaksi.getNominal()));
            }
        }

        return processedData;
    }

}
