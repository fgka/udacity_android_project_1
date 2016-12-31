package app.operativus.com.popularmovies.json.impl;


import android.support.annotation.NonNull;
import android.util.Log;

import app.operativus.com.popularmovies.data.MovieItem;
import app.operativus.com.popularmovies.json.JSONParser;

public class JSONMovieParser implements JSONParser<MovieItem> {

    private static final String LOG_TAG = JSONMovieParser.class.getSimpleName();

    @Override
    public final MovieItem parse(@NonNull String json) {
        MovieItem result = null;
        Log.d(LOG_TAG, "JSON input: " + json);
        //TODO
        throw new UnsupportedOperationException();
    }
}
