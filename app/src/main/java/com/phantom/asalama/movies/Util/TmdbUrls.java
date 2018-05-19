package com.phantom.asalama.movies.Util;

import com.phantom.asalama.movies.BuildConfig;

public class TmdbUrls {
    //public static final String API=BuildConfig.API_KEY;
    public static final String THMDB_API_KEY = "api_key=244e23f58f9f1468b298d7d9069d9b91";
    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String SORT_POPULARITY = "popular?&sort_by=popularity.desc&";
    public static final String SORT_RATED = "top_rated?sort_by=vote_average.desc&vote_count.gte=250&";
    public static final String VIDEOS = "{movie_id}/videos?";
    public static final String REVIEWS = "{movie_id}/reviews?";
    public static final String YOUTUBE_URL = "http://www.youtube.com/watch?v=";
    public static final String YOUTUBE_THUMB = "http://img.youtube.com/vi/";
    public static final String YOUTUBE_MEDIUM_QUALITY = "/mqdefault.jpg";
    public static final String PAGE_NUMBER = "&page=";

}
