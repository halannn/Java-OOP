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

public class Role {
    private int id;
    private int idOwnPerusahaan;
    private String namaRole;
    private int permissionsFlag;

    public Role(String namaRole, int permissionsFlag) {
        this.id = -1;
        this.idOwnPerusahaan = -1;
        this.namaRole = namaRole;
        this.permissionsFlag = permissionsFlag;
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

    public String getNamaRole() {
        return namaRole;
    }

    public void setNamaRole(String namaRole) {
        this.namaRole = namaRole;
    }

    public int getPermissionsFlag() {
        return permissionsFlag;
    }

    public void setPermissionsFlag(int permissionsFlag) {
        this.permissionsFlag = permissionsFlag;
    }

    public static ObservableList<Role> getAll(QueryOptions options){
        ObservableList<Role> roleRes = FXCollections.observableArrayList();

        String stmString = "SELECT * FROM role WHERE id_own_perusahaan = ?";
        Optional<String> search = options.getSearch();
        if (search.isPresent()) {
            stmString += " AND (nama_role LIKE ?)";
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
                Role role = new Role(
                        rs.getString("nama_role"),
                        rs.getInt("permissions_flag"));
                role.setId(rs.getInt("id"));
                role.setIdOwnPerusahaan(rs.getInt("id_own_perusahaan"));
                roleRes.add(role);
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

        return roleRes;
    }

    public static int getAllCount(QueryOptions options){
        int count = 0;

        String stmString = "SELECT COUNT(*) FROM role WHERE id_own_perusahaan = ?";
        Optional<String> search = options.getSearch();
        if (search.isPresent()) {
            stmString += " AND (nama_role LIKE ?)";
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

    // Method untuk menambahkan role baru ke database
    public void tambah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB
                    .prepareStatement("INSERT INTO role (id_own_perusahaan, nama_role, permissions_flag) VALUES (?, ?, ?)");
            stm.setInt(1, this.idOwnPerusahaan);
            stm.setString(2, this.namaRole);
            stm.setInt(3, this.permissionsFlag);
            stm.executeUpdate();
            System.out.println(stm.toString());
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

    // Method untuk menghapus role dari database
    public void hapus() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("DELETE FROM role WHERE id = ?");
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

    // Method untuk mengubah role di database
    public void ubah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB
                    .prepareStatement("UPDATE role SET nama_role = ?, permissions_flag = ? WHERE id = ?");
            stm.setString(1, this.namaRole);
            stm.setInt(2, this.permissionsFlag);
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
