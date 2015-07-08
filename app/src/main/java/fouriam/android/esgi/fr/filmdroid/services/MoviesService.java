

package fouriam.android.esgi.fr.filmdroid.services;

import fouriam.android.esgi.fr.filmdroid.entities.AppendToResponse;
import fouriam.android.esgi.fr.filmdroid.entities.Credits;
import fouriam.android.esgi.fr.filmdroid.entities.Images;
import fouriam.android.esgi.fr.filmdroid.entities.ListResultsPage;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;
import fouriam.android.esgi.fr.filmdroid.entities.MovieAlternativeTitles;
import fouriam.android.esgi.fr.filmdroid.entities.MovieKeywords;
import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;
import fouriam.android.esgi.fr.filmdroid.entities.Releases;
import fouriam.android.esgi.fr.filmdroid.entities.ReviewResultsPage;
import fouriam.android.esgi.fr.filmdroid.entities.Videos;
import fouriam.android.esgi.fr.filmdroid.entities.Translations;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MoviesService {

    /**
     * Get the basic movie information for a specific movie id.
     *  @param tmdbId TMDb id.
     * @param language <em>Optional.</em> ISO 639-1 code.
     * @param appendToResponse <em>Optional.</em> extra requests to append to the result.
     */
    @GET("/movie/{id}")
    Movie summary(
            @Path("id") int tmdbId,
            @Query("language") String language,
            @Query("append_to_response") AppendToResponse appendToResponse
    );

    /**
     * Get the alternative titles for a specific movie id.
     *
     * @param tmdbId TMDb id.
     * @param country <em>Optional.</em> ISO 3166-1 code.
     */
    @GET("/movie/{id}/alternative_titles")
    MovieAlternativeTitles alternativeTitles(
            @Path("id") int tmdbId,
            @Query("country") String country
    );

    /**
     * Get the cast and crew information for a specific movie id.
     *
     * @param tmdbId TMDb id.
     */
    @GET("/movie/{id}/credits")
    Credits credits(
            @Path("id") int tmdbId
    );

    /**
     * Get the images (posters and backdrops) for a specific movie id.
     *
     * @param tmdbId TMDb id.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/movie/{id}/images")
    Images images(
            @Path("id") int tmdbId,
            @Query("language") String language
    );

    /**
     * Get the plot keywords for a specific movie id.
     *
     * @param tmdbId TMDb id.
     */
    @GET("/movie/{id}/keywords")
    MovieKeywords keywords(
            @Path("id") int tmdbId
    );

    /**
     * Get the release date and certification information by country for a specific movie id.
     *
     * @param tmdbId TMDb id.
     */
    @GET("/movie/{id}/releases")
    Releases releases(
            @Path("id") int tmdbId
    );

    /**
     * Get the videos (trailers, teasers, clips, etc...) for a specific movie id.
     *
     * @param tmdbId TMDb id.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/movie/{id}/videos")
    Videos videos(
            @Path("id") int tmdbId,
            @Query("language") String language
    );

    /**
     * Get the translations for a specific movie id.
     *
     * @param tmdbId TMDb id.
     * @param appendToResponse <em>Optional.</em> extra requests to append to the result.
     * @return
     */
    @GET("/movie/{id}/translations")
    Translations translations(
            @Path("id") int tmdbId,
            @Query("append_to_response") AppendToResponse appendToResponse
    );

    /**
     * Get the similar movies for a specific movie id.
     *
     * @param tmdbId TMDb id.
     * @param page <em>Optional.</em> Minimum value is 1, expected value is an integer.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/movie/{id}/similar")
    MovieResultsPage similar(
            @Path("id") int tmdbId,
            @Query("page") Integer page,
            @Query("language") String language
    );

    /**
     * Get the reviews for a particular movie id.
     *
     * @param tmdbId TMDb id.
     * @param page <em>Optional.</em> Minimum value is 1, expected value is an integer.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/movie/{id}/reviews")
    ReviewResultsPage reviews(
            @Path("id") int tmdbId,
            @Query("page") Integer page,
            @Query("language") String language
    );

    /**
     * Get the lists that the movie belongs to.
     *
     * @param tmdbId TMDb id.
     * @param page <em>Optional.</em> Minimum value is 1, expected value is an integer.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/movie/{id}/lists")
    ListResultsPage lists(
            @Path("id") int tmdbId,
            @Query("page") Integer page,
            @Query("language") String language
    );

    /**
     * Get the latest movie id.
     */
    @GET("/movie/latest")
    fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage latest();

    /**
     * Get the list of upcoming movies. This list refreshes every day. The maximum number of items this list will
     * include is 100.
     *
     * @param page <em>Optional.</em> Minimum value is 1, expected value is an integer.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/movie/upcoming")
    MovieResultsPage upcoming(
            @Query("page") Integer page,
            @Query("language") String language
    );

    /**
     * Get the list of movies playing in theaters. This list refreshes every day. The maximum number of items this list
     * will include is 100.
     *
     * @param page <em>Optional.</em> Minimum value is 1, expected value is an integer.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/movie/now_playing")
    MovieResultsPage nowPlaying(
            @Query("page") Integer page,
            @Query("language") String language
    );

    /**
     * Get the list of popular movies on The Movie Database. This list refreshes every day.
     *
     * @param page <em>Optional.</em> Minimum value is 1, expected value is an integer.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/movie/popular")
    MovieResultsPage popular(
            @Query("page") Integer page,
            @Query("language") String language
    );

    /**
     * Get the list of top rated movies. By default, this list will only include movies that have 10 or more votes. This
     * list refreshes every day.
     *
     * @param page <em>Optional.</em> Minimum value is 1, expected value is an integer.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/movie/top_rated")
    MovieResultsPage topRated(
            @Query("page") Integer page,
            @Query("language") String language
    );

}
