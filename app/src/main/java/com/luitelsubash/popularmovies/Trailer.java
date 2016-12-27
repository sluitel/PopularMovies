package com.luitelsubash.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Subash on 12/12/16.
 */

public class Trailer implements Parcelable {

    private  final String LOG_TAG = Trailer.class.getSimpleName();
    final String VIDEO_URL_BASE = "https://www.youtube.com/watch?v=";

    final private String KEY_PARAM = "key";
    final private String NAME_PARAM = "name";
    final private String SITE_PARAM = "site";

    String key;
    String name;
    String site;
    String videoPath;


    public Trailer(JSONObject trailerInfoObject) {
        try {
            this.key = trailerInfoObject.getString(KEY_PARAM);
            this.name = trailerInfoObject.getString(NAME_PARAM);
            this.site = trailerInfoObject.getString(SITE_PARAM);
            this.videoPath = VIDEO_URL_BASE + trailerInfoObject.getString(KEY_PARAM);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public Trailer(Parcel in) {
        key = in.readString();
        name = in.readString();
        site = in.readString();
        videoPath = in.readString();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(videoPath);
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel parcel) {
            return new Trailer(parcel);
        }

        @Override
        public Trailer[] newArray(int i) {
            return new Trailer[i];
        }
    };
}
