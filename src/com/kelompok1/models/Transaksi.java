package com.kelompok1.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.kelompok1.DB;
import com.kelompok1.types.PosisiAkun;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Transaksi {
    private int id;
    private int idOwnPerusahaan;
    private LocalDate tanggal;
    private int idAkun;
    private PosisiAkun posisiAkun;
    private int idKlien;
    private String keterangan;
    private double nominal;

    public Transaksi(LocalDate tanggal, int idAkun, PosisiAkun posisiAkun, int idKlien, String keterangan,
            double nominal) {
        this.id = -1;
        this.idOwnPerusahaan = -1;
        this.tanggal = tanggal;
        this.idAkun = idAkun;
        this.posisiAkun = posisiAkun;
        this.idKlien = idKlien;
        this.keterangan = keterangan;
        this.nominal = nominal;
    }

    public Transaksi(LocalDate tanggal, int idAkun, PosisiAkun posisiAkun, String keterangan, double nominal) {
        this.id = -1;
        this.idOwnPerusahaan = -1;
        this.tanggal = tanggal;
        this.idAkun = idAkun;
        this.posisiAkun = posisiAkun;
        this.idKlien = -1;
        this.keterangan = keterangan;
        this.nominal = nominal;
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

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public int getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(int idAkun) {
        this.idAkun = idAkun;
    }

    public PosisiAkun getPosisiAkun() {
        return posisiAkun;
    }

    public void setPosisiAkun(PosisiAkun posisiAkun) {
        this.posisiAkun = posisiAkun;
    }

    public int getIdKlien() {
        return idKlien;
    }

    public void setIdKlien(int idKlien) {
        this.idKlien = idKlien;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        if (keterangan.length() > 255) {
            throw new IllegalArgumentException("Keterangan tidak boleh lebih dari 255 karakter");
        }
        this.keterangan = keterangan;
    }

    public double getNominal() {
        return nominal;
    }

    public void setNominal(double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("Saldo tidak boleh negatif");
        }
        this.nominal = saldo;
    }

    // public static ObservableList<Transaksi> getAll(QueryOptions options) {
    // ObservableList<Transaksi> transaksiRes = FXCollections.observableArrayList();

    // String stmString = "SELECT * FROM transaksi WHERE id_own_perusahaan = ?";
    // Optional<String> search = options.getSearch();
    // if (search.isPresent()) {
    // stmString += " AND (keterangan LIKE ? OR DATE_FORMAT(tanggal, '%d-%m-%Y')
    // LIKE ?)";
    // }
    // OptionalInt limit = options.getLimit();
    // if (limit.isPresent()) {
    // stmString += " LIMIT ?";
    // } else {
    // stmString += " LIMIT 50";
    // }
    // OptionalInt currentPage = options.getCurrentPage();
    // if (currentPage.isPresent()) {
    // stmString += " OFFSET ?";
    // }

    // try {
    // DB.loadJDBCDriver();
    // DB.connect();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // try {
    // PreparedStatement stm = DB.prepareStatement(stmString);
    // int paramIndex = 1;
    // stm.setInt(paramIndex, options.getIdOwnPerusahaan());
    // paramIndex += 1;
    // if (search.isPresent()) {
    // String processedSearch = "%" + search.get().replace("%", "\\%").replaceAll("
    // +", "%") + "%";
    // stm.setString(paramIndex, processedSearch);
    // paramIndex += 1;
    // stm.setString(paramIndex, processedSearch);
    // paramIndex += 1;
    // }
    // if (limit.isPresent()) {
    // stm.setInt(paramIndex, Math.max(limit.getAsInt(), 0));
    // paramIndex += 1;
    // }
    // if (currentPage.isPresent()) {
    // int limitVal = 50;
    // if (limit.isPresent()) {
    // limitVal = Math.max(limit.getAsInt(), 0);
    // }
    // stm.setInt(paramIndex, Math.max((currentPage.getAsInt() - 1), 0) * limitVal);
    // paramIndex += 1;
    // }
    // ResultSet rs = stm.executeQuery();
    // while (rs.next()) {
    // Transaksi transaksi = new Transaksi(rs.getDate("tanggal").toLocalDate(),
    // rs.getInt("id_akun"),
    // PosisiAkun.valueOf(rs.getString("posisi_akun")), rs.getInt("id_klien"),
    // rs.getString("keterangan"), rs.getDouble("nominal"));
    // transaksi.setId(rs.getInt("id"));
    // transaksi.setIdOwnPerusahaan(rs.getInt("id_own_perusahaan"));
    // transaksiRes.add(transaksi);
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // } finally {
    // try {
    // DB.disconnect();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    // return transaksiRes;
    // }

    // public static int getAllCount(QueryOptions options) {
    // int count = 0;

    // String stmString = "SELECT COUNT(*) FROM transaksi WHERE id_own_perusahaan =
    // ?";
    // Optional<String> search = options.getSearch();
    // if (search.isPresent()) {
    // stmString += " AND (keterangan LIKE ? OR DATE_FORMAT(tanggal, '%d-%m-%Y')
    // LIKE ?)";
    // }

    // try {
    // DB.loadJDBCDriver();
    // DB.connect();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // try {
    // PreparedStatement stm = DB.prepareStatement(stmString);
    // int paramIndex = 1;
    // stm.setInt(paramIndex, options.getIdOwnPerusahaan());
    // paramIndex += 1;
    // if (search.isPresent()) {
    // String processedSearch = "%" + search.get().replace("%", "\\%").replaceAll("
    // +", "%") + "%";
    // stm.setString(paramIndex, processedSearch);
    // paramIndex += 1;
    // stm.setString(paramIndex, processedSearch);
    // paramIndex += 1;
    // }
    // ResultSet rs = stm.executeQuery();
    // while (rs.next()) {
    // count = rs.getInt(1);
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // } finally {
    // try {
    // DB.disconnect();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    // return count;
    // }

    public static ObservableList<Transaksi> getRanged(int idOwnPerusahaan, LocalDate from, LocalDate to) {
        ObservableList<Transaksi> transaksiRes = FXCollections.observableArrayList();

        String stmString = "SELECT * FROM transaksi WHERE id_own_perusahaan = ? AND tanggal BETWEEN ? AND ? ORDER BY tanggal ASC";
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stm = DB.prepareStatement(stmString);
            stm.setInt(1, idOwnPerusahaan);
            stm.setDate(2, java.sql.Date.valueOf(from));
            stm.setDate(3, java.sql.Date.valueOf(to));
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Transaksi transaksi = new Transaksi(rs.getDate("tanggal").toLocalDate(), rs.getInt("id_akun"),
                        PosisiAkun.fromString(rs.getString("posisi_akun")), rs.getInt("id_klien"),
                        rs.getString("keterangan"), rs.getDouble("nominal"));
                transaksi.setId(rs.getInt("id"));
                transaksi.setIdOwnPerusahaan(idOwnPerusahaan);
                transaksiRes.add(transaksi);
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
        return transaksiRes;
    }

    // Method untuk menambahkan transaksi ke database
    public void tambah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement(
                    "INSERT INTO transaksi (id_own_perusahaan, tanggal, id_akun, posisi_akun, id_klien, keterangan, nominal) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stm.setInt(1, this.idOwnPerusahaan);
            stm.setDate(2, java.sql.Date.valueOf(this.tanggal));
            stm.setInt(3, this.idAkun);
            stm.setString(4, this.posisiAkun.toString());
            if (this.idKlien == -1) {
                stm.setNull(5, java.sql.Types.INTEGER);
            } else {
                stm.setInt(5, this.idKlien);
            }
            stm.setString(6, this.keterangan);
            stm.setDouble(7, this.nominal);
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

    // Method untuk menghapus transaksi dari database
    public void hapus() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("DELETE FROM transaksi WHERE id = ?");
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

    // Method untuk mengubah transaksi di database
    public void ubah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement(
                    "UPDATE transaksi SET tanggal = ?, id_akun = ?, posisi_akun = ?, id_klien = ?, keterangan = ?, nominal = ? WHERE id = ?");
            stm.setDate(1, java.sql.Date.valueOf(this.tanggal));
            stm.setInt(2, this.idAkun);
            stm.setString(3, this.posisiAkun.toString());
            if (this.idKlien == -1) {
                stm.setNull(4, java.sql.Types.INTEGER);
            } else {
                stm.setInt(4, this.idKlien);
            }
            stm.setString(5, this.keterangan);
            stm.setDouble(6, this.nominal);
            stm.setInt(7, this.id);
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

    // Method untuk mengambil data akun berdasarkan idAkun
    public Akun akun() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("SELECT * FROM akun WHERE id = ?");
            stm.setInt(1, this.idAkun);
            ResultSet rs = stm.executeQuery();
            Akun relatedAkun = null;
            if (rs.next()) {
                relatedAkun = new Akun(rs.getString("nama_akun"),
                        Akun.getJenisAkunFromString(rs.getString("jenis_akun")));
                relatedAkun.setId(rs.getInt("id"));
            }
            return relatedAkun;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Method untuk mengambil data klien berdasarkan idKlien
    public Klien klien() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (idKlien == -1) {
                return null;
            }
            PreparedStatement stm = DB.prepareStatement("SELECT * FROM klien WHERE id = ?");
            stm.setInt(1, this.idKlien);
            ResultSet rs = stm.executeQuery();
            Klien relatedKlien = null;
            if (rs.next()) {
                relatedKlien = new Klien(rs.getString("nama"), rs.getString("no_telp"), rs.getString("email"),
                        rs.getString("perusahaan_asal"), rs.getString("alamat"));
                relatedKlien.setId(rs.getInt("id"));
            }
            return relatedKlien;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
