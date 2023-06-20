package com.kelompok1.types;

public enum PosisiAkun {
    Debit,
    Kredit;

    @Override
    public String toString() {
        switch (this) {
            case Debit:
                return "debit";
            case Kredit:
                return "kredit";
            default:
                return "unknown";
        }
    }

    public static PosisiAkun fromString(String posisiString) {
        switch (posisiString) {
            case "debit":
                return Debit;
            case "kredit":
                return Kredit;
            default:
                return null;
        }
    }
}
