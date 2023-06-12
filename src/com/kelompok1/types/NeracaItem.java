package com.kelompok1.types;

enum KelompokNeraca {
    Aktiva, Pasiva;

    @Override
    public String toString() {
        switch (this) {
            case Aktiva:
                return "aktiva";
            case Pasiva:
                return "pasiva";
            default:
                return "unknown";
        }
    }

    public static KelompokNeraca fromString(String kelompokString) {
        switch (kelompokString) {
            case "aktiva":
                return Aktiva;
            case "pasiva":
                return Pasiva;
            default:
                return null;
        }
    }
}

public class NeracaItem implements ILaporanItem {
    private KelompokNeraca kelompok;
    private String namaAkun;
    private double nominal;

    public NeracaItem(String kelompok, String namaAkun, double nominal) {
        this.kelompok = KelompokNeraca.fromString(kelompok);
        this.namaAkun = namaAkun;
        this.nominal = nominal;
    }

    public KelompokNeraca getKelompok() {
        return kelompok;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public double getNominal() {
        return nominal;
    }
}
