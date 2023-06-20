package com.kelompok1.types;

public class NeracaKeuanganItem implements ILaporanItem {
    private KelompokNeraca kelompok;
    private String namaAkun;
    private double nominal;

    public NeracaKeuanganItem(String kelompok, String namaAkun, double nominal) {
        this.kelompok = KelompokNeraca.fromString(kelompok);
        this.namaAkun = namaAkun;
        this.nominal = nominal;
    }

    public KelompokNeraca getKelompok() {
        return kelompok;
    }

    public void setKelompok(KelompokNeraca kelompok) {
        this.kelompok = kelompok;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public double getNominal() {
        return nominal;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;
    }
}
