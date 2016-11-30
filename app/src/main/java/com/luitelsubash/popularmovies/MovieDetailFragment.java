package com.luitelsubash.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailFragment extends Fragment {


    public MovieDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ImageView moviePosterView = (ImageView) root.findViewById(R.id.movie_poster);
        TextView title = (TextView) root.findViewById(R.id.movie_title);
        TextView synopsis = (TextView) root.findViewById(R.id.movie_synopsis);
        TextView rating = (TextView) root.findViewById(R.id.movie_rating);
        TextView releaseDate = (TextView) root.findViewById(R.id.movie_release_date);

        Movie movie = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        Picasso.with(getActivity()).load(movie.thumbnailPath).into(moviePosterView);
        title.setText(movie.title);
        synopsis.setText(movie.synopsis);
        rating.setText(Double.toString(movie.rating));
        releaseDate.setText(movie.releaseDate);

        return root;
    }
}
