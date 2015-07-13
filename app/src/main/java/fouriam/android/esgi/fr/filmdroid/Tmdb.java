
package fouriam.android.esgi.fr.filmdroid;

import fouriam.android.esgi.fr.filmdroid.services.DiscoverService;
import fouriam.android.esgi.fr.filmdroid.services.MoviesService;
import fouriam.android.esgi.fr.filmdroid.services.PeopleService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class Tmdb {

    public static final String API_URL = "https://api.themoviedb.org/3";

    public static final String PARAM_API_KEY = "api_key";

    private String apiKey;
    private boolean isDebug;
    private RestAdapter restAdapter;

    public Tmdb() {
    }

    public Tmdb setApiKey(String value) {
        this.apiKey = value;
        restAdapter = null;
        return this;
    }

    protected RestAdapter.Builder newRestAdapterBuilder() {
        return new RestAdapter.Builder();
    }


    protected RestAdapter getRestAdapter() {
        if (restAdapter == null) {
            RestAdapter.Builder builder = newRestAdapterBuilder();

            builder.setEndpoint(API_URL);
            builder.setConverter(new GsonConverter(TmdbHelper.getGsonBuilder().create()));
            builder.setRequestInterceptor(new RequestInterceptor() {
                public void intercept(RequestFacade requestFacade) {
                    requestFacade.addQueryParam(PARAM_API_KEY, apiKey);
                }
            });

            if (isDebug) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
            }

            restAdapter = builder.build();
        }

        return restAdapter;
    }

    public fouriam.android.esgi.fr.filmdroid.services.MoviesService moviesService() {
        return (fouriam.android.esgi.fr.filmdroid.services.MoviesService) getRestAdapter().create(MoviesService.class);
    }

    public PeopleService personService() {
        return getRestAdapter().create(PeopleService.class);
    }

    public fouriam.android.esgi.fr.filmdroid.services.SearchService searchService() {
        return getRestAdapter().create(fouriam.android.esgi.fr.filmdroid.services.SearchService.class);
    }

    
    public DiscoverService discoverService() {
        return getRestAdapter().create(DiscoverService.class);
    }
}
