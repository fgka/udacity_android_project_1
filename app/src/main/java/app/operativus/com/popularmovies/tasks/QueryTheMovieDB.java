package app.operativus.com.popularmovies.tasks;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import app.operativus.com.popularmovies.BuildConfig;
import app.operativus.com.popularmovies.data.MovieItem;
import app.operativus.com.popularmovies.data.MovieListRanking;
import app.operativus.com.popularmovies.json.JSONMovieListParser;
import app.operativus.com.popularmovies.json.JSONMovieParser;
import app.operativus.com.popularmovies.json.JSONParser;

public class QueryTheMovieDB {

    private static final String LOG_TAG = QueryTheMovieDB.class.getSimpleName();

    private static final String API_THE_MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_QUERY_MOST_POPULAR_URL = API_THE_MOVIE_DB_BASE_URL + "/movie/popular";
    private static final String API_QUERY_TOP_RATED_URL = API_THE_MOVIE_DB_BASE_URL + "/movie/top_rated";
    private static final String API_QUERY_MOVIE_ID_TMPL = API_THE_MOVIE_DB_BASE_URL + "/movie/%d";
    private static final String API_KEY_QUERY_PARAM = "api_key";

    public static final List<MovieItem> getMovieListByRanking(@NonNull MovieListRanking ranking) {
        List<MovieItem> result = null;
        Log.d(LOG_TAG, "Querying movie list by ranking: " + ranking);
        Uri uri = buildUriByRanking(ranking);

        return fetchAndParseJson(uri, new JSONMovieListParser());
    }

    private static Uri buildUriByRanking(MovieListRanking ranking) {
        String baseUrl = null;

        switch (ranking) {
            case MOST_POPULAR:
                baseUrl = API_QUERY_MOST_POPULAR_URL;
                break;
            case TOP_RATED:
                baseUrl = API_QUERY_TOP_RATED_URL;
                break;
            default:
                throw new IllegalArgumentException("Invalid ranking value: " + ranking);
        }
        Uri.Builder builder = appendApiKeyToUriBuilder(Uri.parse(baseUrl).buildUpon());

        return builder.build();
    }

    private static Uri.Builder appendApiKeyToUriBuilder(@NonNull Uri.Builder builder) {
        return builder
                .appendQueryParameter(API_KEY_QUERY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY_V3);
    }

    private static <T> T fetchAndParseJson(Uri uri, @NonNull JSONParser<T> parser) {
        T result = null;
        String jsonResponse = fetchJsonResponseByGet(uri);

        if (jsonResponse != null && !jsonResponse.isEmpty()) {
            result = parser.parse(jsonResponse);
        } else {
            Log.w(LOG_TAG, "Nothing to parse, JSON response is: " + jsonResponse);
        }

        return result;
    }

    /*
     * I dislike very much the method chaining.
     */
    private static String fetchJsonResponseByGet(Uri uri) {
        BufferedReader reader = null;
        URL url = getUrlFromUri(uri);
        HttpURLConnection urlConnection = getUrlConnectionByGet(url);

        return fetchConnectionResponse(urlConnection);
    }

    private static URL getUrlFromUri(Uri uri) {
        URL result = null;

        if (uri != null) {
            try {
                result = new URL(uri.toString());
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "URL error!", e);
            }
        } else {
            Log.w(LOG_TAG, "URI is null!");
        }

        return result;
    }

    private static HttpURLConnection getUrlConnectionByGet(URL url) {
        HttpURLConnection result = null;

        if (url != null) {
            try {
                result = (HttpURLConnection) url.openConnection();
                result.setRequestMethod("GET");
                result.connect();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Connection error!", e);
            }
        } else {
            Log.w(LOG_TAG, "URL is null!");
        }

        return result;
    }

    private static String fetchConnectionResponse(HttpURLConnection urlConnection) {
        StringBuffer result = new StringBuffer();
        BufferedReader reader = null;

        if (urlConnection != null) {
            try {
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        result.append(line + '\n');
                    }
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Could not read connection response!", e);
            } finally {
                urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Could not close reader!", e);
                    }
                }
            }
        } else {
            Log.w(LOG_TAG, "URL connection is null!");
        }

        return result.toString();
    }

    public static final MovieItem getMovieById(@NonNull Long movieId) {
        MovieItem result = null;
        Log.d(LOG_TAG, "Querying movie by ID: " + movieId);
        Uri uri = buildUriByMovieId(movieId);

        return fetchAndParseJson(uri, new JSONMovieParser());
    }

    private static Uri buildUriByMovieId(Long movieId) {
        String baseUrl = String.format(API_QUERY_MOVIE_ID_TMPL, movieId);
        Uri.Builder builder = appendApiKeyToUriBuilder(Uri.parse(baseUrl).buildUpon());

        return builder.build();
    }
}
