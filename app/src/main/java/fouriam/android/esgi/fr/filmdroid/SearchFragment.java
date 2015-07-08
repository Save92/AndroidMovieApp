package fouriam.android.esgi.fr.filmdroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;

import fouriam.android.esgi.fr.filmdroid.entities.Person;
import fouriam.android.esgi.fr.filmdroid.entities.PersonResultsPage;
import fouriam.android.esgi.fr.filmdroid.services.MoviesService;
import fouriam.android.esgi.fr.filmdroid.services.SearchService;
//import com.uwetrottmann.tmdb.services.SearchService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.Adapters.FilmAdapterFactory;
import fouriam.android.esgi.fr.filmdroid.Adapters.ListFilmsAdapter;
import fouriam.android.esgi.fr.filmdroid.Models.Film;
import fouriam.android.esgi.fr.filmdroid.Models.Genre;
import fouriam.android.esgi.fr.filmdroid.Models.ListFilms;
import fouriam.android.esgi.fr.filmdroid.httpservice.FilmDroidService;
import fouriam.android.esgi.fr.filmdroid.utils.ErrorsHandlerUtils;
import fouriam.android.esgi.fr.filmdroid.utils.NetworkUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    private EditText searchFilmTitle;
    private EditText searchFilmActor;
    private Button searchBtn;
    private Button goFavorisBtn;
    private Button recentFilmsBtn;
    private Button upcomingFilmBtn;

    private Toast toast;
    private FilmDroidService filmDroidService;
    private ProgressDialog progress;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new FilmAdapterFactory())
                .create();


        filmDroidService = new RestAdapter.Builder()
                .setEndpoint(FilmDroidService.ENDPOINT)
                .setErrorHandler(new ErrorsHandlerUtils())
                .setConverter(new GsonConverter(gson))
                .build()
                .create(FilmDroidService.class);

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        searchFilmTitle = (EditText) view.findViewById(R.id.searchByTitleText);
        searchFilmActor = (EditText) view.findViewById(R.id.searchByPersonText);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        goFavorisBtn = (Button) view.findViewById(R.id.goFavorisBtn);
        recentFilmsBtn = (Button) view.findViewById(R.id.filmsRecentsBtn);
        upcomingFilmBtn = (Button) view.findViewById(R.id.filmsUpcommingBtn);
        progress = new ProgressDialog(getActivity());
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (validateForm()) {
                    goSearch();
                }
            }
        });

        goFavorisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goFavoris();
            }
        });

        recentFilmsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                goSearchRecent();
            }
        });

        upcomingFilmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                goSearchUpcoming();
            }
        });

        return view;
    }

    public boolean validateForm () {
        if(searchFilmTitle.getText().toString().isEmpty()  &&
                searchFilmActor.getText().toString().isEmpty()) {
            searchFilmTitle.setError("Au moins un critères doit être saisie");
            searchFilmActor.setError("Au moins un critères doit être saisie");
            return false;
        }
        if(!searchFilmTitle.getText().toString().isEmpty()  &&
                !searchFilmActor.getText().toString().isEmpty()) {
            searchFilmTitle.setError("Un seul critère doit être saisie");
            searchFilmActor.setError("Un seul critère doit être saisie");
            return false;
        }
        return true;
    }

    public void goSearch() {
        if (NetworkUtils.checkDeviceConnected(getActivity())) {

            if(!searchFilmTitle.getText().toString().isEmpty()) {
                new getMoviesList().execute(searchFilmTitle.getText().toString());
            }
            if(!searchFilmActor.getText().toString().isEmpty()) {
                new getActorList().execute(searchFilmActor.getText().toString());
            }
        } else {
            toast.setText("Please check your internet connection");
            toast.show();
        }
    }

    public void goSearchRecent() {
        if (NetworkUtils.checkDeviceConnected(getActivity())) {
            new getRecentMoviesList().execute("");
        } else {
            toast.setText("Please check your internet connection");
            toast.show();
        }
    }

    public void goSearchUpcoming() {
        if (NetworkUtils.checkDeviceConnected(getActivity())) {
            new getUpcomigMoviesList().execute("");
        } else {
            toast.setText("Please check your internet connection");
            toast.show();
        }
    }

    public void goFavoris() {
        ((MainActivity) getActivity()).showFavoris();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




/**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class getActorList extends AsyncTask<String, Void, PersonResultsPage> {
        @Override
        protected void onPreExecute() { // Actions avant d’exe la requete
            super.onPreExecute();
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected PersonResultsPage doInBackground(String... actorName) { // Exe en arriere plan
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            SearchService searchService = tmdb.searchService();
            PersonResultsPage actors = searchService.person(actorName[0],1,false,null);
            //MovieResultsPage movies = searchService.movie(movieName[0], 1, "fr-fr", null, null, null, null);

            return actors;
        }

        @Override
        protected void onPostExecute(PersonResultsPage actors) { // Action apres exe !
            progress.dismiss();
            ((MainActivity) getActivity()).showResultActors(actors.getResults());
        }
    }

    private class getRecentMoviesList extends AsyncTask<String, Void, MovieResultsPage> {
        @Override
        protected void onPreExecute() { // Actions avant d’exe la requete
            super.onPreExecute();
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected MovieResultsPage doInBackground(String... nothing) { // Exe en arriere plan
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            MoviesService moviesService = tmdb.moviesService();
            MovieResultsPage movies = moviesService.nowPlaying(null, "fr");
            //MovieResultsPage movies = searchService.movie(movieName[0], 1, "fr-fr", null, null, null, null);

            return movies;
        }

        @Override
        protected void onPostExecute(MovieResultsPage movies) { // Action apres exe !
            progress.dismiss();
            ((MainActivity) getActivity()).showResultMovies(movies.getResults());
        }
    }

    private class getMoviesList extends AsyncTask<String, Void, MovieResultsPage> {
        @Override
        protected void onPreExecute() { // Actions avant d’exe la requete
            super.onPreExecute();
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected MovieResultsPage doInBackground(String... movieName) { // Exe en arriere plan
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            SearchService searchService = tmdb.searchService();
            MovieResultsPage movies = searchService.movie(movieName[0], 1, "fr-fr", null, null, null, null);
            //MovieResultsPage movies = searchService.movie(movieName[0], 1, "fr-fr", null, null, null, null);

            return movies;
        }

        @Override
        protected void onPostExecute(MovieResultsPage movies) { // Action apres exe !
            progress.dismiss();
            ((MainActivity) getActivity()).showResultMovies(movies.getResults());
        }
    }


    private class getUpcomigMoviesList extends AsyncTask<String, Void, MovieResultsPage> {
        @Override
        protected void onPreExecute() { // Actions avant d’exe la requete
            super.onPreExecute();
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected MovieResultsPage doInBackground(String... movieName) { // Exe en arriere plan
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            MoviesService moviesService = tmdb.moviesService();
            MovieResultsPage movies = moviesService.upcoming(null, "fr");

            return movies;
        }

        @Override
        protected void onPostExecute(MovieResultsPage movies) { // Action apres exe !
            progress.dismiss();
            ((MainActivity) getActivity()).showResultMovies(movies.getResults());
        }
    }

}

