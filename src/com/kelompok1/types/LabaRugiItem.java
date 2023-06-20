package com.kelompok1.types;

public class LabaRugiItem implements ILaporanItem {
    private KelompokLabaRugi kelompok;
    private String namaAkun;

    private double nominal;

    public LabaRugiItem(String kelompok, String namaAkun, double nominal) {
        this.kelompok = KelompokLabaRugi.fromString(kelompok);
        this.namaAkun = namaAkun;
        this.nominal = nominal;
    }

    public KelompokLabaRugi getKelompok() {
        return kelompok;
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
