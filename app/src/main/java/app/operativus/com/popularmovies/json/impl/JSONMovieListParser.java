package app.operativus.com.popularmovies.json.impl;


import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.operativus.com.popularmovies.data.MovieItem;
import app.operativus.com.popularmovies.json.JSONParser;

public class JSONMovieListParser implements JSONParser<List<MovieItem>> {

    // Movie item JSON keys
    public static final String ITEM_ID = "id";
    public static final String ITEM_POSTER_IMAGE_PATH = "poster_path";
    public static final String ITEM_TITLE = "title";
    public static final String ITEM_ORIGINAL_TITLE = "original_title";
    public static final String ITEM_PLOT_SYNOPSIS = "overview";
    public static final String ITEM_USER_RATING = "vote_average";
    public static final String ITEM_RELEASE_DATE_STR = "release_date";
    private static final String LOG_TAG = JSONMovieListParser.class.getSimpleName();
    // Response top level JSON keys
    private static final String PAGE = "page";
    private static final String LIST_ITEMS = "results";
    private static final String TOTAL_ITEMS = "total_results";
    private static final String TOTAL_PAGES = "total_pages";
    // Date manipulation
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat DATE_STRING_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    /*
        {
          "page": 1,
          "results": [
            {
              "poster_path": "\\/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg",
              "adult": false,
              "overview": "A rogue band of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy.",
              "release_date": "2016-12-14",
              "genre_ids": [
                28,
                12,
                14,
                878,
                53,
                10752
              ],
              "id": 330459,
              "original_title": "Rogue One: A Star Wars Story",
              "original_language": "en",
              "title": "Rogue One: A Star Wars Story",
              "backdrop_path": "\\/tZjVVIYXACV4IIIhXeIM59ytqwS.jpg",
              "popularity": 207.809668,
              "vote_count": 1122,
              "video": false,
              "vote_average": 7.3
            },
          ],
          "total_results": 19628,
          "total_pages": 982
        }
     */
    private static final List<MovieItem> parse(JSONObject jsonObject) throws JSONException {
        long page = jsonObject.getLong(PAGE);
        long totalItems = jsonObject.getLong(TOTAL_ITEMS);
        long totalPages = jsonObject.getLong(TOTAL_PAGES);
        JSONArray itemList = jsonObject.getJSONArray(LIST_ITEMS);

        Log.i(LOG_TAG, String.format(
                "Parsing page %d/%d. Total items to parse: %d/%d",
                page, totalPages,
                itemList.length(), totalItems));
        return parseJsonArrayToMovieList(itemList);
    }

    /*
     [
        {
          "poster_path": "\\/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg",
          "adult": false,
          "overview": "A rogue band of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy.",
          "release_date": "2016-12-14",
          "genre_ids": [
            28,
            12,
            14,
            878,
            53,
            10752
          ],
          "id": 330459,
          "original_title": "Rogue One: A Star Wars Story",
          "original_language": "en",
          "title": "Rogue One: A Star Wars Story",
          "backdrop_path": "\\/tZjVVIYXACV4IIIhXeIM59ytqwS.jpg",
          "popularity": 207.809668,
          "vote_count": 1122,
          "video": false,
          "vote_average": 7.3
        },
      ]
     */
    private static final List<MovieItem> parseJsonArrayToMovieList(@NonNull JSONArray itemList) throws JSONException {
        List<MovieItem> result = new ArrayList<>();
        int total = itemList.length();

        for (int i = 0; i < total; i++) {
            JSONObject jsonObject = itemList.getJSONObject(i);
            Log.d(LOG_TAG, String.format("Parsing movie item: %d/%d: %s", i, total, String.valueOf(jsonObject)));
            result.add(parseJsonObjectToMovieItem(jsonObject));
        }

        return result;
    }

    /*
    {
      "poster_path": "\\/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg",
      "adult": false,
      "overview": "A rogue band of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy.",
      "release_date": "2016-12-14",
      "genre_ids": [
        28,
        12,
        14,
        878,
        53,
        10752
      ],
      "id": 330459,
      "original_title": "Rogue One: A Star Wars Story",
      "original_language": "en",
      "title": "Rogue One: A Star Wars Story",
      "backdrop_path": "\\/tZjVVIYXACV4IIIhXeIM59ytqwS.jpg",
      "popularity": 207.809668,
      "vote_count": 1122,
      "video": false,
      "vote_average": 7.3
    }
     */
    private static final MovieItem parseJsonObjectToMovieItem(@NonNull JSONObject jsonObject) throws JSONException {
        Long id = jsonObject.getLong(ITEM_ID);
        String posterImagePath = jsonObject.getString(ITEM_POSTER_IMAGE_PATH);
        String originalTitle = jsonObject.getString(ITEM_ORIGINAL_TITLE);
        String plotSynopsis = jsonObject.getString(ITEM_PLOT_SYNOPSIS);
        Double userRating = jsonObject.getDouble(ITEM_USER_RATING);
        Date releaseDate = getDateFromString(jsonObject.getString(ITEM_RELEASE_DATE_STR));

        return new MovieItem(id, posterImagePath, originalTitle, plotSynopsis, userRating, Double.NaN, releaseDate);
    }

    private static Date getDateFromString(String dateStr) {
        Date result = null;

        try {
            result = DATE_STRING_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            Log.e(LOG_TAG, String.format("Could not parse date '%s' using format '%s'", dateStr, DATE_FORMAT), e);
        }

        return result;
    }

    /**
     * @param json
     * @return
     */
    @Override
    public final List<MovieItem> parse(@NonNull String json) {
        List<MovieItem> result = null;
        Log.d(LOG_TAG, "JSON input: " + json);

        try {
            JSONObject jsonObject = new JSONObject(json);
            result = parse(jsonObject);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Could not parse json string!", e);
        }

        return result;
    }
}
