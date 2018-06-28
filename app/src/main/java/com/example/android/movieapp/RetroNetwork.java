package com.example.android.movieapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetroNetwork {
    @SerializedName("title")
    @Expose
    private String title;

    public String getTitle() {
        return title;
    }
}
