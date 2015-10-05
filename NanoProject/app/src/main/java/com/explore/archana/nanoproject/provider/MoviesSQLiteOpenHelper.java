package com.explore.archana.nanoproject.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by archana on 9/29/2015.
 */
public class MoviesSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FavoriteMovies";
    static final String TABLE_NAME = "favorites";
    static final int DATABASE_VERSION = 1;

    //fields for database
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String POSTER_PATH = "poster_path";
    public static final String RELEASE_DATE = "releaseDate";
    public static final String VOTE_AVERAGE = "voteAverage";
    public static final String OVERVIEW = "overview";
    public static final String TRAILERS = "trailers";
    public static final String REVIEWS = "reviews";

    static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME +" (id INTEGER PRIMARY KEY AUTOINCREMENT, " + " _id TEXT NOT NULL, " + "" +
            "title TEXT NOT NULL, " + "poster_path BLOB NOT NULL, " + "releaseDate TEXT NOT NULL, " + "voteAverage TEXT NOT NULL, " + "overview TEXT NOT NULL, " +
            "trailers TEXT NOT NULL, " + "reviews TEXT NOT NULL );";


    public MoviesSQLiteOpenHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
            onCreate(db);
    }
}
