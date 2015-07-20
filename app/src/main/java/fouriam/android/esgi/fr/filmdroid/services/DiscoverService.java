package fouriam.android.esgi.fr.filmdroid.services;

import fouriam.android.esgi.fr.filmdroid.entities.AppendToDiscoverResponse;
import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;
import fouriam.android.esgi.fr.filmdroid.enumerations.SortBy;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.Date;

/**
 * Created by FPierre on 07/05/15.
 *
 * http://docs.themoviedb.apiary.io/#reference/discover
 *
 * Cherche directement lse films.
 */
public interface DiscoverService {

    @GET("/discover/movie")
    MovieResultsPage discoverMovie(
            @Query("include_adult") boolean includeAdult,
            @Query("include_video") boolean includeVideo,
            @Query("language") String language,
            @Query("page") Integer page,
            @Query("primary_release_year") String primaryReleaseYear,
            @Query("primary_release_date.gte") Date primaryReleaseYearGte,
            @Query("primary_release_date.lte") Date primaryReleaseYearLte,
            @Query("release_date.gte") Date releaseDateGte,
            @Query("release_date.lte") Date releaseDateLte,
            @Query("sort_by") SortBy sortBy,
            @Query("vote_count.gte") Integer voteCountGte,
            @Query("vote_count.lte") Integer voteCountLte,
            @Query("vote_average.gte") Float voteAverageGte,
            @Query("vote_average.lte") Float voteAverageLte,
            @Query("with_cast") AppendToDiscoverResponse withCast,
            @Query("with_crew") AppendToDiscoverResponse withCrew,
            @Query("with_companies") AppendToDiscoverResponse withCompanies,
            @Query("with_genres") AppendToDiscoverResponse withGenres,
            @Query("with_keywords") AppendToDiscoverResponse withKeywords,
            @Query("with_people") AppendToDiscoverResponse withPeople,
            @Query("year") Integer year
    );

}
