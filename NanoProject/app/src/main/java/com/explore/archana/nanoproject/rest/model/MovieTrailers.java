package com.explore.archana.nanoproject.rest.model;

/**
 * Created by archana on 9/26/2015.
 */

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.explore.archana.nanoproject.model.MovieTrailersResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MovieTrailers implements Parcelable {

    public static final String KEY_ID = "id";
    public static final String KEY_RESULTS = "results";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieTrailersResult> results = new ArrayList<MovieTrailersResult>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The results
     */
    public List<MovieTrailersResult> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<MovieTrailersResult> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID,id);
        bundle.putParcelableArrayList(KEY_RESULTS, (ArrayList<? extends Parcelable>) results);
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<MovieTrailers> CREATOR = new Creator<MovieTrailers>() {
        @Override
        public MovieTrailers createFromParcel(Parcel source) {
            MovieTrailers trailers = new MovieTrailers();
            trailers.id = source.readInt();
            Bundle bundle = source.readBundle(MovieTrailersResult.class.getClassLoader());
            trailers.results = bundle.getParcelableArrayList(KEY_RESULTS);
            return trailers;
        }

        @Override
        public MovieTrailers[] newArray(int size) {
            return new MovieTrailers[size];
        }
    };
}
