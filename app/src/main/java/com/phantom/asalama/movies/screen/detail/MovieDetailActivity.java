package com.phantom.asalama.movies.screen.detail;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.phantom.asalama.movies.MovieApplication;
import com.phantom.asalama.movies.R;
import com.phantom.asalama.movies.data.MovieContract;
import com.phantom.asalama.movies.data.MoviesContentProvider;
import com.phantom.asalama.movies.models.Movie;
import com.phantom.asalama.movies.screen.home.MovieListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_BACKDROP;
import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_DATE;
import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_ID;
import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_LANGUAGE;
import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW;
import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_POPULARITY;
import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_POSTER;
import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_RATING;
import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_TITLE;
import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.COLUMN_NAME_VOTE_COUNT;

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieListActivity}.
 */
public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE_DETAIL_KEY = "963";
    private FloatingActionButton mFabBtn;
    private Movie mMovie;
    private boolean mIsMovieInDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        mFabBtn = findViewById(R.id.favorite_btn);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(false);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(android.R.color.transparent));
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            mMovie = getIntent().getParcelableExtra(MovieDetailFragment.ARG_ITEM_ID);
            Bundle arguments = new Bundle();
            arguments.putParcelable(MovieDetailFragment.ARG_ITEM_ID, mMovie);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
            // collapsingToolbarLayout.setTitle(movie.getTitle());
        } else {
            mMovie = savedInstanceState.getParcelable(MOVIE_DETAIL_KEY);
        }

        Picasso picasso = ((MovieApplication) (getApplication())).getmPicasso();

        picasso
                .load("http://image.tmdb.org/t/p/w500//"
                        + mMovie.getBackdropPath()).into(((ImageView) findViewById(R.id.movie_backdrop)));


        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mIsMovieInDB) {
                    int deletedIndex = deleteMovieFromDatabase(mMovie.getId().toString());
                    //if(deletedIndex>=0){
                    mFabBtn.setImageDrawable(ContextCompat.getDrawable(MovieDetailActivity.this, R.drawable.ic_favorite_outline));
                    mIsMovieInDB = false;
                    //}
                } else {
                    Uri addedUri = addMovieToFavorite(mMovie);
                    String lastsegment = addedUri.getLastPathSegment();
                    //if(lastsegment.equals(mMovie.getId())) {
                    mFabBtn.setImageDrawable(ContextCompat.getDrawable(MovieDetailActivity.this, R.drawable.ic_favorite));
                    mIsMovieInDB = true;
                    //}
                }


            }
        });
        mIsMovieInDB = isMovieInDatabase(String.valueOf(mMovie.getId()));
        if (mIsMovieInDB) {
            mFabBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        } else {
            mFabBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_outline));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. Formo
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_DETAIL_KEY, mMovie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMovie = savedInstanceState.getParcelable(MOVIE_DETAIL_KEY);
    }

    private Uri addMovieToFavorite(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_NAME_ID, movie.getId());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_RATING, movie.getVoteAverage());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_DATE, movie.getReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_BACKDROP, movie.getBackdropPath());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_VOTE_COUNT, movie.getVoteCount());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_LANGUAGE, movie.getOriginalLanguage());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_POPULARITY, movie.getPopularity());
        values.put(MovieContract.MovieEntry.COLUMN_NAME_POSTER, movie.getPosterPath());
        return getContentResolver().insert(MovieContract.CONTENT_BASE_URI, values);
    }

    public boolean isMovieInDatabase(String id) {

        Movie movie = getMovieFromDatabase(id);
        if (movie == null)
            return false;
        return true;
    }

    private Movie getMovieFromDatabase(String ID) {
        Movie movie = null;
        Uri contentUri = MovieContract.CONTENT_BASE_URI;
        String selectionClause = null;
        String[] selectionArgs = {""};
        selectionClause = MovieContract.MovieEntry.COLUMN_NAME_ID + " = ?";

        selectionArgs[0] = ID;
        Cursor c = this.getContentResolver().query(contentUri, null, selectionClause, selectionArgs, null);
        c.moveToFirst();
        if (null == c) {

        } else if (c.getCount() < 1) {

        } else {
            movie = new Movie();
            movie.setId((Integer.valueOf(c.getString(c.getColumnIndex(COLUMN_NAME_ID)))));
            int index = c.getColumnIndex(COLUMN_NAME_TITLE);
            movie.setTitle(c.getString(index));
            movie.setReleaseDate(c.getString(c.getColumnIndex(COLUMN_NAME_DATE)));
            movie.setPosterPath(c.getString(c.getColumnIndex(COLUMN_NAME_POSTER)));
            movie.setBackdropPath(c.getString(c.getColumnIndex(COLUMN_NAME_BACKDROP)));
            movie.setOverview(c.getString(c.getColumnIndex(COLUMN_NAME_OVERVIEW)));
            movie.setVoteCount(c.getInt(c.getColumnIndex(COLUMN_NAME_VOTE_COUNT)));
            movie.setVoteAverage(c.getDouble(c.getColumnIndex(COLUMN_NAME_RATING)));
            movie.setOriginalLanguage(c.getString(c.getColumnIndex(COLUMN_NAME_LANGUAGE)));
            movie.setPopularity(c.getDouble(c.getColumnIndex(COLUMN_NAME_POPULARITY)));
        }
        c.close();
        return movie;
    }

    private int deleteMovieFromDatabase(String ID) {

        Uri contentUri = MovieContract.CONTENT_BASE_URI;
        String selectionClause = null;
        String[] selectionArgs = {""};
        selectionClause = MovieContract.MovieEntry.COLUMN_NAME_ID + " = ?";

        selectionArgs[0] = ID;
        return this.getContentResolver().delete(contentUri, selectionClause, selectionArgs);
    }

}

