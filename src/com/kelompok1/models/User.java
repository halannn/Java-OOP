package com.kelompok1.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.OptionalInt;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.kelompok1.DB;
import com.kelompok1.types.QueryOptions;
import com.kelompok1.types.UserStatus;

public class User {
    private int id;
    private String username;
    private String hashedPassword;

    private int idOwnPerusahaan;
    private int idRole;
    private UserStatus status;

    private static Argon2 argon2 = Argon2Factory.create();

    public User(String username, String hashedPassword, int idOwnPerusahaan, int idRole, UserStatus status) {
        this.id = -1;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.idOwnPerusahaan = idOwnPerusahaan;
        this.idRole = idRole;
        this.status = status;
    }

    public User() {
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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public int getIdOwnPerusahaan() {
        return idOwnPerusahaan;
    }

    public void setIdOwnPerusahaan(int idOwnPerusahaan) {
        this.idOwnPerusahaan = idOwnPerusahaan;
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
            stm.setInt(1, this.idOwnPerusahaan);
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
        Role relatedRole = null;
        try {
            PreparedStatement stm = DB.prepareStatement("SELECT * FROM role WHERE id = ?");
            stm.setInt(1, this.idRole);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                relatedRole = new Role(rs.getString("nama_role"), rs.getInt("permissions_flag"));
                relatedRole.setId(rs.getInt("id"));
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
        return relatedRole;
    }

    public static ObservableList<User> getAll(QueryOptions options){
        ObservableList<User> userRes = FXCollections.observableArrayList();

        String stmString = "SELECT * FROM user WHERE id_own_perusahaan = ?";
        Optional<String> search = options.getSearch();
        if (search.isPresent()) {
            stmString += " AND (username LIKE ?)";
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
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("id_own_perusahaan"),
                        rs.getInt("id_role"),
                        UserStatus.fromString(rs.getString("status")));
                user.setId(rs.getInt("id"));
                user.setIdOwnPerusahaan(rs.getInt("id_own_perusahaan"));
                userRes.add(user);
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

        return userRes;
    }

    public static int getAllCount(QueryOptions options){
        int count = 0;

        String stmString = "SELECT COUNT(*) FROM user WHERE id_own_perusahaan = ?";
        Optional<String> search = options.getSearch();
        if (search.isPresent()) {
            stmString += " AND (username LIKE ?)";
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

    public static User getByUsername(String username) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = null;
        try {
            PreparedStatement stm = DB.prepareStatement("SELECT * FROM user WHERE username = ?");
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getInt("id_own_perusahaan"),
                        rs.getInt("id_role"), UserStatus.fromString(rs.getString("status")));
                user.setId(rs.getInt("id"));
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
        return user;
    }

    // Method untuk menambahkan user baru ke database
    public void tambah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB
                    .prepareStatement("INSERT INTO user (id_own_perusahaan, username, password, id_role, status) VALUES (?, ?, ?, ?, ?)");
            stm.setInt(1, this.idOwnPerusahaan);
            stm.setString(2, this.username);
            stm.setString(3, this.hashedPassword);
            stm.setInt(4, this.idRole);
            stm.setString(5, this.status.toString());
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

    // Method untuk menghapus user dari database
    public void hapus() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("DELETE FROM user WHERE id = ?");
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

    // Method untuk mengubah user di database
    public void ubah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB
                    .prepareStatement("UPDATE user SET username = ?, password = ?, id_role = ?, status = ? WHERE id = ?");
            stm.setString(1, this.username);
            stm.setString(2, this.hashedPassword);
            stm.setInt(3, this.idRole);
            stm.setString(4, this.status.toString());
            stm.setInt(5, this.id);
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

    public static String hashPassword(String password){
        char[] passwordChars = password.toCharArray();
        return argon2.hash(10, 65536, 1, passwordChars);
    }

    public boolean verifyPassword(String password) {
        char[] passwordChars = password.toCharArray();
        boolean verified = false;
        try {
            verified = argon2.verify(this.hashedPassword, passwordChars);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            argon2.wipeArray(passwordChars);
        }
        return verified;
    }
}
