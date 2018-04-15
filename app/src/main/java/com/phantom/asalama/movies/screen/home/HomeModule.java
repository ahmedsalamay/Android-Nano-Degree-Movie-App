package com.phantom.asalama.movies.screen.home;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ahmedsalama on 23/03/18.
 */
@Module
public class HomeModule {

    private final MovieListActivity mMovieListAcctivity;
    private Boolean mTwoPane;

    public HomeModule(MovieListActivity movieListActivity, Boolean twoPane) {
        mMovieListAcctivity = movieListActivity;
        mTwoPane = twoPane;
    }

    @Provides
    public SectionsPagerAdapter sectionPagerAdapter() {

        return new SectionsPagerAdapter
                (mMovieListAcctivity.getSupportFragmentManager(), mTwoPane);
    }
}
