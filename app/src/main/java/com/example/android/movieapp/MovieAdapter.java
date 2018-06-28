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
    private String[] mMoviePosters;
    private String[] mMovieTitles;
    private String[] mMovieData;
    private Context context;
    private List<RetroNetwork> mData;
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public TextView mMoviePosterImageView;
        public MovieAdapterViewHolder(View view){
            super(view);
            mMoviePosterImageView = (TextView) view.findViewById(R.id.im_movie_images);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String movieName = mMovieTitles[adapterPosition];
            String moviePoster = mMoviePosters[adapterPosition];
            String movieData = mMovieData[adapterPosition];
            String movie = movieName +","+ moviePoster+","+movieData;
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
        RetroNetwork movieImage = mData.get(position);
        String name = movieImage.getTitle();
        //Picasso.with(context).load(movieImage).into(holder.mMoviePosterImageView);
        holder.mMoviePosterImageView.setText(name);
    }

    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.size();
    }

    public void setMoviePosters(String[] moviePosters) {
        mMoviePosters = moviePosters;
        notifyDataSetChanged();
    }

    public void setMovieTitles(String[] movieTitles){
        mMovieTitles = movieTitles;
    }

    public void setMovieData(String[] movieData){
        mMovieData = movieData;
    }

    public void setData(List<RetroNetwork> test){
        mData = test;
        notifyDataSetChanged();
    }
}