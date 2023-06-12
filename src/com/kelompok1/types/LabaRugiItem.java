package com.kelompok1.types;

enum KelompokLabaRugi {
    Pendapatan,
    Pengeluaran;

    @Override
    public String toString(){
        switch(this){
            case Pendapatan:
                return "pendapatan";
            case Pengeluaran:
                return "pengeluaran";
            default:
                return "unknown";
        }
    }

    public static KelompokLabaRugi fromString(String kelompokString){
        switch(kelompokString){
            case "pendapatan":
                return Pendapatan;
            case "pengeluaran":
                return Pengeluaran;
            default:
                return null;
        }
    }
}

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

    public double getNominal() {
        return nominal;
    }
}
