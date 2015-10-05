package com.explore.archana.nanoproject.rest.model;

/**
 * Created by archana on 9/27/2015.
 */

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.explore.archana.nanoproject.model.MovieReviewResults;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MoviesReviews implements Parcelable{
    public static final String KEY_ID = "id";
    public static final String KEY_PAGE = "page";
    public static final String KEY_TOTAL_PAGES="totalpages";
    public static final String KEY_TOTALRESULT = "totalresults";
    public static final String KEY_RESULTS = "results";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<MovieReviewResults> results = new ArrayList<MovieReviewResults>();
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

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
     * The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The results
     */
    public List<MovieReviewResults> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<MovieReviewResults> results) {
        this.results = results;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     * The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     * The total_results
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID,id);
        bundle.putInt(KEY_PAGE,page);
        bundle.putInt(KEY_TOTAL_PAGES,totalPages);
        bundle.putInt(KEY_TOTALRESULT,totalResults);
        bundle.putParcelableArrayList(KEY_RESULTS, (ArrayList<? extends Parcelable>) results);
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<MoviesReviews> CREATOR = new Creator<MoviesReviews>() {
        @Override
        public MoviesReviews createFromParcel(Parcel source) {
            MoviesReviews reviews = new MoviesReviews();
            reviews.id = source.readInt();
            reviews.page = source.readInt();
            reviews.totalPages = source.readInt();
            reviews.totalResults = source.readInt();
            Bundle bundle = source.readBundle(MovieReviewResults.class.getClassLoader());
            reviews.results =bundle.getParcelableArrayList(KEY_RESULTS);
            return null;
        }

        @Override
        public MoviesReviews[] newArray(int size) {
            return new MoviesReviews[size];
        }
    };
}