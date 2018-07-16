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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private ScrollView mScrollView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private RadioButton rateButton;
    private RadioButton popularButton;
    GridLayoutManager layoutManager;
    private String mSorting = "popular";
    private RetroNetwork repos;
    private static final String TAG = NetworkUtils.class.getSimpleName();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sort", mSorting);
        outState.putParcelable("key", repos);
        outState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_display);
        mRecyclerView.setNestedScrollingEnabled(false);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        layoutManager
                = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(MainActivity.this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        if (savedInstanceState != null) {
            mSorting = savedInstanceState.getString("sort");
            repos = savedInstanceState.getParcelable("key");
            mMovieAdapter.setMoviePosters(repos);
            final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
            if (position != null)
                mScrollView.post(new Runnable() {
                    public void run() {
                        mScrollView.scrollTo(position[0], position[1]);
                    }
                });
        } else {
            loadMovieData();
        }

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
                    mSorting = "top_rated";
                    loadMovieData();
                }
            });
            popularButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mSorting = "popular";
                    loadMovieData();
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadMovieData() {
        showMovieData();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        GetData client = retrofit.create(GetData.class);
        Call<RetroNetwork> call = client.reposForUser(mSorting, "4e300fef67ec466d8676e3e807204ef4");
        mLoadingIndicator.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<RetroNetwork>() {


            @Override
            public void onResponse(Call<RetroNetwork> call, Response<RetroNetwork> response) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                repos = response.body();
                mMovieAdapter.setMoviePosters(repos);
            }

            @Override
            public void onFailure(Call<RetroNetwork> call, Throwable t) {
                showErrorMessage();
                Toast.makeText(MainActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });

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

}