package com.luitelsubash.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Subash on 12/12/16.
 */

public class TrailersAdapter extends ArrayAdapter<Trailer> {


    Context context;

    public TrailersAdapter(Context context, ArrayList<Trailer> items) {
        super(context, 0, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Trailer trailer = getItem(position);
        ImageView playIconView;
        TextView trailerNameView;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.trailers_list_layout, null);
        }
        trailerNameView = (TextView) convertView.findViewById(R.id.trailer_name);
        trailerNameView.setText(trailer.name);

        return convertView;
    }
}
