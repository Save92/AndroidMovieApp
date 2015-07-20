
package fouriam.android.esgi.fr.filmdroid.services;

import fouriam.android.esgi.fr.filmdroid.entities.Person;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by FPierre on 07/05/15.
 */
public interface PeopleService {

    @GET("/person/{id}")
    Person summary(
            @Path("id") int tmdbId
    );

}
