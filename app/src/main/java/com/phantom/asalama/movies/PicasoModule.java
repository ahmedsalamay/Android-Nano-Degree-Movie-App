package com.phantom.asalama.movies;

import android.content.Context;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module(includes = {ContextModule.class, NetworkModule.class})
public class PicasoModule {

    @Provides
    @MovieAppScope
    public Picasso picasso(Context context, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(context).downloader(okHttp3Downloader).build();
    }

    @Provides
    @MovieAppScope
    public OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClinet) {
        return new OkHttp3Downloader(okHttpClinet);
    }

}
