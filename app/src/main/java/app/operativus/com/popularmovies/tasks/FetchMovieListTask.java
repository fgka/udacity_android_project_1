package app.operativus.com.popularmovies.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import app.operativus.com.popularmovies.adapters.MovieListAdapter;
import app.operativus.com.popularmovies.data.MovieItem;
import app.operativus.com.popularmovies.data.MovieListRanking;

public class FetchMovieListTask extends AsyncTask<MovieListRanking, Void, List<MovieItem>> {

    private static final String LOG_TAG = FetchMovieListTask.class.getSimpleName();

    private final MovieListAdapter movieListAdapter;

    public FetchMovieListTask(@NonNull MovieListAdapter movieListAdapter) {
        this.movieListAdapter = movieListAdapter;
    }

    @Override
    protected List<MovieItem> doInBackground(@NonNull MovieListRanking... params) {
        List<MovieItem> result = null;

        if (params.length == 0) {
            Log.w(LOG_TAG, "Requesting movie list with no ranking provided!");
        } else {
            result = QueryTheMovieDB.getMovieListByRanking(params[0]);
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<MovieItem> movieItems) {
        if (movieItems != null) {
            this.movieListAdapter.clear();
            this.movieListAdapter.addAll(movieItems);
        } else {
            Log.w(LOG_TAG, "Resulting movie list is null!");
        }
    }
}
