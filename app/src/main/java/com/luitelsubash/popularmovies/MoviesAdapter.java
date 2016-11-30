package com.luitelsubash.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subash on 11/29/16.
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {


    Context context;

    public MoviesAdapter(Context context, ArrayList<Movie> items) {
        super(context, 0, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);
        ImageView thumbnailView;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_item_layout, null);
            thumbnailView = (ImageView) convertView.findViewById(R.id.grid_thumbnail);
            convertView.setTag(thumbnailView);
        } else
            thumbnailView = (ImageView) convertView.getTag();

        // Download Image
        String thumbnailURLString = movie.thumbnailPath;
        Picasso.with(context).load(thumbnailURLString).into(thumbnailView);

        return convertView;
    }
}
