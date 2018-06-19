package com.example.android.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    public ImageView mMoviePosterImageView;
    private String[] mMoviePosters;
    private Context context;
    public MovieAdapter() {

    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder{

        public MovieAdapterViewHolder(View view){
            super(view);
            mMoviePosterImageView = (ImageView) view.findViewById(R.id.im_movie_images);
        }


    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.grid_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String movieImage = mMoviePosters[position];
        Log.v(TAG, " TEST " + movieImage);
        Picasso.with(context).load(movieImage).into(mMoviePosterImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMoviePosters) return 0;
        return mMoviePosters.length;
    }

    public void setMovieData(String[] moviePosters) {
        mMoviePosters = moviePosters;
        notifyDataSetChanged();
    }
}
