package com.phantom.asalama.movies.screen.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phantom.asalama.movies.MovieApplication;
import com.phantom.asalama.movies.R;
import com.phantom.asalama.movies.models.Movie;
import com.phantom.asalama.movies.screen.detail.MovieDetailActivity;
import com.phantom.asalama.movies.screen.detail.MovieDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieListRecyclerViewAdapter.ViewHolder> {

    private final MovieListActivity mParentActivity;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Movie item = (Movie) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(MovieDetailFragment.ARG_ITEM_ID, item);
                MovieDetailFragment fragment = new MovieDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, item);

                context.startActivity(intent);
            }
        }
    };
    private List<Movie> mValues;

    MovieListRecyclerViewAdapter(MovieListActivity parent,
                                 List<Movie> items,
                                 boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso picasso = ((MovieApplication) mParentActivity.getApplication()).getmPicasso();


        picasso
                .load("http://image.tmdb.org/t/p/w185//"
                        + mValues.get(position).getPosterPath())
                .into(holder.mPosterView);

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        if (mValues == null)
            return 0;
        return mValues.size();
    }

    public void setNewData(List<Movie> newData) {
        mValues = newData;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView mPosterView;
        ViewHolder(View view) {
            super(view);

            mPosterView = view.findViewById(R.id.movie_poster_item);
        }
    }
}
