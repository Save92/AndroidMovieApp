package fouriam.android.esgi.fr.filmdroid.httpservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.Models.Acteur;
import fouriam.android.esgi.fr.filmdroid.Models.Film;
import fouriam.android.esgi.fr.filmdroid.Models.Genre;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Save92 on 01/07/15.
 */
public interface FilmDroidService {

    public static final String ENDPOINT = "http://api.themoviedb.org/3/";
    public static final String APIKEY = "4718f1a9036a1c190dad9301f401fb25";


    /**
     *
     * FILMS
     *
     */

    // Get pour avoir les films par titre
    @GET("search/movie?query={titleString}&api_key=" + APIKEY)
    void getMoviesByTitle(@Path("titleString") String titleString, Callback<ArrayList<Film>> callback);

    // Get pour chercher un actor
    @GET("search/person?query={actorString}&api_key="+ APIKEY)
    void getActor(@Path("actorString") String actorString, Callback<Acteur> callback);

    // Get pour chercher un film par actor
    @GET("discover/movie?with_people={actorId}&api_key=" + APIKEY)
    void getMoviesByActor(@Path("actorId") Integer actorId, Callback<ArrayList<Film>> callback);

    // Get pour chercher un film par son id
    @GET("/movie/{movieId}&api_key=" + APIKEY)
    void getMoviesById(@Path("movieId") Integer movieId, Callback<Film> callback);


    /**
     *
     * FILMS EN SALLE
     *
     */

    // Get pour avoir les films en salle
    @GET("/movie/now_playing?api_key="+APIKEY)
    void getMoviesNowPlaying(Callback<ArrayList<Film>> callback);

    /**
     *
     * FILMS A VENIR
     *
     */

    // Get pour avoir les films à venir
    @GET("/movie/upcoming?api_key="+APIKEY)
    void getMoviesUpComming(Callback<ArrayList<Film>> callback);

    /**
     * ACTORS
     */
    @GET("/movie/{movieId}/credits?api_key="+APIKEY)
    void getActorsFromMovie(Callback<ArrayList<Acteur>> callback);

    /**
     * GENRES
     */
    @GET("/genre/movie/list?api_key="+APIKEY)
    void getGenreForMovie(Callback<ArrayList<HashMap<Integer, String>>> callback);

}
