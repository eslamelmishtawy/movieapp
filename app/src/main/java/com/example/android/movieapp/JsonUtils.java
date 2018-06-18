package com.example.android.movieapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class JsonUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    public static String getMoviePoster(Context context, String movieJsonString)
            throws JSONException {


        final String POSTER_PATH = "poster_path";

        String moviePoster = null;

        JSONObject movieJson = new JSONObject(movieJsonString);

            moviePoster = movieJson.getString(POSTER_PATH);
            moviePoster = "http://image.tmdb.org/t/p/w185//" + moviePoster;

    return moviePoster;
    }
}
