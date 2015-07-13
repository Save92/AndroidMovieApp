

package fouriam.android.esgi.fr.filmdroid.services;

import fouriam.android.esgi.fr.filmdroid.entities.AppendToResponse;
import fouriam.android.esgi.fr.filmdroid.entities.Credits;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;
import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MoviesService {

    @GET("/movie/{id}")
    Movie summary(
            @Path("id") int tmdbId,
            @Query("language") String language,
            @Query("append_to_response") AppendToResponse appendToResponse
    );

    @GET("/movie/{id}/credits")
    Credits credits(
            @Path("id") int tmdbId
    );


    @GET("/movie/upcoming")
    MovieResultsPage upcoming(
            @Query("page") Integer page,
            @Query("language") String language
    );

    @GET("/movie/now_playing")
    MovieResultsPage nowPlaying(
            @Query("page") Integer page,
            @Query("language") String language
    );

}
