package app.operativus.com.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.operativus.com.popularmovies.data.MovieItem;
import app.operativus.com.popularmovies.data.MoviePosterImageSize;

public class MovieListAdapter extends ArrayAdapter<MovieItem> {

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();
    private static final MoviePosterImageSize DEFAULT_POSTER_SIZE = MoviePosterImageSize.W_185;
    private static final String MOVIE_POSTER_URL_TMPL = "http://image.tmdb.org/t/p/%s/%s";

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
        String url = getPosterUrlByPosition(position);
        Picasso.with(getContext()).load(url).into(view);

        return view;
    }

    private String getPosterUrlByPosition(int position) {
        MovieItem item = getItem(position);

        if (item == null) {
            throw new IllegalArgumentException("Could not find item at " + position);
        }

        return String.format(MOVIE_POSTER_URL_TMPL,
                this.posterSize.getSizeUrlPath(),
                item.getPosterImagePath());
    }
}
