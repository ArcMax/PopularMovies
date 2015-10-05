package com.explore.archana.nanoproject.ui.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.model.MovieResultsModel;
import com.explore.archana.nanoproject.ui.activity.fragments.MovieDetailFragment;

/**
 * Created by archana on 9/29/2015.
 */
public class MovieDetailActivity extends AppCompatActivity {
    MovieDetailFragment movieDetailFragment;
    public Boolean isFirstTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);
        if(!isFirstTime)
            movieDetailFragment = new MovieDetailFragment();

        if (savedInstanceState == null) {
            if(MovieListActivity.passData!=null && MovieListActivity.passCursor== null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MovieDetailFragment.DETAILURI, MovieListActivity.passData);
                movieDetailFragment.setArguments(bundle);
            }
            getFragmentManager().beginTransaction().add(R.id.movie_detail_container, movieDetailFragment).addToBackStack(null).commit();
        } else {
            movieDetailFragment = (MovieDetailFragment) getFragmentManager().findFragmentByTag(MovieListActivity.DETAILFRAGMENT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getFragmentManager().putFragment(outState, MovieListActivity.DETAILFRAGMENT, movieDetailFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movieDetailFragment = (MovieDetailFragment) getFragmentManager().getFragment(savedInstanceState, MovieListActivity.DETAILFRAGMENT);
    }
}
