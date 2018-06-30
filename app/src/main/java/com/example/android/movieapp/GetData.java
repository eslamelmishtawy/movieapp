package com.example.android.movieapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetData {
    @GET("/3/movie/{data}")
    Call<RetroNetwork> reposForUser(@Path("data") String data, @Query("api_key") String key);

}
