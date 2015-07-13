
package fouriam.android.esgi.fr.filmdroid.services;

import fouriam.android.esgi.fr.filmdroid.entities.Person;
import retrofit.http.GET;
import retrofit.http.Path;
public interface PeopleService {

    @GET("/person/{id}")
    Person summary(
            @Path("id") int tmdbId
    );

}
