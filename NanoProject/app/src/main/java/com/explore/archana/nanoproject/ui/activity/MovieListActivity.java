package com.explore.archana.nanoproject.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.listeners.ILoadMoveDetail;
import com.explore.archana.nanoproject.model.MovieResultsModel;
import com.explore.archana.nanoproject.ui.activity.fragments.MovieDetailFragment;
import com.explore.archana.nanoproject.ui.activity.fragments.MovieListFragment;
import com.explore.archana.nanoproject.utils.Constants;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * Created by archana on 7/28/2015.
 */
public class MovieListActivity extends AppCompatActivity implements ILoadMoveDetail {

    public static final String LISTFRAGMENT = "MLTAG";
    public static final String DETAILFRAGMENT = "DFTAG";
    private boolean mTwoPane;
    private MovieListFragment listFragment;
    private MovieDetailFragment movieDetailFragment;
    public static MovieResultsModel passData;
    public static Cursor passCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);
        listFragment = new MovieListFragment();
        getFragmentManager().beginTransaction().add(R.id.movie_list_container, listFragment, LISTFRAGMENT).commit();

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                movieDetailFragment = new MovieDetailFragment();
//                getFragmentManager().beginTransaction().add(R.id.movie_detail_container, movieDetailFragment, DETAILFRAGMENT).addToBackStack(null).commit();
            }

        } else {
            mTwoPane = false;
            MovieDetailFragment.isFirstTime = true;
            getSupportActionBar().setElevation(0f);
        }

        listFragment = (MovieListFragment) getFragmentManager().findFragmentByTag(LISTFRAGMENT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getFragmentManager().putFragment(outState, LISTFRAGMENT, listFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listFragment = (MovieListFragment) getFragmentManager().getFragment(savedInstanceState, LISTFRAGMENT);
        }
    }

    @Override
    public void onMovieSelected(int position, MovieResultsModel movieData, Cursor cursor) {
        Log.v("selected movie position", "selected movie position" + position + movieData + cursor);
        if (mTwoPane) {
            movieDetailFragment = new MovieDetailFragment();
            if (cursor == null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MovieDetailFragment.DETAILURI, movieData);
                movieDetailFragment.setArguments(bundle);
            } else {
                movieDetailFragment.setCursor(cursor);
            }
            getFragmentManager().beginTransaction().replace(R.id.movie_detail_container, movieDetailFragment, DETAILFRAGMENT).addToBackStack(null).commit();


        } else {
            if (cursor != null)
                passCursor =cursor;
            else
            passData = movieData;
            Intent intent = new Intent(this, MovieDetailActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        MovieDetailFragment.isFirstTime = false;
        if (getFragmentManager().getBackStackEntryCount() == 0)
            finish();
        else {
            removeCurrentFragment();
        }
    }

    public void removeCurrentFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        android.app.Fragment currentFrag = getFragmentManager().findFragmentById(R.id.movie_detail_container);
        Log.i("Current fragment", "current fragment" + currentFrag.getClass().getSimpleName());
        final boolean b = getFragmentManager().popBackStackImmediate(currentFrag.getClass().getSimpleName(), 0);
        Log.d("stack", "stack" + getFragmentManager().popBackStackImmediate());
        if (!b && getFragmentManager().findFragmentByTag(DETAILFRAGMENT) == null) {
            transaction.replace(R.id.movie_detail_container, currentFrag, DETAILFRAGMENT);
            transaction.commit();
        }
    }
}
