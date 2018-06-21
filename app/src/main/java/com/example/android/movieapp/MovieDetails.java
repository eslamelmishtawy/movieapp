package com.example.android.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        mName = (TextView) findViewById(R.id.tv_movie_name);
        mYear = (TextView) findViewById(R.id.tv_year);
        mDuration = (TextView) findViewById(R.id.tv_duration);
        mRate = (TextView) findViewById(R.id.tv_rate);
        mDescription = (TextView) findViewById(R.id.tv_description);

        mPoster = (ImageView) findViewById(R.id.im_movie_image);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String name = intent.getStringExtra(Intent.EXTRA_TEXT);
            mName.setText(name);
        }
    }
}