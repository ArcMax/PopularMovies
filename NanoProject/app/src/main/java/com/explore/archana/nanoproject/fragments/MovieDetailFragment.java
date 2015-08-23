package com.explore.archana.nanoproject.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.model.MovieData;
import com.squareup.picasso.Picasso;

/**
 * Created by archana on 7/30/2015.
 */
public class MovieDetailFragment extends Fragment {

    private MovieData movieData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (view == null)
            view = inflater.inflate(R.layout.movie_detail_layout, null);

        TextView mMovieTitle = (TextView) view.findViewById(R.id.movie_name);
        ImageView mMovieTumb = (ImageView) view.findViewById(R.id.movie_tumb);
        TextView mMovieYear = (TextView) view.findViewById(R.id.textView_year);
        TextView mMovieVote = (TextView) view.findViewById(R.id.textView_ratings);
        TextView mMovieOverview = (TextView) view.findViewById(R.id.movie_overview);

        Bundle bundle = getArguments();
        if (bundle != null || movieData != null) {
            try {
                movieData = (MovieData) bundle.getSerializable("MovieObject");
            } catch (NullPointerException e) {
                getActivity().getActionBar().setTitle("Movie Details");
            }

            String imageUrl = "http://image.tmdb.org/t/p/w500";
            Picasso.with(getActivity()).load(imageUrl + movieData.getMovieImage()).into(mMovieTumb);
            String year = movieData.getMovieReleaseYear();
            mMovieYear.setText(year.substring(0, 4));
            mMovieVote.setText(movieData.getMovieVote() + "/10");
            mMovieTitle.setText(movieData.getMovieName());
            mMovieOverview.setText(movieData.getMovieOverview());
        }
        return view;


    }

    public void setMovieData(MovieData data) {
        this.movieData = data;
    }
}
