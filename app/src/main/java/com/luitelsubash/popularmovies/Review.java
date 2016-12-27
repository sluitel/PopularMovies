package com.luitelsubash.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Subash on 12/15/16.
 */

public class Review implements Parcelable {

    private  final String LOG_TAG = Trailer.class.getSimpleName();
    final String VIDEO_URL_BASE = "https://www.youtube.com/watch?v=";

    final private String AUTHOR_PARAM = "author";
    final private String CONTENT_PARAM = "content";
    final private String URL_PARAM = "url";

    String author;
    String text;
    String path;

    public Review (JSONObject object) {
        try {
            this.author = object.getString(AUTHOR_PARAM);
            this.text = object.getString(CONTENT_PARAM);
            this.path = object.getString(URL_PARAM);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public Review(Parcel in) {
        author = in.readString();
        text = in.readString();
        path = in.readString();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(text);
        parcel.writeString(path);
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int i) {
            return new Review[i];
        }
    };
}
