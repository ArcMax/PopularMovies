package com.explore.archana.nanoproject.ui.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.model.MovieReviewResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archana on 9/28/2015.
 */
public class MovieReviewAdapter extends ArrayAdapter<MovieReviewResults> {

    private List<MovieReviewResults> reviewResultsList;
    private Context mContext;
    private int mLayoutResourse;
    public MovieReviewAdapter(Context context, int resource, List<MovieReviewResults> resultsList) {
        super(context, resource, resultsList);
        this.mContext = context;
        this.mLayoutResourse = resource;
        this.reviewResultsList = resultsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if(row==null){
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            row=inflater.inflate(mLayoutResourse,parent,false);
            holder = new ViewHolder();
            holder.reviewUserName = (TextView)row.findViewById(R.id.reviewer);
            holder.reviewUserName.setText(reviewResultsList.get(position).getAuthor());
            holder.review = (TextView)row.findViewById(R.id.review);
            holder.review.setText(reviewResultsList.get(position).getContent());
            row.setTag(holder);
        }else {
            holder = (ViewHolder)row.getTag();
        }

        return row;
    }

    class ViewHolder{
        TextView reviewUserName;
        TextView review;
    }
}
