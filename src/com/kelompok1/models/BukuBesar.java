package com.kelompok1.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.kelompok1.DB;
import com.kelompok1.types.BukuBesarItem;
import com.kelompok1.types.ILaporanItem;
import com.kelompok1.types.PosisiAkun;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BukuBesar extends Laporan {

    private double hitungSaldoSebelum(LocalDate tanggal, int idAkun) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement(
                    "SELECT SUM(IF(transaksi.posisi_akun='kredit',-1,1)*nominal) FROM transaksi WHERE tanggal < ? AND id_akun = ?");
            stm.setDate(1, java.sql.Date.valueOf(tanggal));
            stm.setInt(2, idAkun);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public ObservableList<ILaporanItem> processData() {
        ObservableList<ILaporanItem> processedData = FXCollections.observableArrayList();

        List<Transaksi> rawData = getData();
        rawData.sort((a, b) -> a.getIdAkun() - b.getIdAkun());

        for (Transaksi transaksi : rawData) {
            Akun akun = transaksi.akun();
            double debit = 0;
            if (transaksi.getPosisiAkun() == PosisiAkun.Debit) {
                debit = transaksi.getNominal();
            }
            double kredit = 0;
            if (transaksi.getPosisiAkun() == PosisiAkun.Kredit) {
                kredit = transaksi.getNominal();
            }
            Double saldo = hitungSaldoSebelum(transaksi.getTanggal(), transaksi.getIdAkun()) + debit - kredit;
            processedData.add(new BukuBesarItem(transaksi.getTanggal().toString(), akun.getNamaAkun(),
                    transaksi.getKeterangan(), debit, kredit, saldo));
        }

        return processedData;
    }

}
