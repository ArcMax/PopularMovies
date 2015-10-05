package com.explore.archana.nanoproject.rest.model;

/**
 * Created by archana on 9/24/2015.
 */

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.explore.archana.nanoproject.model.MovieResultsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MovieDataModel implements Parcelable {
    private static final String KEY_PAGE = "page";
    private static final String KEY_TOTALPAGES = "totalpages";
    private static final String KEY_TOTALRESULTS = "totalresults";
    private static final String KEY_RESULTS = "results";

    public MovieDataModel() {
    }

    public MovieDataModel(Integer page, List<MovieResultsModel> results, Integer totalPages, Integer totalResults) {
        this.page = page;
        this.results = results;

        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<MovieResultsModel> results = new ArrayList<MovieResultsModel>();
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    /**
     * @return The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return The results
     */
    public List<MovieResultsModel> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<MovieResultsModel> results) {
        this.results = results;
    }

    /**
     * @return The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * @return The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults The total_results
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
        bundle.putInt(KEY_PAGE,page);
        bundle.putInt(KEY_TOTALPAGES,totalPages);
        bundle.putInt(KEY_TOTALRESULTS,totalResults);
        bundle.putParcelableArrayList(KEY_RESULTS, (ArrayList<? extends Parcelable>) results);

        dest.writeBundle(bundle);
    }
    /**
     *
     * Called from the constructor to create this
     * object from a parcel.
     *
     * @param in parcel from which to re-create object
     */
    private void readFromParcel(Parcel in) {

        Bundle bundle = new Bundle();
        bundle.getInt(KEY_PAGE);
        bundle.getInt(KEY_TOTALRESULTS);
        bundle.getInt(KEY_TOTALPAGES);
        bundle.getParcelableArrayList(KEY_RESULTS);
        in.readBundle();
    }

    public static final Parcelable.Creator<MovieDataModel> CREATOR = new Creator<MovieDataModel>() {
        @Override
        public MovieDataModel createFromParcel(Parcel source) {
            MovieDataModel model = new MovieDataModel();
            model.page = source.readInt();
            model.totalResults = source.readInt();
            model.totalPages = source.readInt();
            Bundle bundle = source.readBundle(MovieResultsModel.class.getClassLoader());
            model.results = bundle.getParcelableArrayList(KEY_RESULTS);
            return model;
        }

        @Override
        public MovieDataModel[] newArray(int size) {
            return new MovieDataModel[size];
        }
    };
}