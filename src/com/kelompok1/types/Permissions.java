package com.kelompok1.types;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getPermissionsString(int permissionsFlag) {
        List<String> permissions = new ArrayList<>();

        if ((permissionsFlag & MELIHAT_TRANSAKSI) > 0) {
            permissions.add("Melihat Transaksi");
        }

        if ((permissionsFlag & MELIHAT_LAPORAN) > 0) {
            permissions.add("Melihat Laporan");
        }

        if ((permissionsFlag & MELIHAT_AKUN) > 0) {
            permissions.add("Melihat Akun");
        }

        if ((permissionsFlag & MELIHAT_KLIEN) > 0) {
            permissions.add("Melihat Klien");
        }

        if ((permissionsFlag & MELIHAT_USER) > 0) {
            permissions.add("Melihat User");
        }

        if ((permissionsFlag & MELIHAT_ROLE) > 0) {
            permissions.add("Melihat Role");
        }

        if ((permissionsFlag & MENAMBAH_TRANSAKSI) > 0) {
            permissions.add("Menambah Transaksi");
        }

        if ((permissionsFlag & MENAMBAH_AKUN) > 0) {
            permissions.add("Menambah Akun");
        }

        if ((permissionsFlag & MENAMBAH_KLIEN) > 0) {
            permissions.add("Menambah Klien");
        }

        if ((permissionsFlag & MENAMBAH_USER) > 0) {
            permissions.add("Menambah User");
        }

        if ((permissionsFlag & MENAMBAH_ROLE) > 0) {
            permissions.add("Menambah Role");
        }

        if ((permissionsFlag & MENGUBAH_TRANSAKSI) > 0) {
            permissions.add("Mengubah Transaksi");
        }

        if ((permissionsFlag & MENGUBAH_AKUN) > 0) {
            permissions.add("Mengubah Akun");
        }

        if ((permissionsFlag & MENGUBAH_KLIEN) > 0) {
            permissions.add("Mengubah Klien");
        }

        if ((permissionsFlag & MENGUBAH_USER) > 0) {
            permissions.add("Mengubah User");
        }

        if ((permissionsFlag & MENGUBAH_ROLE) > 0) {
            permissions.add("Mengubah Role");
        }

        if ((permissionsFlag & MENGHAPUS_TRANSAKSI) > 0) {
            permissions.add("Menghapus Transaksi");
        }

        if ((permissionsFlag & MENGHAPUS_AKUN) > 0) {
            permissions.add("Menghapus Akun");
        }

        if ((permissionsFlag & MENGHAPUS_KLIEN) > 0) {
            permissions.add("Menghapus Klien");
        }

        if ((permissionsFlag & MENGHAPUS_USER) > 0) {
            permissions.add("Menghapus User");
        }

        if ((permissionsFlag & MENGHAPUS_ROLE) > 0) {
            permissions.add("Menghapus Role");
        }

        return permissions;
    }
}
