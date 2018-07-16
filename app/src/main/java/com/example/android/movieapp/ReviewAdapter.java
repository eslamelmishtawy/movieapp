package com.example.android.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private ViewReviews mMovieReviews;
    public ReviewAdapter() {

    }


    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mMovieReviewTextView;
        public ReviewAdapterViewHolder(View view){
            super(view);
            mMovieReviewTextView = (TextView) view.findViewById(R.id.tv_review);
        }
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewAdapterViewHolder trailersAdapterViewHolder, int i) {
        String movieReview = mMovieReviews.getResults().get(i).getContent();
        trailersAdapterViewHolder.mMovieReviewTextView.setText(movieReview);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieReviews) return 0;
        return mMovieReviews.getResults().size();
    }

    public void setMovieReviews(ViewReviews reviews) {
        mMovieReviews = reviews;
        notifyDataSetChanged();
    }

}
