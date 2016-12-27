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
 * Created by Subash on 12/19/16.
 */

public class ReviewsAdapter extends ArrayAdapter<Review> {

    Context context;

    public ReviewsAdapter(Context context, ArrayList<Review> items) {
        super(context, 0, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Review review = getItem(position);
        TextView reviewTitleView;
        TextView reviewContentView;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.reviews_list_layout, null);
        }
        reviewTitleView = (TextView) convertView.findViewById(R.id.review_title);
        reviewTitleView.setText(review.author);

        reviewContentView = (TextView) convertView.findViewById(R.id.review_content);
        reviewContentView.setText(review.text);

        return convertView;
    }
}
