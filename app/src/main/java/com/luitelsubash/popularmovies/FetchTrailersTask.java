package com.luitelsubash.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

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

/**
 * Created by Subash on 12/14/16.
 */

public class FetchTrailersTask extends AsyncTask<Void, Void, ArrayList<Trailer>> {

    private  final String LOG_TAG = FetchTrailersTask.class.getSimpleName();
    final String RESULTS_PARAM = "results";

    private OnMovieDetailDownloadComplete listener;
    private Context context;
    private int movieId;

    public FetchTrailersTask (int movieId, OnMovieDetailDownloadComplete listener, Context context) {
        this.listener = listener;
        this.context = context;
        this.movieId = movieId;
    }

    @Override
    protected ArrayList<Trailer> doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String trailersJSONString = null;

        try {
            urlConnection = (HttpURLConnection) getRequestURL().openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            trailersJSONString = buffer.toString();
            try {
                JSONObject object = new JSONObject(trailersJSONString);
                JSONArray trailersJSONArray = object.getJSONArray(RESULTS_PARAM);
                return getTrailerArrayFromJSONArray(trailersJSONArray);

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Trailer> trailers) {

        super.onPostExecute(trailers);
        listener.onMovieTrailersDownloadCompleted(trailers);
    }

    private URL getRequestURL() {
        String language = "en-US";
        String apiKey = "98b5cc62c0718d727aa6e70ce2488e90";

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String movieIdString = Integer.toString(movieId);

        final String QUERY_BASE_URL = "https://api.themoviedb.org/3/movie/" + movieIdString + "/videos?";
        final String API_KEY_PARAM = "api_key";
        final String LANGUAGE_PARAM = "language";

        Uri builtUri = Uri.parse(QUERY_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .build();
        try {
            return new URL(builtUri.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Trailer> getTrailerArrayFromJSONArray(JSONArray mJSONArray) {
        ArrayList<Trailer> trailersArray = new ArrayList<Trailer>();
        for (int i=0; i<mJSONArray.length(); i++) {
            try {
                JSONObject object = mJSONArray.getJSONObject(i);
                Trailer trailer = new Trailer(object);
                trailersArray.add(trailer);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return trailersArray;
    }
}
