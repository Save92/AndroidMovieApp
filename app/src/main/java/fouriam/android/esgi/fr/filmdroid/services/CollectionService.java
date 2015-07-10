
package fouriam.android.esgi.fr.filmdroid.services;

import fouriam.android.esgi.fr.filmdroid.entities.AppendToResponse;
import fouriam.android.esgi.fr.filmdroid.entities.Collection;
import fouriam.android.esgi.fr.filmdroid.entities.Images;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface CollectionService {
    /**
     * Get the basic collection information for a specific collection id.
     *
     * @param tmdbId TMDb id.
     * @param language <em>Optional.</em> ISO 639-1 code.
     * @param appendToResponse <em>Optional.</em> extra requests to append to the result.
     */
    @GET("/collection/{id}")
    Collection summary(@Path("id") int tmdbId, @Query("language") String language, @Query("append_to_response") AppendToResponse appendToResponse);

    /**
     * Get the images (posters and backdrops) for a specific collection id.
     *
     * @param tmdbId TMDb id.
     * @param language <em>Optional.</em> ISO 639-1 code.
     */
    @GET("/collection/{id}/images")
    Images images(@Path("id") int tmdbId, @Query("language") String language);
}
