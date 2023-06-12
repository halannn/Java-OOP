package com.kelompok1.models;

import java.sql.PreparedStatement;

import com.kelompok1.DB;

enum JenisAkun {
    Aset,
    Modal,
    Pengeluaran,
    Pendapatan,
    Hutang,
    Piutang;

    @Override
    public String toString() {
        switch (this) {
            case Aset:
                return "aset";
            case Modal:
                return "modal";
            case Pengeluaran:
                return "pengeluaran";
            case Pendapatan:
                return "pendapatan";
            case Hutang:
                return "hutang";
            case Piutang:
                return "piutang";
            default:
                return "unknown";
        }
    }

    public static JenisAkun fromString(String jenisString) {
        switch (jenisString) {
            case "aset":
                return Aset;
            case "modal":
                return Modal;
            case "pengeluaran":
                return Pengeluaran;
            case "pendapatan":
                return Pendapatan;
            case "hutang":
                return Hutang;
            case "piutang":
                return Piutang;
            default:
                return null;
        }
    }
}

public class Akun {
    private int id;
    private String namaAkun;
    private JenisAkun jenisAkun;

    public Akun(String namaAkun, JenisAkun jenisAkun) {
        this.id = -1;
        this.namaAkun = namaAkun;
        this.jenisAkun = jenisAkun;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public JenisAkun getJenisAkun() {
        return jenisAkun;
    }

    public void setJenisAkun(JenisAkun tipeAkun) {
        this.jenisAkun = tipeAkun;
    }

    public static JenisAkun getJenisAkunFromString(String jenisString) {
        return JenisAkun.fromString(jenisString);
    }

    // Method untuk menambahkan akun baru ke database
    public void tambah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB
                    .prepareStatement("INSERT INTO akun (nama_akun, jenis_akun, posisi_akun) VALUES (?, ?, ?)");
            stm.setString(1, this.namaAkun);
            stm.setString(2, this.jenisAkun.toString());
            stm.executeUpdate();
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

    // Method untuk menghapus akun dari database
    public void hapus() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("DELETE FROM akun WHERE id = ?");
            stm.setInt(1, this.id);
            stm.executeUpdate();
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

    // Method untuk mengubah akun di database
    public void ubah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB
                    .prepareStatement("UPDATE akun SET nama_akun = ?, jenis_akun = ?, posisi_akun = ? WHERE id = ?");
            stm.setString(1, this.namaAkun);
            stm.setString(2, this.jenisAkun.toString());
            stm.setInt(4, this.id);
            stm.executeUpdate();
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
}
