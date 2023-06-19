package com.kelompok1.types;

public enum JenisAkun {
    Aset,
    Modal,
    Pengeluaran,
    Pendapatan,
    Hutang,
    Piutang;

    @Override
    public String toString() {
        switch (this) {
            case Aset:
                return "aset";
            case Modal:
                return "modal";
            case Pengeluaran:
                return "pengeluaran";
            case Pendapatan:
                return "pendapatan";
            case Hutang:
                return "hutang";
            case Piutang:
                return "piutang";
            default:
                return "";
        }
    }

    public static JenisAkun fromString(String jenisString) {
        switch (jenisString) {
            case "aset":
                return Aset;
            case "modal":
                return Modal;
            case "pengeluaran":
                return Pengeluaran;
            case "pendapatan":
                return Pendapatan;
            case "hutang":
                return Hutang;
            case "piutang":
                return Piutang;
            default:
                return null;
        }
    }
}
