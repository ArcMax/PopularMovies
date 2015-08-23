package com.explore.archana.nanoproject.model;

import java.io.Serializable;

/**
 * Created by archana on 8/14/2015.
 */
public class MovieData implements Serializable {

    private String movieId;
    private String movieImage;
    private String movieName;
    private String movieOverview;
    private String movieReleaseYear;
    private String movieVote;


    public String getMovieImage() {
        return movieImage;
    }

    public String getMovieVote() {
        return movieVote;
    }

    public void setMovieVote(String movieVote) {
        this.movieVote = movieVote;
    }

    public String getMovieOverview() {

        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieName() {

        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieId() {

        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieReleaseYear() {
        return movieReleaseYear;
    }

    public void setMovieReleaseYear(String movieReleaseYear) {
        this.movieReleaseYear = movieReleaseYear;
    }

}
