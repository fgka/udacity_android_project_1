package app.operativus.com.popularmovies.data;

import app.operativus.com.popularmovies.R;


public enum MovieListRanking {

    //@formatter:off
    MOST_POPULAR(R.id.action_most_popular), TOP_RATED(R.id.action_top_rated);
    //@formatter:on

    private final int menuId;

    MovieListRanking(int menuId) {
        this.menuId = menuId;
    }

    /**
     * This would be lovely if done with Streams.
     * Damn, backwards compatibility :-(
     *
     * @param value
     * @return
     */
    public static MovieListRanking fromId(int value) {
        MovieListRanking result = null;
        for (MovieListRanking i : MovieListRanking.values()) {
            if (i.getMenuId() == value) {
                result = i;
                break;
            }
        }
        return result;
    }

    public int getMenuId() {
        return menuId;
    }

    @Override
    public String toString() {
        return "MovieListRanking{" +
                "name='" + this.name() + '\'' +
                ", menuId=" + menuId +
                '}';
    }
}
