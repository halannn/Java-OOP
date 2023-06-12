package com.kelompok1.types;

public class TrialBalanceItem implements ILaporanItem {
    private String namaAkun;
    private double debit;
    private double kredit;

    public TrialBalanceItem(String namaAkun, double debit, double kredit) {
        this.namaAkun = namaAkun;
        this.debit = debit;
        this.kredit = kredit;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public double getDebit() {
        return debit;
    }

    public double getKredit() {
        return kredit;
    }
}
