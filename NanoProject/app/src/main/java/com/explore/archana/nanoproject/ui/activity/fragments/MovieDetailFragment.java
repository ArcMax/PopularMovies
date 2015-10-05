package com.explore.archana.nanoproject.ui.activity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.app.App;
import com.explore.archana.nanoproject.model.MovieResultsModel;
import com.explore.archana.nanoproject.model.MovieReviewResults;
import com.explore.archana.nanoproject.model.MovieTrailersResult;
import com.explore.archana.nanoproject.provider.MoviesProvider;
import com.explore.archana.nanoproject.provider.MoviesSQLiteOpenHelper;
import com.explore.archana.nanoproject.rest.model.MovieDataModel;
import com.explore.archana.nanoproject.rest.model.MovieTrailers;
import com.explore.archana.nanoproject.rest.model.MoviesReviews;
import com.explore.archana.nanoproject.ui.activity.MovieListActivity;
import com.explore.archana.nanoproject.ui.activity.adapter.MovieReviewAdapter;
import com.explore.archana.nanoproject.ui.activity.adapter.MovieTrailerAdapter;
import com.explore.archana.nanoproject.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by archana on 7/30/2015.
 */
public class MovieDetailFragment extends Fragment implements OnClickListener {

    public static final String TAG = MovieDetailFragment.class.getSimpleName();
    public static final String DETAILURI = "URI";
    private MovieResultsModel movieData;
    ListView mTrailersListView, mReviewListView;
    private MovieTrailerAdapter trailerAdapter;
    private MovieReviewAdapter reviewAdapter;
    private ToggleButton mMovieFavorate;
    private Cursor mCursor;
    private MovieTrailers trailers;
    private MoviesReviews reviews;
    private String mId;
    public static Boolean isFirstTime = false;
    private static Bundle firstTime;


    public static MovieDetailFragment newInstance(MovieDataModel index){
        MovieDetailFragment detailFragment = new MovieDetailFragment();
        firstTime = new Bundle();
        firstTime.putParcelable(DETAILURI, index.getResults().get(0));
        detailFragment.setArguments(firstTime);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(MovieListActivity.passCursor!=null)
            mCursor = MovieListActivity.passCursor;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DETAILURI, movieData);
        outState.putParcelable("trailers", trailers);
        outState.putParcelable("reviews", reviews);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_detail_layout, null);
        if(!isFirstTime){
            movieData = firstTime.getParcelable(DETAILURI);
            isFirstTime = true;
            Log.d("first time data","first time data"+movieData);

        }else {
            try {
                Bundle bundle = getArguments();
                movieData = bundle.getParcelable(DETAILURI);
                trailers = bundle.getParcelable("trailers");
                reviews = bundle.getParcelable("reviews");
                Log.d(TAG, "bundle data" + movieData + "---" + trailers + "---" + reviews);
            } catch (NullPointerException e) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Movie Details");
            }
        }

        TextView mMovieTitle = (TextView) view.findViewById(R.id.movie_name);
        ImageView mMovieTumb = (ImageView) view.findViewById(R.id.movie_tumb);
        TextView mMovieYear = (TextView) view.findViewById(R.id.textView_year);
        TextView mMovieVote = (TextView) view.findViewById(R.id.textView_ratings);
        TextView mMovieOverview = (TextView) view.findViewById(R.id.movie_overview);
        mMovieFavorate = (ToggleButton) view.findViewById(R.id.button_markasfav);
        mMovieFavorate.setOnClickListener(this);
        mTrailersListView = (ListView) view.findViewById(R.id.listview_trailers_moviedetail);
        mReviewListView = (ListView) view.findViewById(R.id.listview_reviews_moviedetail);
        Log.d("mCursor", "mCursor" + mCursor);
        if (movieData!=null) {
            Log.d("Movie data", "Movie data" + movieData);
            String imageUrl = "http://image.tmdb.org/t/p/w500";
            Picasso.with(getActivity()).load(imageUrl + movieData.getPosterPath()).into(mMovieTumb);
            String year = movieData.getReleaseDate();
            mMovieYear.setText(year.substring(0, 4));
            mMovieVote.setText(movieData.getVoteAverage() + "/10");
            mMovieTitle.setText(movieData.getTitle());
            mMovieOverview.setText(movieData.getOverview());
            mMovieFavorate.setChecked(movieData.getFavorate());
            setTrailers();
            setReviews();

        } else if(mCursor !=null){
            mId = mCursor.getString(mCursor.getColumnIndex(MoviesSQLiteOpenHelper.ID));
            String title = mCursor.getString(mCursor.getColumnIndex(MoviesSQLiteOpenHelper.TITLE));
            byte[] posterpath = mCursor.getBlob(mCursor.getColumnIndex(MoviesSQLiteOpenHelper.POSTER_PATH));
            String releasedate = mCursor.getString(mCursor.getColumnIndex(MoviesSQLiteOpenHelper.RELEASE_DATE));
            String voteaverage = mCursor.getString(mCursor.getColumnIndex(MoviesSQLiteOpenHelper.VOTE_AVERAGE));
            String overview = mCursor.getString(mCursor.getColumnIndex(MoviesSQLiteOpenHelper.OVERVIEW));

            mMovieTumb.setImageBitmap(Utility.getImage(posterpath));
            mMovieYear.setText(releasedate.substring(0, 4));
            mMovieVote.setText(voteaverage + "/10");
            mMovieTitle.setText(title);
            mMovieOverview.setText(overview);
            mMovieFavorate.setChecked(true);
            setTrailers();
            setReviews();
        }

        return view;
    }



    @Override
    public void onClick(View v) {
        if (mMovieFavorate.isChecked() ) {
            new ImageToDB().execute();
            movieData.setFavorate(true);
        } else {
            Uri.Builder uriBuilder = MoviesProvider.CONTENT_URI.buildUpon();
            if (mCursor == null)
                mId = movieData.getId().toString();
            getActivity().getContentResolver().delete(uriBuilder.build(), mId, null);
            if(mCursor!=null)getActivity().onBackPressed();
        }
    }

    public void setCursor(Cursor cursor) {
        this.mCursor = cursor;
    }

    private void setTrailers() {
        if (movieData != null) {
            App.getRestClient().getIApiCallService().getMovieTrailers(movieData.getId(), new Callback<MovieTrailers>() {
                @Override
                public void success(MovieTrailers movieTrailers, Response response) {
                    trailers = movieTrailers;
                    trailerAdapter = new MovieTrailerAdapter(getActivity(), R.layout.list_item_trailer_layout, movieTrailers.getResults());
                    mTrailersListView.setAdapter(trailerAdapter);
                    Utility.setListViewHeightBasedOnChildren(mTrailersListView);
                }

                @Override
                public void failure(RetrofitError error) {
                    Crouton.makeText(getActivity(), error.getMessage(), Style.ALERT).show();
                }
            });
        } else {
            MovieTrailers trailer = (MovieTrailers) new Gson().fromJson(mCursor.getString(mCursor.getColumnIndex(MoviesSQLiteOpenHelper.TRAILERS)), MovieTrailers.class);
            trailerAdapter = new MovieTrailerAdapter(getActivity(), R.layout.list_item_trailer_layout, trailer.getResults());
            mTrailersListView.setAdapter(trailerAdapter);
            Utility.setListViewHeightBasedOnChildren(mTrailersListView);

        }
    }

    private void setReviews() {
        if (movieData != null) {
            App.getRestClient().getIApiCallService().getMovieReviews(movieData.getId(), new Callback<MoviesReviews>() {
                @Override
                public void success(MoviesReviews moviesReviews, Response response) {
                    reviews = moviesReviews;
                    reviewAdapter = new MovieReviewAdapter(getActivity(), R.layout.list_item_review_layout, moviesReviews.getResults());
                    mReviewListView.setAdapter(reviewAdapter);
                    Utility.setListViewHeightBasedOnChildren(mReviewListView);
                }

                @Override
                public void failure(RetrofitError error) {
                    Crouton.makeText(getActivity(), error.getMessage(), Style.ALERT).show();
                }
            });
        } else {
            MoviesReviews review = (MoviesReviews) new Gson().fromJson(mCursor.getString(mCursor.getColumnIndex(MoviesSQLiteOpenHelper.REVIEWS)), MoviesReviews.class);
            reviewAdapter = new MovieReviewAdapter(getActivity(), R.layout.list_item_review_layout, review.getResults());
            mReviewListView.setAdapter(reviewAdapter);
            Utility.setListViewHeightBasedOnChildren(mReviewListView);
        }

    }

    class ImageToDB extends AsyncTask<Void, Void, ByteArrayOutputStream> {
        ByteArrayOutputStream stream;

        @Override
        protected ByteArrayOutputStream doInBackground(Void... params) {
            try {
                InputStream is = new URL("http://image.tmdb.org/t/p/w500" + movieData.getPosterPath()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stream;
        }

        @Override
        protected void onPostExecute(ByteArrayOutputStream byteArrayOutputStream) {
            Log.e("stream", "stream" + byteArrayOutputStream.toByteArray());
            ContentValues contentValues = new ContentValues();
            contentValues.put(MoviesSQLiteOpenHelper.ID, movieData.getId());
            contentValues.put(MoviesSQLiteOpenHelper.TITLE, movieData.getTitle());
            contentValues.put(MoviesSQLiteOpenHelper.RELEASE_DATE, movieData.getReleaseDate());
            contentValues.put(MoviesSQLiteOpenHelper.POSTER_PATH, byteArrayOutputStream.toByteArray());
            contentValues.put(MoviesSQLiteOpenHelper.VOTE_AVERAGE, Double.toString(movieData.getVoteAverage()));
            contentValues.put(MoviesSQLiteOpenHelper.OVERVIEW, movieData.getOverview());
            Gson gson = new Gson();
            String tString = gson.toJson(trailers);
            String rString = gson.toJson(reviews);
            contentValues.put(MoviesSQLiteOpenHelper.TRAILERS, tString);
            contentValues.put(MoviesSQLiteOpenHelper.REVIEWS, rString);
            Uri uri = getActivity().getContentResolver().insert(MoviesProvider.CONTENT_URI, contentValues);
        }
    }

}
