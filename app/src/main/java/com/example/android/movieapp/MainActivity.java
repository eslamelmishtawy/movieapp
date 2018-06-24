package com.example.android.movieapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{
    public static boolean rateFlag = false;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private RadioButton rateButton;
    private RadioButton popularButton;
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private String[] movieTitles;
    private String[] movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_display);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        GridLayoutManager layoutManager
                = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loadMovieData();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.sort);
            dialog.setTitle("Sort by");
            dialog.setCancelable(true);
            dialog.show();
            rateButton = dialog.findViewById(R.id.radioRate);
            popularButton = dialog.findViewById(R.id.radioPopular);
            rateButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"rate",Toast.LENGTH_SHORT).show();
                    rateFlag = true;
                    loadMovieData();
                }
            });
            popularButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"popular",Toast.LENGTH_SHORT).show();
                    rateFlag = false;
                    loadMovieData();
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void loadMovieData() {
        showMovieData();
        String key = "4e300fef67ec466d8676e3e807204ef4";
        new MovieTask().execute(key);
    }

    @Override
    public void onClick(String movie) {
        Context context = this;
        Intent intent = new Intent(MainActivity.this, MovieDetails.class);
        intent.putExtra(Intent.EXTRA_TEXT, movie);
        startActivity(intent);
    }

    private void showMovieData() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class MovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String key = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(key);
            Log.v(TAG,"URL " + movieRequestUrl);
            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);
                String[] jsonData = JsonUtils
                        .getMoviePoster(MainActivity.this, jsonResponse);

                movieTitles = JsonUtils.getMovieTitles(MainActivity.this, jsonResponse);
                mMovieAdapter.setMovieTitles(movieTitles);

                movieData = JsonUtils.getMovieData(MainActivity.this, jsonResponse);
                mMovieAdapter.setMovieData(movieData);

                return jsonData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieData();
                mMovieAdapter.setMoviePosters(movieData);
            } else {
                showErrorMessage();
            }
        }
    }
}
