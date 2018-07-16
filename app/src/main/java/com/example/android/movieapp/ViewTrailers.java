package com.example.android.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ViewTrailers implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<TrailerResults> results = null;

    ViewTrailers(Parcel in) {

        this.results = new ArrayList<TrailerResults>();
        in.readTypedList(results, TrailerResults.CREATOR);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TrailerResults> getResults() {
        return results;
    }

    public void setResults(List<TrailerResults> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(results);
    }
    public static final Parcelable.Creator<ViewTrailers> CREATOR
            = new Parcelable.Creator<ViewTrailers>() {

        public ViewTrailers createFromParcel(Parcel in) {
            return new ViewTrailers(in);
        }

        public ViewTrailers[] newArray(int size) {
            return new ViewTrailers[size];
        }
    };
}
