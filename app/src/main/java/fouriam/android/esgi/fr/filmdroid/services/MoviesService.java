

package fouriam.android.esgi.fr.filmdroid.services;

import fouriam.android.esgi.fr.filmdroid.entities.AppendToResponse;
import fouriam.android.esgi.fr.filmdroid.entities.Credits;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;
import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by FPierre on 07/05/15.
 *
 * http://docs.themoviedb.apiary.io/#reference/movies
 */
public interface MoviesService {

    /**
     * Cherche un film par son identifiant TMDB
     */
    @GET("/movie/{id}")
    Movie summary(
            @Path("id") int tmdbId,
            @Query("language") String language,
            @Query("append_to_response") AppendToResponse appendToResponse
    );

    /**
     * Cherches les crédits d'un film par son identifiant TMDB
     */
    @GET("/movie/{id}/credits")
    Credits credits(
            @Path("id") int tmdbId
    );

    /**
     * Liste des films qui vont arriver à l'affiche
     */
    @GET("/movie/upcoming")
    MovieResultsPage upcoming(
            @Query("page") Integer page,
            @Query("language") String language
    );

    /**
     * Liste des films qui sortent en salle
     */
    @GET("/movie/now_playing")
    MovieResultsPage nowPlaying(
            @Query("page") Integer page,
            @Query("language") String language
    );

}
