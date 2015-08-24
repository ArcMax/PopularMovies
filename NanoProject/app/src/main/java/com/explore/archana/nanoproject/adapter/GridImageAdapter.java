package com.explore.archana.nanoproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.model.MovieData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by archana on 8/13/2015.
 */
public class GridImageAdapter extends ArrayAdapter<MovieData> {

    private Context mContext;
    private int mLayoutResourceId;
    private ArrayList<MovieData> movieDataArrayList = new ArrayList<MovieData>();

    public GridImageAdapter(Context context, int resource, ArrayList<MovieData> movieDatas) {
        super(context, resource, movieDatas);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.movieDataArrayList = movieDatas;
        notifyDataSetChanged();
    }

    public void setGridData(ArrayList<MovieData> mGridData) {
        Log.e("tag to check arraylist","tag to check arraylist setGridData"+movieDataArrayList.size());
        this.movieDataArrayList = mGridData;
        Log.e("tag to check arraylist", "tag to check arraylist setGridData addall" + movieDataArrayList.size());
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

        MovieData data = movieDataArrayList.get(position);
        Log.e("Movie Image", "Movie Image" + "http://image.tmdb.org/t/p/w185" + data.getMovieImage());
        String imageUrl = "http://image.tmdb.org/t/p/w185";
        Picasso.with(mContext).load(imageUrl+data.getMovieImage()).into(holder.imageView);

        return row;
    }

    @Override
    public int getCount() {
        return movieDataArrayList.size();
    }

    @Override
    public MovieData getItem(int position) {
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
