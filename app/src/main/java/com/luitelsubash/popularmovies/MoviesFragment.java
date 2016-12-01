package com.luitelsubash.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class MoviesFragment extends Fragment implements OnDownloadComplete {

    MoviesAdapter moviesAdapter = null;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        if (moviesAdapter == null) {
            // create and assign adapter to grid view
            moviesAdapter = new MoviesAdapter(getActivity(), new ArrayList<Movie>());
        }
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(moviesAdapter);

        // Handle click on grid view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie clickedMovie = moviesAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, clickedMovie);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void loadMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask(this, getActivity());
        moviesTask.execute();
    }

    @Override
    public void onMoviesDownloadCompleted(ArrayList<Movie> movies) {
        if (movies != null) {
            moviesAdapter.clear();
            moviesAdapter.addAll(movies);
        }
        else
            Toast.makeText(getActivity(),
                    "Something went wrong. Please check your internet connection and try again.",
                    Toast.LENGTH_LONG)
                    .show();
    }
}
