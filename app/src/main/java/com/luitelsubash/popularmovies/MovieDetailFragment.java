package com.luitelsubash.popularmovies;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieDetailFragment extends Fragment implements OnMovieDetailDownloadComplete {

    TrailersAdapter trailersAdapter = null;
    ReviewsAdapter reviewsAdapter = null;
    Movie movie;
    ListView trailersListView;
    ListView reviewsListView;

    public MovieDetailFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        root.setBackgroundColor(Color.WHITE);
        ImageView moviePosterView = (ImageView) root.findViewById(R.id.movie_poster);
        TextView title = (TextView) root.findViewById(R.id.movie_title);
        TextView synopsis = (TextView) root.findViewById(R.id.movie_synopsis);
        TextView rating = (TextView) root.findViewById(R.id.movie_rating);
        TextView releaseDate = (TextView) root.findViewById(R.id.movie_release_date);

        movie = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        Picasso.with(getActivity()).load(movie.thumbnailPath).into(moviePosterView);
        title.setText(movie.title);
        synopsis.setText(movie.synopsis);
        rating.setText(Double.toString(movie.rating) + "/10");
        releaseDate.setText(movie.releaseYear);

        if (trailersAdapter == null) {
            // create and assign adapter to grid view
            trailersAdapter = new TrailersAdapter(getActivity(), new ArrayList<Trailer>());
        }
        trailersListView = (ListView) root.findViewById(R.id.trailers_list_view);
        trailersListView.setAdapter(trailersAdapter);
        loadTrailers();

        if (reviewsAdapter == null) {
            reviewsAdapter = new ReviewsAdapter(getActivity(), new ArrayList<Review>());
        }
        reviewsListView = (ListView) root.findViewById(R.id.reviews_list_view);
        reviewsListView.setAdapter(reviewsAdapter);
        loadReviews();

        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Trailer clickedTrailer = trailersAdapter.getItem(i);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(clickedTrailer.videoPath));
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onMovieTrailersDownloadCompleted(ArrayList<Trailer> trailers) {
        if (trailers != null) {
            trailersAdapter.clear();
            trailersAdapter.addAll(trailers);
//            justifyListViewHeightBasedOnChildren(trailersListView);

        }
        else
            Toast.makeText(getActivity(),
                    "Something went wrong. Please check your internet connection and try again.",
                    Toast.LENGTH_LONG)
                    .show();
    }

    @Override
    public void onMovieReviewsDownloadCompleted(ArrayList<Review> reviews) {
        if (reviews != null) {
            reviewsAdapter.clear();
            reviewsAdapter.addAll(reviews);
//            justifyListViewHeightBasedOnChildren(reviewsListView);

        }
        else
            Toast.makeText(getActivity(),
                    "Something went wrong. Please check your internet connection and try again.",
                    Toast.LENGTH_LONG)
                    .show();
    }

    private void loadTrailers() {
        FetchTrailersTask trailersTask = new FetchTrailersTask(movie.id, this, getActivity());
        trailersTask.execute();
    }

    private void loadReviews() {
        FetchReviewsTask reviewsTask = new FetchReviewsTask(movie.id, this, getActivity());
        reviewsTask.execute();
    }

//    public void justifyListViewHeightBasedOnChildren (ListView listView) {
//
//        if (listView.getAdapter() == null) {
//            return;
//        }
//        ViewGroup vg = listView;
//        int totalHeight = 0;
//        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
//            View listItem = listView.getAdapter().getView(i, null, vg);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams par = listView.getLayoutParams();
//        par.height = totalHeight + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1));
//        listView.setLayoutParams(par);
//        listView.requestLayout();
//    }
}
