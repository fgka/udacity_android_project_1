package app.operativus.com.popularmovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import app.operativus.com.popularmovies.MovieDetailActivity;
import app.operativus.com.popularmovies.MovieItem;
import app.operativus.com.popularmovies.R;
import app.operativus.com.popularmovies.adapters.MovieListAdapter;

public class MovieListFragment extends Fragment {

    private final String LOG_TAG = MovieListFragment.class.getSimpleName();

    private MovieListAdapter movieListAdapter;
    private List<MovieItem> movieList;

    public MovieListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            Log.d(LOG_TAG, "Menu: refresh");
            //TODO
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.movieListAdapter = new MovieListAdapter(getActivity(), this.movieList);
        View rootView = inflater.inflate(R.layout.fragment_movie_item_list, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movie_list);
        gridView.setAdapter(this.movieListAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MovieItem item = movieListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class)
                        .putExtra(MovieItem.INTENT_EXTRA_NAME, item);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
