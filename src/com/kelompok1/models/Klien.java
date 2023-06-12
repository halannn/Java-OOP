package com.kelompok1.models;

import java.sql.PreparedStatement;

import com.kelompok1.DB;

public class Klien {
    private int id;
    private String nama;
    private String noTelp;
    private String email;
    private String perusahaan;
    private String alamat;

    public Klien(String nama, String noTelp, String email, String perusahaan, String alamat) {
        this.id = -1;
        this.nama = nama;
        this.noTelp = noTelp;
        this.email = email;
        this.perusahaan = perusahaan;
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // Method untuk menambahkan klien baru ke database
    public void tambah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement(
                    "INSERT INTO klien (nama, no_telp, email, perusahaan, alamat) VALUES (?, ?, ?, ?, ?)");
            stm.setString(1, this.nama);
            stm.setString(2, this.noTelp);
            stm.setString(3, this.email);
            stm.setString(4, this.perusahaan);
            stm.setString(5, this.alamat);
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

    // Method untuk menghapus klien dari database
    public void hapus() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("DELETE FROM klien WHERE id = ?");
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

    // Method untuk mengubah data klien di database
    public void ubah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement(
                    "UPDATE klien SET nama = ?, no_telp = ?, email = ?, perusahaan = ?, alamat = ? WHERE id = ?");
            stm.setString(1, this.nama);
            stm.setString(2, this.noTelp);
            stm.setString(3, this.email);
            stm.setString(4, this.perusahaan);
            stm.setString(5, this.alamat);
            stm.setInt(6, this.id);
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
