
package fouriam.android.esgi.fr.filmdroid;

import fouriam.android.esgi.fr.filmdroid.services.CollectionService;
import fouriam.android.esgi.fr.filmdroid.services.ConfigurationService;
import fouriam.android.esgi.fr.filmdroid.services.DiscoverService;
import fouriam.android.esgi.fr.filmdroid.services.FindService;
import fouriam.android.esgi.fr.filmdroid.services.MoviesService;
import fouriam.android.esgi.fr.filmdroid.services.PeopleService;
import fouriam.android.esgi.fr.filmdroid.services.TvEpisodesService;
import fouriam.android.esgi.fr.filmdroid.services.TvSeasonsService;
import fouriam.android.esgi.fr.filmdroid.services.TvService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class Tmdb {

    /**
     * Tmdb API URL.
     */
    public static final String API_URL = "https://api.themoviedb.org/3";

    /**
     * API key query parameter name.
     */
    public static final String PARAM_API_KEY = "api_key";

    private String apiKey;
    private boolean isDebug;
    private RestAdapter restAdapter;

    /**
     * Create a new manager instance.
     */
    public Tmdb() {
    }

    /**
     * Set the TMDB API key.
     * @param value Your TMDB API key.
     */
    public Tmdb setApiKey(String value) {
        this.apiKey = value;
        restAdapter = null;
        return this;
    }

    /**
     * Create a new {@link retrofit.RestAdapter.Builder}. Override this to e.g. set your own client or executor.
     *
     * @return A {@link retrofit.RestAdapter.Builder} with no modifications.
     */
    protected RestAdapter.Builder newRestAdapterBuilder() {
        return new RestAdapter.Builder();
    }

    /**
     * Return the current {@link retrofit.RestAdapter} instance. If none exists (first call, API key changed),
     * builds a new one.
     * <p>
     * When building, sets the endpoint, a custom converter ({@link TmdbHelper#getGsonBuilder()})
     * and a {@link retrofit.RequestInterceptor} which adds the API key as query param.
     */
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

    public ConfigurationService configurationService() {
        return getRestAdapter().create(ConfigurationService.class);
    }

    public FindService findService() {
        return getRestAdapter().create(FindService.class);
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

    public TvService tvService() {
        return getRestAdapter().create(TvService.class);
    }

    public TvSeasonsService tvSeasonsService() {
        return getRestAdapter().create(TvSeasonsService.class);
    }
    
    public TvEpisodesService tvEpisodesService() {
        return getRestAdapter().create(TvEpisodesService.class);
    }
    
    public DiscoverService discoverService() {
        return getRestAdapter().create(DiscoverService.class);
    }

    public CollectionService collectionService() {
        return getRestAdapter().create(CollectionService.class);
    }
}
