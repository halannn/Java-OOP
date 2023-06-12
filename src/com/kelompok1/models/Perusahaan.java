package com.kelompok1.models;

public class Perusahaan {
    private int id;
    private String namaPerusahaan;
    private String noTelp;
    private String email;
    private String alamat;

    public Perusahaan(String namaPerusahaan, String noTelp, String email, String alamat) {
        this.id = -1;
        this.namaPerusahaan = namaPerusahaan;
        this.noTelp = noTelp;
        this.email = email;
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
