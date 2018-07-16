package com.example.android.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ViewReviews implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<ReviewsResults> results = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    ViewReviews(Parcel in) {

        this.results = new ArrayList<ReviewsResults>();
        in.readTypedList(results, ReviewsResults.CREATOR);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ReviewsResults> getResults() {
        return results;
    }

    public void setResults(List<ReviewsResults> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(results);
    }
    public static final Parcelable.Creator<ViewReviews> CREATOR
            = new Parcelable.Creator<ViewReviews>() {

        public ViewReviews createFromParcel(Parcel in) {
            return new ViewReviews(in);
        }

        public ViewReviews[] newArray(int size) {
            return new ViewReviews[size];
        }
    };
}
