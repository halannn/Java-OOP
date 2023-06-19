package com.kelompok1.types;

import java.util.Optional;
import java.util.OptionalInt;

public class QueryOptions {
    private int idOwnPerusahaan;
    private OptionalInt currentPage;
    private OptionalInt limit;
    private Optional<String> search;

    public QueryOptions() {
        idOwnPerusahaan = -1;
        currentPage = OptionalInt.empty();
        limit = OptionalInt.empty();
        search = Optional.empty();
    }

    public QueryOptions(int idOwnPerusahaan, OptionalInt currentPage, OptionalInt limit, Optional<String> search) {
        this.idOwnPerusahaan = idOwnPerusahaan;
        this.currentPage = currentPage;
        this.limit = limit;
        this.search = search;
    }

    public OptionalInt getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(OptionalInt currentPage) {
        this.currentPage = currentPage;
    }

    public OptionalInt getLimit() {
        return limit;
    }

    public void setLimit(OptionalInt limit) {
        this.limit = limit;
    }

    public Optional<String> getSearch() {
        return search;
    }

    public void setSearch(Optional<String> search) {
        this.search = search;
    }

    public int getIdOwnPerusahaan() {
        return idOwnPerusahaan;
    }

    public void setIdOwnPerusahaan(int idOwnPerusahaan) {
        this.idOwnPerusahaan = idOwnPerusahaan;
    }
}
