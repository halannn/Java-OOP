package com.kelompok1.types;

public class JurnalUmumItem implements ILaporanItem {
    private String tanggal;
    private String namaAkun;
    private String keterangan;
    private double debit;
    private double kredit;

    public JurnalUmumItem(String tanggal, String namaAkun, String keterangan, double debit, double kredit) {
        this.tanggal = tanggal;
        this.namaAkun = namaAkun;
        this.keterangan = keterangan;
        this.debit = debit;
        this.kredit = kredit;
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
}
