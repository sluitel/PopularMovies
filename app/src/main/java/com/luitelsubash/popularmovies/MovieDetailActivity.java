package com.luitelsubash.popularmovies;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

public class MovieDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(getIntent().getExtras());
            fragmentTransaction.add(R.id.activity_movie_detail, fragment);
            fragmentTransaction.commit();
        }
    }
}
