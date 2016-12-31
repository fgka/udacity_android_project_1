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

public class MovieListAdapter extends ArrayAdapter<MovieItem> {

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

    public MovieListAdapter(Context context, List<MovieItem> movieList) {
        super(context, 0, movieList);
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
        String url = getItem(position).getPosterImageUrl();
        Picasso.with(getContext()).load(url).into(view);
        return convertView;
    }
}
