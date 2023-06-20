package com.kelompok1.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.OptionalInt;

import com.kelompok1.DB;
import com.kelompok1.types.QueryOptions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Klien {
    private int id;
    private int idOwnPerusahaan;
    private String nama;
    private String noTelp;
    private String email;
    private String perusahaan;
    private String alamat;

    public Klien(String nama, String noTelp, String email, String perusahaan, String alamat) {
        this.id = -1;
        this.idOwnPerusahaan = -1;
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

    public int getIdOwnPerusahaan() {
        return idOwnPerusahaan;
    }

    public void setIdOwnPerusahaan(int idOwnPerusahaan) {
        this.idOwnPerusahaan = idOwnPerusahaan;
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

    public static ObservableList<Klien> getAll(QueryOptions options){
        ObservableList<Klien> klienRes = FXCollections.observableArrayList();

        String stmString = "SELECT * FROM klien WHERE id_own_perusahaan = ?";
        Optional<String> search = options.getSearch();
        if (search.isPresent()) {
            stmString += " AND (nama LIKE ? OR no_telp LIKE ? OR email LIKE ? OR perusahaan_asal LIKE ? OR alamat LIKE ?)";
        }
        OptionalInt limit = options.getLimit();
        if (limit.isPresent()) {
            stmString += " LIMIT ?";
        } else {
            stmString += " LIMIT 50";
        }
        OptionalInt currentPage = options.getCurrentPage();
        if (currentPage.isPresent()) {
            stmString += " OFFSET ?";
        }

        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stm = DB.prepareStatement(stmString);
            int paramIndex = 1;
            stm.setInt(paramIndex, options.getIdOwnPerusahaan());
            paramIndex += 1;
            if (search.isPresent()) {
                String processedSearch = "%" + search.get().replace("%", "\\%").replaceAll(" +", "%") + "%";
                stm.setString(paramIndex, processedSearch);
                paramIndex += 1;
                stm.setString(paramIndex, processedSearch);
                paramIndex += 1;
                stm.setString(paramIndex, processedSearch);
                paramIndex += 1;
                stm.setString(paramIndex, processedSearch);
                paramIndex += 1;
                stm.setString(paramIndex, processedSearch);
                paramIndex += 1;
            }
            if (limit.isPresent()) {
                stm.setInt(paramIndex, Math.max(limit.getAsInt(),0));
                paramIndex += 1;
            }
            if (currentPage.isPresent()) {
                int limitVal = 50;
                if (limit.isPresent()) {
                    limitVal = Math.max(limit.getAsInt(), 0);
                }
                stm.setInt(paramIndex, Math.max((currentPage.getAsInt() - 1), 0) * limitVal);
                paramIndex += 1;
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Klien klien = new Klien(
                        rs.getString("nama"),
                        rs.getString("no_telp"),
                        rs.getString("email"),
                        rs.getString("perusahaan_asal"),
                        rs.getString("alamat"));
                klien.setId(rs.getInt("id"));
                klien.setIdOwnPerusahaan(rs.getInt("id_own_perusahaan"));
                klienRes.add(klien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return klienRes;
    }

    public static int getAllCount(QueryOptions options){
        int count = 0;

        String stmString = "SELECT COUNT(*) FROM klien WHERE id_own_perusahaan = ?";
        Optional<String> search = options.getSearch();
        if (search.isPresent()) {
            stmString += " AND (nama LIKE ? OR no_telp LIKE ? OR email LIKE ? OR perusahaan_asal LIKE ? OR alamat LIKE ?)";
        }

        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stm = DB.prepareStatement(stmString);
            int paramIndex = 1;
            stm.setInt(paramIndex, options.getIdOwnPerusahaan());
            paramIndex += 1;
            if (search.isPresent()) {
                String processedSearch = "%" + search.get().replace("%", "\\%").replaceAll(" +", "%") + "%";
                stm.setString(paramIndex, processedSearch);
                paramIndex += 1;
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count;
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
                    "INSERT INTO klien (id_own_perusahaan, nama, no_telp, email, perusahaan_asal, alamat) VALUES (?, ?, ?, ?, ?, ?)");
            stm.setInt(1, this.idOwnPerusahaan);
            stm.setString(2, this.nama);
            stm.setString(3, this.noTelp);
            stm.setString(4, this.email);
            stm.setString(5, this.perusahaan);
            stm.setString(6, this.alamat);
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
                    "UPDATE klien SET nama = ?, no_telp = ?, email = ?, perusahaan_asal = ?, alamat = ? WHERE id = ?");
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
