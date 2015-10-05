package com.explore.archana.nanoproject.ui.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.model.MovieResultsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archana on 8/13/2015.
 */
public class GridImageAdapter extends ArrayAdapter<MovieResultsModel> {

    private Context mContext;
    private int mLayoutResourceId;
    private List<MovieResultsModel> movieDataArrayList = new ArrayList<MovieResultsModel>();

    public GridImageAdapter(Context context, int resource, List<MovieResultsModel> movieDatas) {
        super(context, resource, movieDatas);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.movieDataArrayList = movieDatas;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
            row = layoutInflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.imageViewMoviePoster);
            row.setTag(holder);
        }else{
            holder = (ViewHolder) row.getTag();
        }

        MovieResultsModel data = movieDataArrayList.get(position);
        Log.e("Movie Image", "Movie Image" + "http://image.tmdb.org/t/p/w185" + data.getPosterPath());
        String imageUrl = "http://image.tmdb.org/t/p/w185";
        Picasso.with(mContext).load(imageUrl + data.getPosterPath()).into(holder.imageView);

        return row;
    }

    @Override
    public int getCount() {
        return movieDataArrayList.size();
    }

    @Override
    public MovieResultsModel getItem(int position) {
        return movieDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        ImageView imageView;
    }
}
