package com.kelompok1.models;

public class Role {
    private int id;
    private String namaRole;
    private int permissionsFlag;

    public Role(String namaRole, int permissionsFlag) {
        this.id = -1;
        this.namaRole = namaRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
