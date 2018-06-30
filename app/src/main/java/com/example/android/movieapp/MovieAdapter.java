package com.example.android.movieapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.squareup.picasso.Cache;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private RetroNetwork mMoviePosters;
    private String[] mMovieTitles;
    private String[] mMovieData;
    private Context context;
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public ImageView mMoviePosterImageView;
        public MovieAdapterViewHolder(View view){
            super(view);
            mMoviePosterImageView = (ImageView) view.findViewById(R.id.im_movie_images);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String movieName = mMoviePosters.getResults().get(adapterPosition).getTitle();
            String moviePoster = "http://image.tmdb.org/t/p/w185//" + mMoviePosters.getResults().get(adapterPosition).getPosterPath();
            String movieYear = mMoviePosters.getResults().get(adapterPosition).getReleaseDate();
            String movieDesc = mMoviePosters.getResults().get(adapterPosition).getOverview();
            Double movieRate = mMoviePosters.getResults().get(adapterPosition).getVoteAverage();
            String movie = movieName +","+ moviePoster+","+ movieYear + "," + movieRate + "," + movieDesc;
            mClickHandler.onClick(movie);
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
        String movieImage = "http://image.tmdb.org/t/p/w500//" +
                mMoviePosters.getResults().get(position).getPosterPath();
        Picasso.with(context).load(movieImage).into(holder.mMoviePosterImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMoviePosters) return 0;
        return mMoviePosters.getResults().size();
    }

    public void setMoviePosters(RetroNetwork moviePosters) {
        mMoviePosters = moviePosters;
        notifyDataSetChanged();
    }

    public void setMovieTitles(String[] movieTitles){
        mMovieTitles = movieTitles;
    }

    public void setMovieData(String[] movieData){
        mMovieData = movieData;
    }

}