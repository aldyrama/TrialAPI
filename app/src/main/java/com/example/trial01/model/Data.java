package com.example.trial01.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {

    @SerializedName("data")
    ArrayList<Product> data;
    @SerializedName("page")
    int Page;
    @SerializedName("per_page")
    int PerPage;
    @SerializedName("total")
    int Total;
    @SerializedName("total_pages")
    int TotalPages;

    public ArrayList<Product> getData() {
        return data;
    }

    public void setData(ArrayList<Product> data) {
        this.data = data;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public int getPerPage() {
        return PerPage;
    }

    public void setPerPage(int perPage) {
        PerPage = perPage;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }
}
