package com.kelompok1.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.kelompok1.DB;
import com.kelompok1.types.ILaporanItem;
import com.kelompok1.types.PosisiAkun;

import javafx.collections.ObservableList;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;

public abstract class Laporan {
    private List<Transaksi> data;

    public Laporan() {
        this.data = new ArrayList<Transaksi>();
    }

    public void queryData(LocalDate from, LocalDate to) {
        data.clear();
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("SELECT * FROM transaksi WHERE tanggal BETWEEN ? AND ? ORDER BY tanggal ASC");
            stm.setDate(1, java.sql.Date.valueOf(from));
            stm.setDate(2, java.sql.Date.valueOf(to));
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Transaksi transaksi = new Transaksi(rs.getObject("tanggal", LocalDate.class), rs.getInt("id_akun"),
                        PosisiAkun.fromString(rs.getString("posisi_akun")), rs.getInt("id_klien"),
                        rs.getString("keterangan"), rs.getDouble("nominal"));
                transaksi.setId(rs.getInt("id"));
                data.add(transaksi);
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
    }

    public List<Transaksi> getData() {
        return data;
    }

    public abstract ObservableList<ILaporanItem> processData();
}
