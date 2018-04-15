package com.phantom.asalama.movies;

import android.app.Activity;
import android.app.Application;

import com.squareup.picasso.Picasso;

import timber.log.Timber;

/**
 * Created by ahmedsalama on 23/03/18.
 */

public class MovieApplication extends Application {

    private MovieService mMovieService;
    private Picasso mPicasso;

    public static MovieApplication get(Activity activity) {
        return (MovieApplication) (activity.getApplication());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        MovieAppComponent movieAppComponent = DaggerMovieAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        mMovieService = movieAppComponent.getMovieService();
        mPicasso = movieAppComponent.getPicasso();

    }

    public MovieService getmMovieService() {
        return mMovieService;
    }

    public Picasso getmPicasso() {
        return mPicasso;
    }
}
