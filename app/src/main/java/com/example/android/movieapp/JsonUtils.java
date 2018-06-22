package com.example.android.movieapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class JsonUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    public static String[] getMoviePoster(Context context, String movieJsonString)
            throws JSONException {

        final String RESULTS ="results";
        final String POSTER_PATH = "poster_path";

        String[] moviePoster = null;

        JSONObject movieJson = new JSONObject(movieJsonString);

        JSONArray moviesArray = movieJson.getJSONArray(RESULTS);

        moviePoster = new String[moviesArray.length()];

        for(int i = 0; i < moviesArray.length(); i++) {
            JSONObject image = moviesArray.getJSONObject(i);
            moviePoster[i] = image.getString(POSTER_PATH);
            moviePoster[i] = "http://image.tmdb.org/t/p/w500//" + moviePoster[i];
            Log.v(TAG, "poster " + moviePoster[i]);
        }

    return moviePoster;
    }

    public static String[] getMovieTitles(Context context, String movieJsonString)
            throws JSONException {

        final String RESULTS ="results";
        final String TITLE = "title";

        String[] movieTitles = null;

        JSONObject movieJson = new JSONObject(movieJsonString);

        JSONArray moviesArray = movieJson.getJSONArray(RESULTS);

        movieTitles = new String[moviesArray.length()];

        for(int i = 0; i < moviesArray.length(); i++) {
            JSONObject title = moviesArray.getJSONObject(i);
            movieTitles[i] = title.getString(TITLE);
            Log.v(TAG, "poster " + movieTitles[i]);
        }

        return movieTitles;
    }

    public static String[] getMovieData(Context context, String movieJsonString)
            throws JSONException {

        final String RESULTS ="results";
        final String RATE = "vote_average";
        final String YEAR = "release_date";
        final String DURATION = "";
        final String DESCRIPTION = "overview";

        String[] movieData = null;

        JSONObject movieJson = new JSONObject(movieJsonString);

        JSONArray moviesArray = movieJson.getJSONArray(RESULTS);

        movieData = new String[moviesArray.length()];

        for(int i = 0; i < moviesArray.length(); i++) {
            JSONObject data = moviesArray.getJSONObject(i);
            movieData[i] = data.getString(YEAR) + "," + data.getString(RATE) + "," + data.getString(DESCRIPTION);
            Log.v(TAG, "poster " + movieData[i]);
        }

        return movieData;
    }


}
