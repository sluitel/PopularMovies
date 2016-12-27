package com.luitelsubash.popularmovies;

import java.util.ArrayList;

/**
 * Created by Subash on 12/12/16.
 */

public interface OnMovieDetailDownloadComplete {
    void onMovieTrailersDownloadCompleted(ArrayList<Trailer> trailers);
    void onMovieReviewsDownloadCompleted(ArrayList<Review> reviews);
}
