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

import com.example.android.movieapp.database.FavEntry;
import com.squareup.picasso.Cache;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import java.util.List;
import java.util.Locale;



public class FavsAdapter extends RecyclerView.Adapter<FavsAdapter.FavsAdapterViewHolder> {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static List<FavEntry> mFavs;
    private Context context;
    private final FavsAdapterOnClickHandler mClickHandler;
    public interface FavsAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    public FavsAdapter(FavsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class FavsAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public ImageView mMoviePosterImageView;

        public FavsAdapterViewHolder(View view) {
            super(view);
            mMoviePosterImageView = (ImageView) view.findViewById(R.id.im_movie_images);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String movieId = mFavs.get(adapterPosition).getId();
            String movieName = mFavs.get(adapterPosition).getName();
            String moviePoster = mFavs.get(adapterPosition).getPoster();
            String movieYear = mFavs.get(adapterPosition).getYear();
            String movieDesc = mFavs.get(adapterPosition).getDescription();
            Double movieRate = Double.parseDouble( mFavs.get(adapterPosition).getRate());
            String movie = movieId + "," + movieName + "," + moviePoster + "," + movieYear + "," + movieRate + "," + movieDesc;
            mClickHandler.onClick(movie);
        }

    }

    @Override
    public FavsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.grid_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FavsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavsAdapterViewHolder holder, int position) {
        FavEntry taskEntry = mFavs.get(position);
        String movieImage =
                mFavs.get(position).getPoster();
        Log.v(TAG, "Testttttt: " + movieImage);
        Picasso.with(context).load(movieImage).into(holder.mMoviePosterImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mFavs) return 0;
        return mFavs.size();
    }

    public void setFavMovies(List<FavEntry> taskEntries) {
        mFavs = taskEntries;
        notifyDataSetChanged();
    }

    public static List<FavEntry> getFavMovies() {
        return mFavs;
    }

}