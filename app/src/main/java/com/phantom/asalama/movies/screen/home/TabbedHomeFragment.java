package com.phantom.asalama.movies.screen.home;

//**

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.phantom.asalama.movies.MovieApplication;
import com.phantom.asalama.movies.MovieService;
import com.phantom.asalama.movies.R;
import com.phantom.asalama.movies.Util.EndlessRecyclerViewScrollListener;
import com.phantom.asalama.movies.Util.Utility;
import com.phantom.asalama.movies.models.Movie;
import com.phantom.asalama.movies.models.MoviesPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/* A placeholder fragment containing a simple view.
 */
public class TabbedHomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<MoviesPage> {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_TWO_PANE = "two_pane";
    private static final String PAGE_NUMBER_ARGS = "page_number";
    private boolean mTwoPane;
    private int mSectionNumber = 1;
    private int pageNumber = 1;
    private List<Movie> mPopularMovies;
    private RecyclerView mRecyclerView;
    private MovieService mMovieService;
    private MoviesPage mMoviesPage;
    private MovieListRecyclerViewAdapter mMovieListRecyclerViewAdapter;
    private LoaderManager loaderManager;
    private SpinKitView mOnLoadMoreLoadingIndecator;


    public TabbedHomeFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabbedHomeFragment newInstance(int sectionNumber, boolean twoPane) {
        TabbedHomeFragment fragment = new TabbedHomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putBoolean(ARG_TWO_PANE, twoPane);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTwoPane = getArguments().getBoolean(ARG_TWO_PANE);
        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        mOnLoadMoreLoadingIndecator = rootView.findViewById(R.id.spin_kit);
        mRecyclerView = rootView.findViewById(R.id.movie_list);
        assert mRecyclerView != null;
        mPopularMovies = new ArrayList<>();
        mMovieService = ((MovieApplication) getActivity().getApplication()).getmMovieService();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2,
                LinearLayoutManager.VERTICAL, false);
        mMovieListRecyclerViewAdapter = new MovieListRecyclerViewAdapter((MovieListActivity) getActivity(), mPopularMovies, mTwoPane);
        mRecyclerView.setAdapter(mMovieListRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        loaderManager = getLoaderManager();
        Loader<MoviesPage> moviesPageLoader = loaderManager.getLoader(0);
        Bundle pageNumber = new Bundle();
        pageNumber.putInt(PAGE_NUMBER_ARGS, 1);
        if (moviesPageLoader == null) {
            getLoaderManager().initLoader(0, pageNumber, this);
        } else {
            loaderManager.restartLoader(0, pageNumber, this);
        }

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Utility.isConnectedOrConnecting(getContext())) {
                    loadNextDataFromApi(page);
                }
            }
        });
        return rootView;
    }

    public void loadNextDataFromApi(int page) {
        Bundle pageNumber = new Bundle();
        pageNumber.putInt(PAGE_NUMBER_ARGS, page);
        if (!loaderManager.hasRunningLoaders()) {
            loaderManager.restartLoader(0, pageNumber, this);
        }
    }


    @Override
    public Loader<MoviesPage> onCreateLoader(int id, Bundle args) {
        pageNumber = args.getInt(PAGE_NUMBER_ARGS);

        return new AsyncTaskLoader<MoviesPage>(getActivity()) {

            @Override
            protected void onStartLoading() {
                //super.onStartLoading();
                forceLoad();
                //TODO loading indecator here
                mOnLoadMoreLoadingIndecator.setVisibility(View.VISIBLE);
            }

            @Override
            public MoviesPage loadInBackground() {
                Call<MoviesPage> pageCall = null;
                if (mSectionNumber == 1) {
                    pageCall = mMovieService.listPopularMoives(pageNumber);
                } else if (mSectionNumber == 2) {
                    pageCall = mMovieService.listRateMoives(pageNumber);
                } else if (mSectionNumber == 3) {

                }
                if (Utility.isConnectedOrConnecting(getContext())) {
                    try {
                        if (pageCall != null) {
                            Response<MoviesPage> moviesPageResponse = pageCall.execute();
                            if (moviesPageResponse.isSuccessful()) {
                                return moviesPageResponse.body();
                            } else {
                                Log.e("Response Msg", moviesPageResponse.message());
                            }
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
    public void onLoadFinished(Loader<MoviesPage> loader, MoviesPage data) {
        if (data != null) {
            mMoviesPage = data;
            mPopularMovies.addAll(mMoviesPage.getResults());
            mMovieListRecyclerViewAdapter.setNewData(mPopularMovies);
            mMovieListRecyclerViewAdapter.notifyDataSetChanged();
        }
        mOnLoadMoreLoadingIndecator.setVisibility(View.INVISIBLE);
        //TODO check for empty state list
    }

    @Override
    public void onLoaderReset(Loader<MoviesPage> loader) {

    }
}