package app.operativus.com.popularmovies.apis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import app.operativus.com.popularmovies.data.MovieItem;
import app.operativus.com.popularmovies.data.MoviePosterImageSize;

public class PosterImageLoader {

    private static final MoviePosterImageSize DEFAULT_POSTER_SIZE = MoviePosterImageSize.W_185;
    private static final String MOVIE_POSTER_URL_TMPL = "http://image.tmdb.org/t/p/%s/%s";

    public static final void loadImage(@NonNull Context context, @NonNull ImageView imageView, @NonNull MovieItem movieItem) {
        loadImage(context, imageView, DEFAULT_POSTER_SIZE, movieItem);
    }

    public static final void loadImage(@NonNull Context context, @NonNull ImageView imageView, @NonNull MoviePosterImageSize size, @NonNull MovieItem movieItem) {
        String url = getPosterUrl(size, movieItem);
        Picasso.with(context).load(url).into(imageView);
    }

    private static String getPosterUrl(MoviePosterImageSize size, MovieItem movieItem) {
        return String.format(MOVIE_POSTER_URL_TMPL, size.getSizeUrlPath(), movieItem.getPosterImagePath());
    }
}
