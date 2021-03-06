package app.operativus.com.popularmovies.data;

public enum MoviePosterImageSize {

    //@formatter:off
    W_92("w92")
    ,W_154("w154")
    ,W_185("w185")
    ,W_342("w342")
    ,W_500("w500")
    ,W_780("w780")
    ,ORIGINAL("original")
    ;
    //@formatter:on

    private final String sizeUrlPath;

    MoviePosterImageSize(String sizeUrlPath) {
        this.sizeUrlPath = sizeUrlPath;
    }

    public String getSizeUrlPath() {
        return sizeUrlPath;
    }

    public int getWidthInPx() {
        return Integer.valueOf(sizeUrlPath.substring(1));
    }
}
