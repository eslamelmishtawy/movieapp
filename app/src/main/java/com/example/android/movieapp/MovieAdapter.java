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

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private String[] mMoviePosters;
    private String[] mMovieTitles;
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
            String movieName = mMovieTitles[adapterPosition];
            String moviePoster = mMoviePosters[adapterPosition];
            String movie = movieName +","+ moviePoster;
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
        String movieImage = mMoviePosters[position];
        Picasso.with(context).load(movieImage).into(holder.mMoviePosterImageView);
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

    public void setMovieTitles(String[] movieTitles){
        mMovieTitles = movieTitles;
    }
}
