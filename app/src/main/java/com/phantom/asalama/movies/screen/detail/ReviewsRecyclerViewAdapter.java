package com.phantom.asalama.movies.screen.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phantom.asalama.movies.R;
import com.phantom.asalama.movies.models.Review;

import java.util.List;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder> {

    private List<Review> mReviews;

    public ReviewsRecyclerViewAdapter(List<Review> reviews) {
        mReviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.author.setText(mReviews.get(position).getAuthor());
        holder.content.setText(mReviews.get(position).getContent());
        holder.url.setText(mReviews.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        if (mReviews != null)
            return mReviews.size();
        else return 0;
    }

    public void setNewData(List<Review> newData) {
        mReviews = newData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView author;
        final TextView content;
        final TextView url;

        public ViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.review_author);
            content = itemView.findViewById(R.id.review_content);
            url = itemView.findViewById(R.id.review_url);
        }
    }
}
