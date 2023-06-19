package com.kelompok1.models;

import java.util.List;

import com.kelompok1.types.ILaporanItem;
import com.kelompok1.types.JenisAkun;
import com.kelompok1.types.NeracaItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Neraca extends Laporan {

    @Override
    public ObservableList<ILaporanItem> processData() {
        ObservableList<ILaporanItem> processedData = FXCollections.observableArrayList();

        List<Transaksi> rawData = getData();

        for(Transaksi transaksi: rawData){
            Akun akun = transaksi.akun();
            if(akun.getJenisAkun()==JenisAkun.Aset||akun.getJenisAkun()==JenisAkun.Piutang){
                processedData.add(new NeracaItem("aktiva", akun.getNamaAkun(), transaksi.getNominal()));
            }else if(akun.getJenisAkun()==JenisAkun.Modal||akun.getJenisAkun()==JenisAkun.Hutang){
                processedData.add(new NeracaItem("pasiva", akun.getNamaAkun(), transaksi.getNominal()));
            }
        }

        return processedData;
    }
    
}
