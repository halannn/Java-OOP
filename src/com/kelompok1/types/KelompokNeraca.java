package com.kelompok1.types;

public enum KelompokNeraca {
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