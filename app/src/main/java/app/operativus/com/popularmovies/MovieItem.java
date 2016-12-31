package app.operativus.com.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItem implements Parcelable {

    public static final String INTENT_EXTRA_NAME = "EXTRA_PARCEL";

    static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
    private static final String LOG_TAG = MovieItem.class.getSimpleName();
    private final String posterImageUrl;
    private final String originalTitle;
    private final String plotSynopsis;
    private final Double userRating;
    private final Integer releaseYear;

    public MovieItem(String posterImageUrl, String originalTitle, String plotSynopsis, Double userRating, Integer releaseYear) {
        this.posterImageUrl = posterImageUrl;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseYear = releaseYear;
    }

    public MovieItem(Parcel in) {
        this(
                in.readString(), //posterImageUrl
                in.readString(), //originalTitle
                in.readString(), //plotSynopsis
                in.readDouble(), //userRating
                in.readInt() //releaseYear
        );
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public Double getUserRating() {
        return userRating;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    @Override
    public String toString() {
        return "MovieItem{" +
                "originalTitle='" + originalTitle + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterImageUrl);
        dest.writeString(this.originalTitle);
        dest.writeString(this.plotSynopsis);
        dest.writeDouble(this.userRating);
        dest.writeInt(this.releaseYear);
    }
}
