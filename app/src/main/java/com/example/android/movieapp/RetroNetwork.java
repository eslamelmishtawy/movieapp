package com.example.android.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RetroNetwork implements Parcelable {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    RetroNetwork(Parcel in) {

        this.results = new ArrayList<Result>();
        in.readTypedList(results, Result.CREATOR);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedList(results);
    }

    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<RetroNetwork> CREATOR
            = new Parcelable.Creator<RetroNetwork>() {

        public RetroNetwork createFromParcel(Parcel in) {
            return new RetroNetwork(in);
        }

        public RetroNetwork[] newArray(int size) {
            return new RetroNetwork[size];
        }
    };
}
