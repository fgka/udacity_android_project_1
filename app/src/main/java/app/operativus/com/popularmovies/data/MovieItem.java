package app.operativus.com.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Source for {@link Parcelable}:
 * https://www.google.com/url?q=http://www.developerphil.com/parcelable-vs-serializable/&sa=D&ust=1483211812738000&usg=AFQjCNHlsmOf15NfHij4U6QLVJSHZC2lBw
 */
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
    private final Long id;
    private final String posterImagePath;
    private final String originalTitle;
    private final String plotSynopsis;
    private final Double userRating;
    private final Double lengthInMin;
    private final Date releaseDate;

    public MovieItem(@NonNull Long id,
                     @NonNull String posterImagePath,
                     @NonNull String originalTitle,
                     @NonNull String plotSynopsis,
                     @NonNull Double userRating,
                     @NonNull Double lengthInMin,
                     @NonNull Date releaseDate) {
        this.id = id;
        this.posterImagePath = posterImagePath;
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.lengthInMin = lengthInMin;
        this.releaseDate = releaseDate;
    }

    public MovieItem(Parcel in) {
        this(
                in.readLong(), //id
                in.readString(), //posterImageUrl
                in.readString(), //originalTitle
                in.readString(), //plotSynopsis
                in.readDouble(), //userRating
                in.readDouble(), //lengthInMin
                new Date(in.readLong()) //releaseDate
        );
    }

    public String getPosterImagePath() {
        return posterImagePath;
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

    public Double getLengthInMin() {
        return lengthInMin;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "MovieItem{" +
                "id=" + id +
                ", originalTitle='" + originalTitle + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.posterImagePath);
        dest.writeString(this.originalTitle);
        dest.writeString(this.plotSynopsis);
        dest.writeDouble(this.userRating);
        dest.writeDouble(this.lengthInMin);
        dest.writeLong(this.releaseDate.getTime());
    }
}
