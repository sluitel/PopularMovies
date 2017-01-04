package com.luitelsubash.popularmovies;

import android.content.Context;
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
 * Created by Subash on 12/1/16.
 */

public class FetchMoviesTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private  final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    final String RESULTS_PARAM = "results";

    private OnDownloadComplete listener;
    private Context context;

    public FetchMoviesTask(OnDownloadComplete listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String moviesJSONString = null;

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
            moviesJSONString = buffer.toString();
            Log.v("JSON", moviesJSONString);
            try {
                JSONObject object = new JSONObject(moviesJSONString);
                JSONArray moviesJSONArray = object.getJSONArray(RESULTS_PARAM);
                return getMovieArrayFromJSONArray(moviesJSONArray);

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
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        listener.onMoviesDownloadCompleted(movies);
    }

    private URL getRequestURL() {

        String language = "en-US";
        String apiKey = "YOUR_API_KEY";
        int page = 1;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sort = prefs.getString(context.getResources().getString(R.string.pref_sort_key), context.getResources().getString(R.string.pref_sort_popular));

        final String QUERY_BASE_URL = "https://api.themoviedb.org/3/movie/" + sort + "?";
        final String API_KEY_PARAM = "api_key";
        final String LANGUAGE_PARAM = "language";
        final String PAGE_PARAM = "page";

        Uri builtUri = Uri.parse(QUERY_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(PAGE_PARAM, Integer.toString(page))
                .build();
        try {
            return new URL(builtUri.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Movie> getMovieArrayFromJSONArray(JSONArray mJSONArray) {
        ArrayList<Movie> moviesArray = new ArrayList<Movie>();
        for (int i=0; i<mJSONArray.length(); i++) {
            try {
                JSONObject object = mJSONArray.getJSONObject(i);
                moviesArray.add(new Movie(object));
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return moviesArray;
    }

}

