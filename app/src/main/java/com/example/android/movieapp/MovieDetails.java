package com.example.android.movieapp;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movieapp.database.AppDatabase;
import com.example.android.movieapp.database.FavEntry;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity implements TrailersAdapter.TrailersAdapterOnClickHandler{
    private TextView mName;
    private TextView mYear;
    private TextView mDuration;
    private TextView mRate;
    private TextView mDescription;
    private ImageView mPoster;
    private ViewTrailers trailers;
    private ViewReviews reviews;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager2;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewReview;
    private TrailersAdapter mTrailersAdapter;
    private ReviewAdapter mReviewAdapter;
    private ScrollView mScrollView;
    private TextView mFavButton;
    private AppDatabase mDb;
    List<String> listIds;



    private static final String TAG = MovieDetails.class.getSimpleName();

    String id;
    String name;
    String poster;
    String year;
    String rate;
    String description;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("key", trailers);
        outState.putParcelable("key2", reviews);
        outState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mScrollView = (ScrollView) findViewById(R.id.scroll_details);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_trailers);
        mRecyclerViewReview = (RecyclerView) findViewById(R.id.rv_reviews);
        mName = (TextView) findViewById(R.id.tv_movie_name);
        mYear = (TextView) findViewById(R.id.tv_year);
        mDuration = (TextView) findViewById(R.id.tv_duration);
        mRate = (TextView) findViewById(R.id.tv_rate);
        mDescription = (TextView) findViewById(R.id.tv_description);
        layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerViewReview.setLayoutManager(layoutManager);
        mRecyclerViewReview.setHasFixedSize(true);
        mRecyclerViewReview.setNestedScrollingEnabled(false);
        mTrailersAdapter = new TrailersAdapter(MovieDetails.this);
        mReviewAdapter = new ReviewAdapter();
        mRecyclerView.setAdapter(mTrailersAdapter);
        mRecyclerViewReview.setAdapter(mReviewAdapter);
        mPoster = (ImageView) findViewById(R.id.im_movie_image);
        Intent intent = getIntent();
        mFavButton = (TextView) findViewById(R.id.fav_button);
        mDb = AppDatabase.getInstance(getApplicationContext());


        if ( intent.hasExtra(Intent.EXTRA_TEXT)) {
            String movieString = intent.getStringExtra(Intent.EXTRA_TEXT);
            String[] movieArray = movieString.split(",", 6);
            id = movieArray[0];
            name = movieArray[1];
            poster = movieArray[2];
            year = movieArray[3].split("-")[0];
            rate = movieArray[4];
            description = movieArray[5];
            mName.setText(name);
            Picasso.with(MovieDetails.this).load(poster).into(mPoster);
            mYear.setText(year);
            mDescription.setText(description);
            mRate.setText(rate + " / 10");
        }


        mFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FavEntry fav = new FavEntry(id, name, poster, year, rate, description);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // insert new task
                        if(mDb.taskDao().selectUserById(id) == null) {

                            mDb.taskDao().insertTask(fav);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MovieDetails.this, "Added to favourits ;)", Toast.LENGTH_SHORT).show();
                                }
                            });

                            Intent intent = new Intent(MovieDetails.this, MainActivity.class);
                            startActivity(intent);

                        }
                        else {

                            mDb.taskDao().deleteByUserId(id);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MovieDetails.this, "Removed from favourits :|", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        finish();
                    }
                });
            }
        });

        if (savedInstanceState != null) {
            trailers = savedInstanceState.getParcelable("key");
            reviews = savedInstanceState.getParcelable("key2");
            mTrailersAdapter.setMovieTrailers(trailers);
            mReviewAdapter.setMovieReviews(reviews);
            final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
            if (position != null)
                mScrollView.post(new Runnable() {
                    public void run() {
                        mScrollView.scrollTo(position[0], position[1]);
                    }
                });
        } else {
            loadTrailers();
            loadReviews();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void loadTrailers() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        GetData client = retrofit.create(GetData.class);
        Call<ViewTrailers> call = client.viewTrailers(MovieAdapter.getmMovieId(), "4e300fef67ec466d8676e3e807204ef4");
        call.enqueue(new Callback<ViewTrailers>() {


            @Override
            public void onResponse(Call<ViewTrailers> call, Response<ViewTrailers> response) {
                trailers = response.body();
                mTrailersAdapter.setMovieTrailers(trailers);
            }

            @Override
            public void onFailure(Call<ViewTrailers> call, Throwable t) {
                Toast.makeText(MovieDetails.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadReviews() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        GetData client = retrofit.create(GetData.class);
        Call<ViewReviews> call = client.viewReviews(MovieAdapter.getmMovieId(), "4e300fef67ec466d8676e3e807204ef4");
        call.enqueue(new Callback<ViewReviews>() {


            @Override
            public void onResponse(Call<ViewReviews> call, Response<ViewReviews> response) {
                reviews = response.body();
                mReviewAdapter.setMovieReviews(reviews);
            }

            @Override
            public void onFailure(Call<ViewReviews> call, Throwable t) {
                Toast.makeText(MovieDetails.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(String id) {
        Context context = this;
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}