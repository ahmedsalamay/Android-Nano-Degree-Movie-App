package com.phantom.asalama.movies.screen.detail;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.phantom.asalama.movies.MovieApplication;
import com.phantom.asalama.movies.MovieService;
import com.phantom.asalama.movies.R;
import com.phantom.asalama.movies.Util.Utility;
import com.phantom.asalama.movies.models.Movie;
import com.phantom.asalama.movies.models.Review;
import com.phantom.asalama.movies.models.ReviewPage;
import com.phantom.asalama.movies.models.Video;
import com.phantom.asalama.movies.models.VideoPage;
import com.phantom.asalama.movies.screen.home.MovieListActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

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

    private static final String TRAILER_KEY = "45";

    private static final String REVIEWS_KEY = "916";

    public MovieDetailFragment() {
    }

    private static final int VIDEOS_LOADER_ID = 1;
    private static final int REVIEWS_LOADER_ID = 2;
    private MovieService mMovieService;
    private android.support.v4.app.LoaderManager mLoaderManager;

    private List<Video> mTrailers;
    private VideosRecyclerViewAdapter mVideosRecyclerViewAdapter;
    private RecyclerView TrailerRecyclerView;
    private VideoPage mVideoPage;
    private LoaderManager.LoaderCallbacks<VideoPage> mVideoPageLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<VideoPage>() {
                @Override
                public Loader<VideoPage> onCreateLoader(int i, Bundle bundle) {
                    return new AsyncTaskLoader<VideoPage>(getActivity()) {
                        @Override
                        protected void onStartLoading() {
                            //TODO loading indecator
                            forceLoad();
                        }

                        @Override
                        public VideoPage loadInBackground() {
                            Call<VideoPage> videoPageCall = mMovieService.listMovieVideos(mItem.getId());
                            if (Utility.isConnectedOrConnecting(getActivity())) {
                                try {
                                    Response<VideoPage> videoPageResponse = videoPageCall.execute();
                                    if (videoPageResponse.isSuccessful()) {
                                        return videoPageResponse.body();
                                    } else {
                                        Log.e("Response Msg", videoPageResponse.message());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //TODO No Connection
                                //TODO Reload
                            }
                            return null;
                        }
                    };
                }

                @Override
                public void onLoadFinished(Loader<VideoPage> loader, VideoPage data) {
                    if (data != null) {
                        mVideoPage = data;
                        mTrailers.addAll(mVideoPage.getResults());
                        mVideosRecyclerViewAdapter.setNewData(mTrailers);
                        mVideosRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    //  mOnLoadMoreLoadingIndecator.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoaderReset(Loader<VideoPage> loader) {

                }
            };

    private List<Review> mReviews;
    private ReviewsRecyclerViewAdapter mReviewsRecyclerViewAdapter;
    private RecyclerView mReviewsRecyclerView;
    private ReviewPage mReviewPage;
    private LoaderManager.LoaderCallbacks<ReviewPage> mReviewPageLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<ReviewPage>() {
                @Override
                public Loader<ReviewPage> onCreateLoader(int i, Bundle bundle) {
                    return new AsyncTaskLoader<ReviewPage>(getActivity()) {
                        @Override
                        protected void onStartLoading() {
                            forceLoad();
                            //TOD loading indecator
                        }

                        @Override
                        public ReviewPage loadInBackground() {
                            Call<ReviewPage> reviewPageCall = mMovieService.listMovieReviews(mItem.getId());
                            if (Utility.isConnectedOrConnecting(getActivity())) {
                                try {
                                    Response<ReviewPage> reviewResponse = reviewPageCall.execute();
                                    if (reviewResponse.isSuccessful()) {
                                        return reviewResponse.body();
                                    } else {
                                        Log.e("Response Msg", reviewResponse.message());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //TODO no connection
                            }
                            return null;
                        }
                    };
                }

                @Override
                public void onLoadFinished(Loader<ReviewPage> loader, ReviewPage data) {
                    if (data != null) {
                        mReviewPage = data;
                        mReviews.addAll(mReviewPage.getResults());
                        mReviewsRecyclerViewAdapter.setNewData(mReviews);
                        mReviewsRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    //  mOnLoadMoreLoadingIndecator.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoaderReset(Loader<ReviewPage> loader) {

                }
            };

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

        mMovieService = ((MovieApplication) (getActivity().getApplication())).getmMovieService();
        mLoaderManager = getLoaderManager();
        mTrailers = new ArrayList<>();
        mReviews = new ArrayList<>();
        if (savedInstanceState != null) {
            mVideoPage = savedInstanceState.getParcelable(TRAILER_KEY);
            mTrailers = mVideoPage.getResults();
            mReviewPage = savedInstanceState.getParcelable(REVIEWS_KEY);
            mReviews = mReviewPage.getResults();
        }
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

            TrailerRecyclerView = rootView.findViewById(R.id.trailers);
            TrailerRecyclerView.hasFixedSize();
            TrailerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false));
            mVideosRecyclerViewAdapter = new VideosRecyclerViewAdapter((MovieDetailActivity) getActivity(), mTrailers);
            TrailerRecyclerView.setAdapter(mVideosRecyclerViewAdapter);
            android.support.v4.content.Loader<Object> videoPageLoader = mLoaderManager.getLoader(VIDEOS_LOADER_ID);
            if (videoPageLoader == null)
                getActivity().getLoaderManager().initLoader
                        (VIDEOS_LOADER_ID, null, mVideoPageLoaderCallbacks);
            else
                getActivity().getLoaderManager().restartLoader(
                        VIDEOS_LOADER_ID, null, mVideoPageLoaderCallbacks);

            mReviewsRecyclerView = rootView.findViewById(R.id.reviews);
            mReviewsRecyclerView.hasFixedSize();
            mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
            mReviewsRecyclerViewAdapter = new ReviewsRecyclerViewAdapter(mReviews);
            mReviewsRecyclerView.setAdapter(mReviewsRecyclerViewAdapter);
            android.support.v4.content.Loader reviewLoader = mLoaderManager.getLoader(REVIEWS_LOADER_ID);
            if (reviewLoader == null)
                getActivity().getLoaderManager().initLoader(REVIEWS_LOADER_ID, null, mReviewPageLoaderCallbacks);
            else
                getActivity().getLoaderManager().restartLoader(REVIEWS_LOADER_ID, null, mReviewPageLoaderCallbacks);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TRAILER_KEY, mVideoPage);
        outState.putParcelable(REVIEWS_KEY, mReviewPage);
    }

}
