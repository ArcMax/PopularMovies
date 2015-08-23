package com.explore.archana.nanoproject.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.fragments.MovieDetailFragment;
import com.explore.archana.nanoproject.fragments.MovieListFragment;
import com.explore.archana.nanoproject.listeners.ILoadMoveDetail;
import com.explore.archana.nanoproject.model.MovieData;

import java.util.ArrayList;

/**
 * Created by archana on 7/28/2015.
 */
public class MovieListActivity extends Activity implements ILoadMoveDetail {

    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            MovieListFragment listFragment = new MovieListFragment();
            transaction.add(R.id.movie_list_container, listFragment, "grid fragment");
            transaction.commit();

        }
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            getFragmentManager().popBackStack();
            MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getFragmentManager().findFragmentById(R.id.movie_detail_container);
            if (movieDetailFragment == null) {
                FragmentTransaction detailTransaction = getFragmentManager().beginTransaction();
                movieDetailFragment = new MovieDetailFragment();
                detailTransaction.replace(R.id.movie_detail_container, movieDetailFragment, "detail fragment1");
                detailTransaction.commit();
            }
        }
    }

    @Override
    public void onMovieSelected(int position,MovieData data) {
        Log.v("selected movie position", "selected movie position"+position+data);
        if(mTwoPane){
            Bundle bundle = new Bundle();
            bundle.putSerializable("MovieObject", data);
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            movieDetailFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, movieDetailFragment, "detail fragment1")
                    .commit();

        }else{
            MovieDetailFragment movieDetailFragment =  new MovieDetailFragment();
            movieDetailFragment.setMovieData(data);
            FragmentTransaction detailTransaction = getFragmentManager().beginTransaction();
            detailTransaction.replace(R.id.movie_list_container,movieDetailFragment,"detail frgamnet2");
            detailTransaction.addToBackStack("grid fragment");
            detailTransaction.commit();
        }
    }

}
