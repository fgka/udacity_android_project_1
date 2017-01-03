package app.operativus.com.popularmovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import app.operativus.com.popularmovies.R;
import app.operativus.com.popularmovies.apis.PosterImageLoader;
import app.operativus.com.popularmovies.data.MovieItem;
import app.operativus.com.popularmovies.data.MoviePosterImageSize;

public class MovieDetailFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    //Release date format
    private static final String DATE_FORMAT = "yyyy";
    private static final SimpleDateFormat DATE_STRING_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    private static final MoviePosterImageSize DEFAULT_POSTER_SIZE = MoviePosterImageSize.W_185;

    private static final String LENGTH_IN_MIN_TMPL = "%01.0fmin";
    private static final String USER_RATING_TMPL = "%01.1f/10";

    private MoviePosterImageSize posterSize = DEFAULT_POSTER_SIZE;

    public MovieDetailFragment() {
        setHasOptionsMenu(true);
    }

    public void setPosterSize(@NonNull MoviePosterImageSize posterSize) {
        this.posterSize = posterSize;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_detail_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            Log.d(LOG_TAG, "Refresh");
            //TODO
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(MovieItem.INTENT_EXTRA_NAME)) {
            MovieItem item = intent.getParcelableExtra(MovieItem.INTENT_EXTRA_NAME);
            if (item == null) {
                throw new IllegalArgumentException("No movie sent through parceable extra at: " + MovieItem.INTENT_EXTRA_NAME);
            }
            setMovieItemIntoView(rootView, item);
        }
        return rootView;
    }

    private void setMovieItemIntoView(View rootView, MovieItem item) {
        Log.d(LOG_TAG, "Details for movie: " + item);
        //Layout elements
        TextView title = (TextView) rootView.findViewById(R.id.movie_title);
        TextView release = (TextView) rootView.findViewById(R.id.movie_release);
        TextView length = (TextView) rootView.findViewById(R.id.movie_length);
        TextView rating = (TextView) rootView.findViewById(R.id.movie_rating);
        TextView synopsis = (TextView) rootView.findViewById(R.id.movie_synopsis);
        ImageView poster = (ImageView) rootView.findViewById(R.id.movie_poster);
        putMovieItemIntoUIViews(item, title, release, length, rating, synopsis, poster);
    }

    private void putMovieItemIntoUIViews(@NonNull MovieItem item,
                                         @NonNull TextView title,
                                         @NonNull TextView release,
                                         @NonNull TextView length,
                                         @NonNull TextView rating,
                                         @NonNull TextView synopsis,
                                         @NonNull ImageView poster) {
        //Set text
        title.setText(item.getOriginalTitle());
        release.setText(DATE_STRING_FORMAT.format(item.getReleaseDate()));
        length.setText(String.format(LENGTH_IN_MIN_TMPL, item.getLengthInMin()));
        rating.setText(String.format(USER_RATING_TMPL, item.getUserRating()));
        synopsis.setText(item.getPlotSynopsis());
        //Set image
        PosterImageLoader.loadImage(getContext(), poster, this.posterSize, item);
    }
}
