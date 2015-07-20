
package fouriam.android.esgi.fr.filmdroid.services;

import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;
import fouriam.android.esgi.fr.filmdroid.entities.PersonResultsPage;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by FPierre on 07/05/15.
 */
public interface SearchService {

    @GET("/search/movie")
    MovieResultsPage movie(
            @Query("query") String query,
            @Query("page") Integer page,
            @Query("language") String language,
            @Query("include_adult") Boolean includeAdult,
            @Query("year") Integer year,
            @Query("primary_release_year") Integer primaryReleaseYear,
            @Query("search_type") String searchType
    );

    @GET("/search/person")
    PersonResultsPage person(
            @Query("query") String query,
            @Query("page") Integer page,
            @Query("include_adult") Boolean includeAdult,
            @Query("search_type") String searchType
    );

}
