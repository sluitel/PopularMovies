package com.luitelsubash.popularmovies;

import java.util.ArrayList;

/**
 * Created by Subash on 12/1/16.
 */

public interface OnDownloadComplete {
    void onMoviesDownloadCompleted(ArrayList<Movie> movies);
}
