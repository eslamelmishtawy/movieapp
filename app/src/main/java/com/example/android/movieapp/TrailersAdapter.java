package com.example.android.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersAdapterViewHolder> {
    private ViewTrailers mMovieTrailers;
    private final TrailersAdapterOnClickHandler mClickHandler;

    public interface TrailersAdapterOnClickHandler {
        void onClick(String trailersForMovie);
    }

    public TrailersAdapter(TrailersAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public class TrailersAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMovieTrailersTextView;
        public TrailersAdapterViewHolder(View view){
                super(view);
                mMovieTrailersTextView = (TextView) view.findViewById(R.id.tv_trailer_name);
                view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mMovieTrailers.getResults().get(adapterPosition).getKey());
        }
    }

    @Override
    public TrailersAdapterViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailers_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new TrailersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.TrailersAdapterViewHolder trailersAdapterViewHolder, int i) {
        String movieTrailerName = mMovieTrailers.getResults().get(i).getName();
        trailersAdapterViewHolder.mMovieTrailersTextView.setText(movieTrailerName);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieTrailers) return 0;
        return mMovieTrailers.getResults().size();
    }

    public void setMovieTrailers(ViewTrailers movieTrailers) {
        mMovieTrailers = movieTrailers;
        notifyDataSetChanged();
    }

}

