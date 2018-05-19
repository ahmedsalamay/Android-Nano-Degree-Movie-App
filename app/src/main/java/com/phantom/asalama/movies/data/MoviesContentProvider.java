package com.phantom.asalama.movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import static com.phantom.asalama.movies.data.MovieContract.*;

public class MoviesContentProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, BASE_PATH, MOVIES);
        sUriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", MOVIE_WITH_ID);
    }

    private MovieDbHelper mMovieDbHelper;

    @Override
    public boolean onCreate() {
        mMovieDbHelper = new MovieDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(MovieDbHelper.TABLE_MOVIE);
                retCursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                long id = db.insert(MovieDbHelper.TABLE_MOVIE, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.CONTENT_BASE_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlDB = mMovieDbHelper.getWritableDatabase();
        int rowsDeleted;
        rowsDeleted = sqlDB.delete(MovieDbHelper.TABLE_MOVIE, selection,
                selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase sqlDB = mMovieDbHelper.getWritableDatabase();
        int rowsUpdated;
        rowsUpdated = sqlDB.update(MovieDbHelper.TABLE_MOVIE,
                values,
                selection,
                selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
