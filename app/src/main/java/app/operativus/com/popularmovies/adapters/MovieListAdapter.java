package app.operativus.com.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import app.operativus.com.popularmovies.apis.PosterImageLoader;
import app.operativus.com.popularmovies.data.MovieItem;
import app.operativus.com.popularmovies.data.MoviePosterImageSize;

public class MovieListAdapter extends ArrayAdapter<MovieItem> {

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();
    private static final MoviePosterImageSize DEFAULT_POSTER_SIZE = MoviePosterImageSize.W_185;

    private final MoviePosterImageSize posterSize;

    public MovieListAdapter(Context context, List<MovieItem> movieList, @NonNull MoviePosterImageSize posterSize) {
        super(context, 0, movieList);
        this.posterSize = posterSize;
    }

    public MovieListAdapter(Context context, List<MovieItem> movieList) {
        this(context, movieList, DEFAULT_POSTER_SIZE);
    }

    public MoviePosterImageSize getPosterSize() {
        return posterSize;
    }

    /**
     * Source: https://square.github.io/picasso/
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;

        if (view == null) {
            view = new ImageView(getContext());
        }
        MovieItem item = getItem(position);
        if (item == null) {
            throw new IllegalArgumentException("Movie item is null at position: " + position);
        }
        PosterImageLoader.loadImage(getContext(), view, this.posterSize, item);
        return view;
    }
}
