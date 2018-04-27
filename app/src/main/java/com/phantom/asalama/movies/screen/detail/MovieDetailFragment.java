package com.phantom.asalama.movies.screen.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phantom.asalama.movies.MovieApplication;
import com.phantom.asalama.movies.R;
import com.phantom.asalama.movies.models.Movie;
import com.phantom.asalama.movies.screen.home.MovieListActivity;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Movie mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (getArguments().getParcelable(ARG_ITEM_ID));


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                //  appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.movie_title)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.movie_rating)).setText(mItem.getReleaseDate());
            ((TextView) rootView.findViewById(R.id.movie_realse_date)).setText(mItem.getVoteAverage().toString());
            ((TextView) rootView.findViewById(R.id.story_line)).setText(mItem.getOverview());


            Picasso picasso = ((MovieApplication) (getActivity().getApplication())).getmPicasso();
            picasso
                    .load("http://image.tmdb.org/t/p/w154//"
                            + mItem.getPosterPath())
                    .into(((ImageView) rootView.findViewById(R.id.movie_poster)));

        }

        return rootView;
    }
}
