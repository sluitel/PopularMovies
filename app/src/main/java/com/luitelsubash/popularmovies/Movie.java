package com.luitelsubash.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Subash on 11/29/16.
 */

public class Movie implements Parcelable {

    private  final String LOG_TAG = Movie.class.getSimpleName();
    final String THUMBNAIL_URL_BASE = "http://image.tmdb.org/t/p/";
    final String THUMBNAIL_SIZE_STRING = "w185";

    final private String ID_PARAM = "id";
    final private String TITLE_PARAM = "original_title";
    final private String THUMBNAIL_PATH_PARAM = "poster_path";
    final private String SYNOPSIS_PARAM = "overview";
    final private String RATING_PARAM = "vote_average";
    final private String RELEASE_DATE_PARAM = "release_date";

    int id;
    String title;
    String thumbnailPath;
    String synopsis;
    double rating;
    String releaseDate;
    String releaseYear;
    String length;


    public Movie(JSONObject movieInfoObject) {
        try {
            this.id = movieInfoObject.getInt(ID_PARAM);
            this.title = movieInfoObject.getString(TITLE_PARAM);
            this.thumbnailPath = THUMBNAIL_URL_BASE + THUMBNAIL_SIZE_STRING + movieInfoObject.getString(THUMBNAIL_PATH_PARAM);
            this.synopsis = movieInfoObject.getString(SYNOPSIS_PARAM);
            this.rating = movieInfoObject.getDouble(RATING_PARAM);
            this.releaseDate = movieInfoObject.getString(RELEASE_DATE_PARAM);
            this.releaseYear = releaseDate.substring(0, 4);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        thumbnailPath = in.readString();
        synopsis = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
        releaseYear = in.readString();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(thumbnailPath);
        parcel.writeString(synopsis);
        parcel.writeDouble(rating);
        parcel.writeString(releaseDate);
        parcel.writeString(releaseYear);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
