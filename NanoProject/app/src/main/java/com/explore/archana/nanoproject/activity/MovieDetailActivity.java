package com.explore.archana.nanoproject.activity;

import android.app.Activity;
import android.os.Bundle;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.fragments.MovieDetailFragment;

/**
 * Created by archana on 8/22/2015.
 */
public class MovieDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);
        if(savedInstanceState == null){
            getFragmentManager().beginTransaction().add(R.id.movie_detail_container,new MovieDetailFragment()).commit();
        }
    }
}
