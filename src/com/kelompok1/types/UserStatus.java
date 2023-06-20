package com.kelompok1.types;


public enum UserStatus {
    Aktif,
    Nonaktif;

    @Override
    public String toString() {
        switch (this) {
            case Aktif:
                return "Aktif";
            case Nonaktif:
                return "Nonaktif";
            default:
                return "";
        }
    }

    public static UserStatus fromString(String statusString) {
        switch (statusString) {
            case "Aktif":
                return Aktif;
            case "Nonaktif":
                return Nonaktif;
            default:
                return null;
        }
    }
}
