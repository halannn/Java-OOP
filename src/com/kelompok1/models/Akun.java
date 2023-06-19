package com.kelompok1.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.OptionalInt;

import com.kelompok1.DB;
import com.kelompok1.types.JenisAkun;
import com.kelompok1.types.QueryOptions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Akun {
    private int id;
    private int idOwnPerusahaan;
    private String namaAkun;
    private JenisAkun jenisAkun;

    public Akun(String namaAkun, JenisAkun jenisAkun) {
        this.id = -1;
        this.idOwnPerusahaan = -1;
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

    public int getIdOwnPerusahaan() {
        return idOwnPerusahaan;
    }

    public void setIdOwnPerusahaan(int idOwnPerusahaan) {
        this.idOwnPerusahaan = idOwnPerusahaan;
    }

    public static JenisAkun getJenisAkunFromString(String jenisString) {
        return JenisAkun.fromString(jenisString);
    }

    public static String jenisAkunToString(JenisAkun jenisAkun) {
        return jenisAkun.toString();
    }

    public static ObservableList<Akun> getAll(QueryOptions options) {
        ObservableList<Akun> akunRes = FXCollections.observableArrayList();

        String stmString = "SELECT * FROM akun WHERE id_own_perusahaan = ?";
        Optional<String> search = options.getSearch();
        if (search.isPresent()) {
            stmString += " AND (nama_akun LIKE ? OR jenis_akun LIKE ?)";
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
            }
            if (limit.isPresent()) {
                stm.setInt(paramIndex, Math.max(limit.getAsInt(),0));
                paramIndex += 1;
            }
            if (currentPage.isPresent()) {
                int limitVal = 50;
                if (limit.isPresent()) {
                    limitVal = Math.max(limit.getAsInt(),0);
                }
                stm.setInt(paramIndex, Math.max((currentPage.getAsInt() - 1),0) * limitVal);
                paramIndex += 1;
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Akun akun = new Akun(
                        rs.getString("nama_akun"),
                        getJenisAkunFromString(rs.getString("jenis_akun")));
                akun.setId(rs.getInt("id"));
                akun.setIdOwnPerusahaan(rs.getInt("id_own_perusahaan"));
                akunRes.add(akun);
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

        return akunRes;
    }

    public static int getAllCount(QueryOptions options){
        int count = 0;

        String stmString = "SELECT COUNT(*) FROM akun WHERE id_own_perusahaan = ?";
        Optional<String> search = options.getSearch();
        if (search.isPresent()) {
            stmString += " AND (nama_akun LIKE ? OR jenis_akun LIKE ?)";
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
                    .prepareStatement("INSERT INTO akun (id_own_perusahaan, nama_akun, jenis_akun) VALUES (?, ?, ?)");
            stm.setInt(1, this.idOwnPerusahaan);
            stm.setString(2, this.namaAkun);
            stm.setString(3, this.jenisAkun.toString());
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
                    .prepareStatement("UPDATE akun SET nama_akun = ?, jenis_akun = ? WHERE id = ?");
            stm.setString(1, this.namaAkun);
            stm.setString(2, this.jenisAkun.toString());
            stm.setInt(3, this.id);
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
