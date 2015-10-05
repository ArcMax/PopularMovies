package com.explore.archana.nanoproject.listeners;

import android.database.Cursor;

import com.explore.archana.nanoproject.model.MovieResultsModel;
import com.explore.archana.nanoproject.rest.model.MovieDataModel;

/**
 * Created by archana on 8/20/2015.
 */
public interface ILoadMoveDetail {

    public void onMovieSelected(int position,MovieResultsModel movieData,Cursor cursor);
}
