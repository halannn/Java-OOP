package com.kelompok1.types;

public class Permissions {
    public static final int MELIHAT_TRANSAKSI = 0b000000000000000000001;
    public static final int MELIHAT_LAPORAN = 0b000000000000000000010;
    public static final int MELIHAT_AKUN = 0b000000000000000000100;
    public static final int MELIHAT_KLIEN = 0b000000000000000001000;
    public static final int MELIHAT_USER = 0b000000000000000010000;
    public static final int MELIHAT_ROLE = 0b000000000000000100000;
    public static final int MENAMBAH_TRANSAKSI = 0b000000000000001000000;
    public static final int MENAMBAH_AKUN = 0b000000000000010000000;
    public static final int MENAMBAH_KLIEN = 0b000000000000100000000;
    public static final int MENAMBAH_USER = 0b000000000001000000000;
    public static final int MENAMBAH_ROLE = 0b000000000010000000000;
    public static final int MENGUBAH_TRANSAKSI = 0b000000000100000000000;
    public static final int MENGUBAH_AKUN = 0b000000001000000000000;
    public static final int MENGUBAH_KLIEN = 0b000000010000000000000;
    public static final int MENGUBAH_USER = 0b000000100000000000000;
    public static final int MENGUBAH_ROLE = 0b000001000000000000000;
    public static final int MENGHAPUS_TRANSAKSI = 0b000010000000000000000;
    public static final int MENGHAPUS_AKUN = 0b000100000000000000000;
    public static final int MENGHAPUS_KLIEN = 0b001000000000000000000;
    public static final int MENGHAPUS_USER = 0b010000000000000000000;
    public static final int MENGHAPUS_ROLE = 0b100000000000000000000;
}
