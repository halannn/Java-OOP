package com.kelompok1.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import com.kelompok1.DB;
import com.kelompok1.types.BukuBesarItem;
import com.kelompok1.types.ILaporanItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BukuBesar extends Laporan {

    private double hitungSaldoSebelum(Date tanggal) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement(
                    "SELECT SUM(IF(transaksi.posisi='kredit',1,-1)*nominal) FROM transaksi WHERE tanggal < ?");
            stm.setDate(1, new java.sql.Date(tanggal.getTime()));
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
        double saldo = 0;

        int tridx = 0;
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
            if (tridx++ == 0) {
                saldo = hitungSaldoSebelum(transaksi.getTanggal()) + debit - kredit;
            } else {
                saldo = saldo + debit - kredit;
            }
            processedData.add(new BukuBesarItem(transaksi.getTanggal().toString(), akun.getNamaAkun(),
                    transaksi.getKeterangan(), debit, kredit, saldo));
        }

        return processedData;
    }

}
