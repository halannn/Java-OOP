package com.kelompok1.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import com.kelompok1.DB;
import com.kelompok1.types.PosisiAkun;

public class Transaksi {
    private int id;
    private LocalDate tanggal;
    private int idAkun;
    private PosisiAkun posisiAkun;
    private int idKlien;
    private String keterangan;
    private double nominal;

    public Transaksi(LocalDate tanggal, int idAkun, PosisiAkun posisiAkun, int idKlien, String keterangan, double nominal) {
        this.id = -1;
        this.tanggal = tanggal;
        this.idAkun = idAkun;
        this.posisiAkun = posisiAkun;
        this.idKlien = idKlien;
        this.keterangan = keterangan;
        this.nominal = nominal;
    }

    public Transaksi(LocalDate tanggal, int idAkun, PosisiAkun posisiAkun, String keterangan, double nominal) {
        this.id = -1;
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
                    "INSERT INTO transaksi (tanggal, id_akun, posisi, id_klien, keterangan, nominal) VALUES (?, ?, ?, ?, ?)");
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
                    "UPDATE transaksi SET tanggal = ?, id_akun = ?, posisi = ?, id_klien = ?, keterangan = ?, nominal = ? WHERE id = ?");
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
    public Akun akun(){
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
                relatedAkun = new Akun(rs.getString("nama_akun"), Akun.getJenisAkunFromString(rs.getString("tipe_akun")));
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
                        rs.getString("perusahaan"), rs.getString("alamat"));
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
