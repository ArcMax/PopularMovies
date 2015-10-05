package com.explore.archana.nanoproject.ui.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.explore.archana.nanoproject.R;
import com.explore.archana.nanoproject.provider.MoviesSQLiteOpenHelper;
import com.explore.archana.nanoproject.utils.Utility;
import com.squareup.picasso.Picasso;

import java.sql.Blob;

/**
 * Created by archana on 9/30/2015.
 */
public class FavoriteGridAdapter extends CursorAdapter {

    private Context mContext;
    public FavoriteGridAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.mContext = context;
        notifyDataSetChanged();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = null;
        LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
        v = layoutInflater.inflate(R.layout.grid_item_layout, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        byte[] posterpath = cursor.getBlob(cursor.getColumnIndex(MoviesSQLiteOpenHelper.POSTER_PATH));

        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewMoviePoster);
        imageView.setImageBitmap(Utility.getImage(posterpath));
    }
}
