package com.explore.archana.nanoproject.rest.service;

import com.explore.archana.nanoproject.rest.model.MovieDataModel;
import com.explore.archana.nanoproject.rest.model.MovieTrailers;
import com.explore.archana.nanoproject.rest.model.MoviesReviews;
import com.explore.archana.nanoproject.utils.Constants;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by archana on 9/24/2015.
 */
public interface IApiCallService {

    @GET("/3/discover/movie?api_key="+ Constants.API_KEY)
    public void getMoviesDataModel(@Query("sort_by") String sort , Callback<MovieDataModel> movieDataModelCallback);

    @GET("/3/movie/{id}/videos?api_key="+Constants.API_KEY)
    public void getMovieTrailers(@Path("id") int id,Callback<MovieTrailers> trailersCallback);

    @GET("/3/movie/{id}/reviews?api_key="+Constants.API_KEY)
    public void getMovieReviews(@Path("id") int id,Callback<MoviesReviews> reviewsCallback);
}
