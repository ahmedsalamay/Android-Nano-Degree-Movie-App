package com.phantom.asalama.movies;

import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phantom.asalama.movies.Util.TmdbUrls;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class MovieServicesModule {
    @Provides
    @MovieAppScope
    public MovieService movieService(OkHttpClient okHttpClient, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TmdbUrls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(MovieService.class);
    }

    @Provides
    @MovieAppScope
    public Gson gson() {
        return new GsonBuilder().
                registerTypeAdapter(DateTime.class, new DateTimeConverter()).create();
    }

}
