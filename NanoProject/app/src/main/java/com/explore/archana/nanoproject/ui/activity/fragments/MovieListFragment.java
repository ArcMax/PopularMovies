package com.explore.archana.nanoproject.ui.activity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.app.App;
import com.explore.archana.nanoproject.listeners.ILoadMoveDetail;
import com.explore.archana.nanoproject.model.MovieResultsModel;
import com.explore.archana.nanoproject.provider.MoviesProvider;
import com.explore.archana.nanoproject.provider.MoviesSQLiteOpenHelper;
import com.explore.archana.nanoproject.rest.model.MovieDataModel;
import com.explore.archana.nanoproject.ui.activity.MovieListActivity;
import com.explore.archana.nanoproject.ui.activity.adapter.FavoriteGridAdapter;
import com.explore.archana.nanoproject.ui.activity.adapter.GridImageAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by archana on 7/28/2015.
 */
public class MovieListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG ="MovieListFragment";
    public static final String LISTURI = "LISTURI";
    private GridImageAdapter gridImageAdapter;
    ILoadMoveDetail loadMoveDetail;
    private GridView gridView;
    private Cursor cursor = null;
    private MovieDataModel dataModel;
    private FavoriteGridAdapter favoriteAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreate");
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
        try {
            loadMoveDetail = (ILoadMoveDetail) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement listener");
        }
        if(favoriteAdapter!=null)
            favoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LISTURI, dataModel);
        Log.d(TAG, "onsavedinstancestate" + dataModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "oncreateview");
          View view = inflater.inflate(R.layout.movielist_layout, null);
//        getActivity().deleteDatabase(MoviesSQLiteOpenHelper.DATABASE_NAME);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.pop_movies);
        gridView = (GridView) view.findViewById(R.id.movies_gridview);
        if(savedInstanceState !=null && savedInstanceState.getParcelable(LISTURI)!=null){
            dataModel = (MovieDataModel)savedInstanceState.getParcelable(LISTURI);
            Log.e(TAG, "movie data state serialised"+dataModel.getResults());
            gridImageAdapter = new GridImageAdapter(getActivity(), R.layout.grid_item_layout, dataModel.getResults());
            gridView.setAdapter(gridImageAdapter);
        }else if(dataModel==null){
            Log.e(TAG, "movie data state calltask");
                callApiTask(null);
        }
        gridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.popular_movies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popolar_movie:

                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.pop_movies);
                callApiTask("popularity.desc");
                break;
            case R.id.highest_rated:
                MovieListActivity.passCursor=null;
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.top_movies);
                callApiTask("vote_count.desc");
                break;
            case R.id.favorites:
                showFavoriteMovies();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.favorate);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFavoriteMovies() {
        String URL = "content://com.explore.archana.nanoproject.provider/movies";
        Uri movies = Uri.parse(URL);
        cursor = getActivity().getContentResolver().query(movies, null, null, null, "_id");
        if (cursor != null) {
            favoriteAdapter = new FavoriteGridAdapter(getActivity(), cursor, 0);
            gridView.setAdapter(favoriteAdapter);
        } else {
            Toast.makeText(getActivity(), "No Favorite movie added", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            MovieResultsModel data = (MovieResultsModel) parent.getItemAtPosition(position);
            if (data.getFavorate() == null)
                data.setFavorate(false);
            loadMoveDetail.onMovieSelected(position, data, null);
        } catch (ClassCastException e) {
            loadMoveDetail.onMovieSelected(position, null, cursor);
        }
    }

    private void callApiTask(String sort) {
        App.getRestClient().getIApiCallService().getMoviesDataModel(sort, new Callback<MovieDataModel>() {
            @Override
            public void success(MovieDataModel movieDataModel, Response response) {
                dataModel = movieDataModel;
                gridImageAdapter = new GridImageAdapter(getActivity(), R.layout.grid_item_layout, dataModel.getResults());
                gridView.setAdapter(gridImageAdapter);
                if(MovieDetailFragment.isFirstTime == false) {
                   MovieDetailFragment fragment = MovieDetailFragment.newInstance(dataModel);
                    getFragmentManager().beginTransaction().replace(R.id.movie_detail_container, fragment, MovieListActivity.DETAILFRAGMENT).addToBackStack(null).commit();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Crouton.makeText(getActivity(), error.getMessage(), Style.ALERT).show();
            }
        });
    }
}
