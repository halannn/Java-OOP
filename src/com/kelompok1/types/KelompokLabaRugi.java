package com.kelompok1.types;

public enum KelompokLabaRugi {
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
