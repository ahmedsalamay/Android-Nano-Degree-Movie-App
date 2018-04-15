package com.phantom.asalama.movies;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by ahmedsalama on 21/03/18.
 */
@Module(includes = ContextModule.class)
public class NetworkModule {
    @Provides
    @MovieAppScope
    public OkHttpClient okHttpClient
            (HttpLoggingInterceptor httpLoggingInterceptor, Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
        return okHttpClient;
    }

    @Provides
    @MovieAppScope
    public HttpLoggingInterceptor loggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @MovieAppScope
    public Cache cache(File file) {
        return new Cache(file, 10 * 1000 * 1000);//10mg cache
    }

    @Provides
    @MovieAppScope
    public File CacheFile(Context context) {
        return new File(context.getCacheDir(), "okhttp cache");
    }
}
