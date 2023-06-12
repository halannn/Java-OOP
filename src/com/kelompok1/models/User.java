package com.kelompok1.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kelompok1.DB;

enum UserStatus {
    Aktif,
    Nonaktif;

    @Override
    public String toString() {
        switch (this) {
            case Aktif:
                return "aktif";
            case Nonaktif:
                return "nonaktif";
            default:
                return "unknown";
        }
    }

    public static UserStatus fromString(String statusString) {
        switch (statusString) {
            case "aktif":
                return Aktif;
            case "nonaktif":
                return Nonaktif;
            default:
                return null;
        }
    }
}

public class User {
    private int id;
    private String username;
    private String password;
    private int idPerusahaan;
    private int idRole;
    private UserStatus status;

    public User(String username, String password, int idPerusahaan, int idRole, UserStatus status) {
        this.id = -1;
        this.username = username;
        this.password = password;
        this.idPerusahaan = idPerusahaan;
        this.idRole = idRole;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdPerusahaan() {
        return idPerusahaan;
    }

    public void setIdPerusahaan(int idPerusahaan) {
        this.idPerusahaan = idPerusahaan;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    // Method untuk mengambil data perusahaan berdasarkan idPerusahaan
    public Perusahaan perusahaan() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("SELECT * FROM perusahaan WHERE id = ?");
            stm.setInt(1, this.idPerusahaan);
            Perusahaan relatedPerusahaan = null;
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                relatedPerusahaan = new Perusahaan(rs.getString("nama"), rs.getString("alamat"),
                        rs.getString("no_telp"), rs.getString("email"));
                relatedPerusahaan.setId(rs.getInt("id"));
            }
            return relatedPerusahaan;
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

    // Method untuk mengambil data role berdasarkan idRole
    public Role role() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("SELECT * FROM role WHERE id = ?");
            stm.setInt(1, this.idRole);
            Role relatedRole = null;
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                relatedRole = new Role(rs.getString("nama_role"), rs.getInt("permissions_flag"));
                relatedRole.setId(rs.getInt("id"));
            }
            return relatedRole;
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
