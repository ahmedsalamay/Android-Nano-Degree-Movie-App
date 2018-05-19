package com.phantom.asalama.movies;

import com.phantom.asalama.movies.Util.TmdbUrls;
import com.phantom.asalama.movies.models.MoviesPage;
import com.phantom.asalama.movies.models.ReviewPage;
import com.phantom.asalama.movies.models.VideoPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ahmedsalama on 21/03/18.
 */

public interface MovieService {
    @GET(TmdbUrls.SORT_POPULARITY + TmdbUrls.THMDB_API_KEY)
    Call<MoviesPage> listPopularMoives(@Query("page") int pageNumber);

    @GET(TmdbUrls.SORT_RATED + TmdbUrls.THMDB_API_KEY)
    Call<MoviesPage> listRateMoives(@Query("page") int pageNumber);

    @GET(TmdbUrls.VIDEOS + TmdbUrls.THMDB_API_KEY)
    Call<VideoPage> listMovieVideos(@Path("movie_id") int movie_id);

    @GET(TmdbUrls.REVIEWS + TmdbUrls.THMDB_API_KEY)
    Call<ReviewPage> listMovieReviews(@Path("movie_id") int movie_id);
}
