package com.explore.archana.nanoproject.ui.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.model.MovieTrailersResult;

import java.util.List;

/**
 * Created by archana on 9/26/2015.
 */
public class MovieTrailerAdapter extends ArrayAdapter<MovieTrailersResult> {

    private Context mContext;
    private int mLayoutResourseId;
    private List<MovieTrailersResult> movieTrailersResults;

    public MovieTrailerAdapter(Context context, int resource, List<MovieTrailersResult> trailersResults) {
        super(context, resource, trailersResults);
        this.mContext = context;
        this.mLayoutResourseId = resource;
        this.movieTrailersResults = trailersResults;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
            view = layoutInflater.inflate(mLayoutResourseId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextViewTrailers=(TextView)view.findViewById(R.id.list_item_trailer_textview);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

            final MovieTrailersResult result = movieTrailersResults.get(position);
            viewHolder.mTextViewTrailers.setText(result.getName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Trailer site", "Trailer site" + result.getName() + "-----" + result.getType() + "--------" + result.getKey());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + result.getKey()));
                    mContext.startActivity(intent);
                }
            });

        return view;
    }

    @Override
    public int getCount() {
        return movieTrailersResults.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MovieTrailersResult getItem(int position) {
        return movieTrailersResults.get(position);
    }

    class ViewHolder {
        TextView mTextViewTrailers;
    }
}
