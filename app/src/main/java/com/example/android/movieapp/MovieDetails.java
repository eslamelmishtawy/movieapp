package com.example.android.movieapp;


import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    private TextView mName;
    private TextView mYear;
    private TextView mDuration;
    private TextView mRate;
    private TextView mDescription;
    private ImageView mPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(getResources().getString(R.string.app_name));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mName = (TextView) findViewById(R.id.tv_movie_name);
        mYear = (TextView) findViewById(R.id.tv_year);
        mDuration = (TextView) findViewById(R.id.tv_duration);
        mRate = (TextView) findViewById(R.id.tv_rate);
        mDescription = (TextView) findViewById(R.id.tv_description);

        mPoster = (ImageView) findViewById(R.id.im_movie_image);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String movieString = intent.getStringExtra(Intent.EXTRA_TEXT);
            String[] movieArray = movieString.split(",",5);
            String name = movieArray[0];
            String poster = movieArray[1];
            String year = movieArray[2].split("-")[0];
            String rate = movieArray[3];
            String description = movieArray[4];
            mName.setText(name);
            Picasso.with(this).load(poster).into(mPoster);
            mYear.setText(year);
            mDescription.setText(description);
            mRate.setText(rate + " / 10");
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
}
