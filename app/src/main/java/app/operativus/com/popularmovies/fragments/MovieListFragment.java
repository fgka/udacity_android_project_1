package app.operativus.com.popularmovies.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import app.operativus.com.popularmovies.MovieDetailActivity;
import app.operativus.com.popularmovies.R;
import app.operativus.com.popularmovies.adapters.MovieListAdapter;
import app.operativus.com.popularmovies.data.MovieItem;
import app.operativus.com.popularmovies.data.MovieListRanking;
import app.operativus.com.popularmovies.helper.HealthStateChecker;
import app.operativus.com.popularmovies.tasks.FetchMovieListTask;

public class MovieListFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = MovieListFragment.class.getSimpleName();

    private static final String RANKING_PREFERENCE_NAME = "list_ranking";
    private static final MovieListRanking DEFAULT_RANKING = MovieListRanking.MOST_POPULAR;

    private MovieListAdapter movieListAdapter;

    public MovieListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        this.movieListAdapter = new MovieListAdapter(getActivity(), new ArrayList<MovieItem>());
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieList();
    }

    private void updateMovieList() {
        if (HealthStateChecker.isOnline(getActivity())) {
            MovieListRanking ranking = getPreferredMovieListRanking();
            FetchMovieListTask listTask = new FetchMovieListTask(this.movieListAdapter);
            listTask.execute(ranking);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_list_fragment, menu);
        MenuItem menuItem = menu.findItem(getPreferredMovieListRanking().getMenuId());
        if (menuItem != null) {
            menuItem.setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        int id = item.getItemId();
        Log.d(LOG_TAG, "Menu: " + item.getTitle());

        MovieListRanking ranking = MovieListRanking.fromId(id);
        if (ranking != null) {
            setPreferredMovieListRanking(ranking);
            item.setChecked(true);
            result = true;
        } else if (id == R.id.action_refresh) {
            updateMovieList();
            result = true;
        } else {
            result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_item_list, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movie_list);

        gridView.setAdapter(this.movieListAdapter);
        gridView.setColumnWidth(this.movieListAdapter.getPosterSize().getWidthInPx());
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

    private MovieListRanking getPreferredMovieListRanking() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String rankingName = sharedPreferences.getString(RANKING_PREFERENCE_NAME, DEFAULT_RANKING.name());

        return MovieListRanking.valueOf(rankingName);
    }

    private void setPreferredMovieListRanking(@NonNull MovieListRanking ranking) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(RANKING_PREFERENCE_NAME, ranking.name());
        editor.commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String name) {
        Log.d(LOG_TAG, "Changed preference: " + name);
        if (RANKING_PREFERENCE_NAME.equalsIgnoreCase(name)) {
            updateMovieList();
        }
    }
}
