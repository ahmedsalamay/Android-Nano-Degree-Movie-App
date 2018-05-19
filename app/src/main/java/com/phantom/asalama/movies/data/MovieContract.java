package com.phantom.asalama.movies.data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.phantom.asalama.movies.models.Movie;

public class MovieContract {

    public static final String AUTHORITY = "com.phantom.asalama.movies.provider";
    public static final String BASE_PATH = "movies";
    public static final Uri CONTENT_BASE_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private MovieContract() {
    }

    public static class MovieEntry {
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_GENRE = "genre";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_BACKDROP = "backdrop";
        public static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
        public static final String COLUMN_NAME_TAG_LINE = "tag_line";
        public static final String COLUMN_NAME_RUN_TIME = "runtime";
        public static final String COLUMN_NAME_LANGUAGE = "language";
        public static final String COLUMN_NAME_POPULARITY = "popularity";
        public static final String COLUMN_NAME_POSTER = "poster";
    }
}
