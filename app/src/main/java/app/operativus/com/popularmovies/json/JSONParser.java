package app.operativus.com.popularmovies.json;


public interface JSONParser<OutputType> {

    OutputType parse(String json);
}
