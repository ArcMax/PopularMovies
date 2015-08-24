package com.explore.archana.nanoproject.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.adapter.GridImageAdapter;
import com.explore.archana.nanoproject.listeners.ILoadMoveDetail;
import com.explore.archana.nanoproject.model.MovieData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by archana on 7/28/2015.
 */
public class MovieListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ArrayList<MovieData> movieDataArrayList;
    private GridImageAdapter gridImageAdapter;
    ILoadMoveDetail loadMoveDetail;

    private String YOUR_API_KEY = "api_key=Enter your API here";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            loadMoveDetail = (ILoadMoveDetail) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+"must implement listener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (view == null)
            view = inflater.inflate(R.layout.movielist_layout, null);

        getActivity().getActionBar().setTitle(R.string.pop_movies);
        GridView gridView = (GridView) view.findViewById(R.id.movies_gridview);

        movieDataArrayList = new ArrayList<MovieData>();
        gridImageAdapter = new GridImageAdapter(getActivity(),R.layout.grid_item_layout,movieDataArrayList);
        gridView.setAdapter(gridImageAdapter);

        new DownloadMovies().execute(getActivity().getString(R.string.movies_api)+YOUR_API_KEY);

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
                movieDataArrayList.clear();
                Log.e("tag to check arraylist", "tag to check arraylist pop movies" + movieDataArrayList.size());
               new DownloadMovies().execute(getActivity().getString(R.string.movies_api)+YOUR_API_KEY);
                break;
            case R.id.highest_rated:
                movieDataArrayList.clear();
                getActivity().getActionBar().setTitle(R.string.top_movies);
                new DownloadMovies().execute(getActivity().getString(R.string.top_movie_api)+"&"+YOUR_API_KEY);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MovieData data = (MovieData) parent.getItemAtPosition(position);
        loadMoveDetail.onMovieSelected(position,data);

    }

    private class DownloadMovies extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            return downloadUrl(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("tag to check arraylist","tag to check arraylist"+movieDataArrayList.size());
            gridImageAdapter.setGridData(movieDataArrayList);
        }


        private String downloadUrl(String mUrl) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL(mUrl);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    Log.e("Debug url", line + "\n");
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();
                parseMovies(moviesJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            return moviesJsonStr;
        }

        private void parseMovies(String moviesJsonStr) {
            try {
                JSONObject object = new JSONObject(moviesJsonStr);
                JSONArray array = object.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    MovieData movieData = new MovieData();
                    JSONObject movie = array.getJSONObject(i);
                    //data from api
                    String movieImage = movie.getString("poster_path");
                    String movieName = movie.getString("original_title");
                    String movieOverview = movie.getString("overview");
                    String movieReseaseDate = movie.getString("release_date");
                    String movieVote = movie.getString("vote_average");

                    //setting data to movie model class
                    movieData.setMovieImage(movieImage);
                    movieData.setMovieName(movieName);
                    movieData.setMovieOverview(movieOverview);
                    movieData.setMovieVote(movieVote);
                    movieData.setMovieReleaseYear(movieReseaseDate);

                    //Adding modelclass object to arraylist
                    movieDataArrayList.add(movieData);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
