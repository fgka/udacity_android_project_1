package app.operativus.com.popularmovies.helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * Created by gkandriotti on 2017-01-02.
 */

public class HealthStateChecker {

    private static final String NO_INTERNET_TOAST_MSG = "No Internet connection!";

    /**
     * Source: https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     *
     * @return
     */
    public static final boolean isOnline(@NonNull Activity activity) {
        boolean result = false;
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        result = netInfo != null && netInfo.isConnectedOrConnecting();
        if (!result) {
            noInternetToast(activity);
        }

        return result;
    }

    private static final void noInternetToast(Activity activity) {
        Toast.makeText(activity.getApplicationContext(), NO_INTERNET_TOAST_MSG, Toast.LENGTH_LONG).show();
    }
}
