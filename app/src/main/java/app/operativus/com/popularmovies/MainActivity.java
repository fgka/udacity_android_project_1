package app.operativus.com.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import app.operativus.com.popularmovies.data.MovieListRanking;
import app.operativus.com.popularmovies.fragments.MovieListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            attachMovieListFragment();
        }
    }

    private void attachMovieListFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new MovieListFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        int id = item.getItemId();

        Log.d(LOG_TAG, "Menu: " + item.getTitle());
        MovieListRanking ranking = MovieListRanking.fromId(id);
        if (ranking != null) {
            //TODO
            item.setChecked(true);
            result = true;
        } else {
            result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    /**
     * Source: https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     *
     * @return
     */
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
