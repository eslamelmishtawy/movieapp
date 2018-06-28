package com.example.android.movieapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetData {
    @GET("/3/movie/top_rated")
    Call<List<RetroNetwork>> reposForUser(@Query("api_key") String key);

}
