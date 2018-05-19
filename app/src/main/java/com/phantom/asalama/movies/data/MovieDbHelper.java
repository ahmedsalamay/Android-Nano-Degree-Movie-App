package com.phantom.asalama.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.phantom.asalama.movies.data.MovieContract.MovieEntry.*;

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "movieDb";

    public static final String TABLE_MOVIE = "moviesDetails";


    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIE + "("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_TITLE + " TEXT,"
                + COLUMN_NAME_RATING + " TEXT,"
                + COLUMN_NAME_GENRE + " TEXT,"
                + COLUMN_NAME_DATE + " TEXT,"
                + COLUMN_NAME_STATUS + " TEXT,"
                + COLUMN_NAME_OVERVIEW + " TEXT,"
                + COLUMN_NAME_BACKDROP + " TEXT,"
                + COLUMN_NAME_VOTE_COUNT + " TEXT,"
                + COLUMN_NAME_TAG_LINE + " TEXT,"
                + COLUMN_NAME_RUN_TIME + " TEXT,"
                + COLUMN_NAME_LANGUAGE + " TEXT,"
                + COLUMN_NAME_POPULARITY + " TEXT,"
                + COLUMN_NAME_POSTER + " TEXT" + ")";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }
}

