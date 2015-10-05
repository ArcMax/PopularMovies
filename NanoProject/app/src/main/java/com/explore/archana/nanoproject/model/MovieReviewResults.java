package com.explore.archana.nanoproject.model;

/**
 * Created by archana on 9/27/2015.
 */
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class MovieReviewResults implements Parcelable{
    public static final String KEY_ID = "id";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_URL = "url";

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     * The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ID,id);
        bundle.putString(KEY_AUTHOR,author);
        bundle.putString(KEY_CONTENT,content);
        bundle.putString(KEY_URL,url);
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<MovieReviewResults> CREATOR = new Creator<MovieReviewResults>() {
        @Override
        public MovieReviewResults createFromParcel(Parcel source) {
            MovieReviewResults model = new MovieReviewResults();
            model.id = source.readString();
            model.author = source.readString();
            model.content = source.readString();
            model.url = source.readString();
            return model;
        }

        @Override
        public MovieReviewResults[] newArray(int size) {
            return new MovieReviewResults[size];
        }
    };
}