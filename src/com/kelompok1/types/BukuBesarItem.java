package com.kelompok1.types;

public class BukuBesarItem implements ILaporanItem {
    private String tanggal;
    private String namaAkun;
    private String keterangan;
    private double debit;
    private double kredit;
    private double saldo;

    public BukuBesarItem(String tanggal, String namaAkun, String keterangan, double debit, double kredit, double saldo) {
        this.tanggal = tanggal;
        this.namaAkun = namaAkun;
        this.keterangan = keterangan;
        this.debit = debit;
        this.kredit = kredit;
        this.saldo = saldo;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public double getDebit() {
        return debit;
    }

    public double getKredit() {
        return kredit;
    }

    public double getSaldo() {
        return saldo;
    }
}
