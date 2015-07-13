package fouriam.android.esgi.fr.filmdroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;

import fouriam.android.esgi.fr.filmdroid.entities.PersonResultsPage;
import fouriam.android.esgi.fr.filmdroid.services.MoviesService;
import fouriam.android.esgi.fr.filmdroid.services.SearchService;


import fouriam.android.esgi.fr.filmdroid.Adapters.FilmAdapterFactory;
import fouriam.android.esgi.fr.filmdroid.utils.ErrorsHandlerUtils;
import fouriam.android.esgi.fr.filmdroid.utils.NetworkUtils;
import retrofit.RestAdapter;
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

    private String savedTitle;
    private String savedActor;

    private Toast toast;
    private ProgressDialog progress;

    private OnFragmentInteractionListener mListener;

    private Activity activity;

    public SearchFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, "onSaveInstanceState SEARCHFRAG");
        if (!searchFilmTitle.getText().toString().isEmpty()) {
            Log.v(TAG, "title saved : " + searchFilmTitle.getText().toString());
            outState.putString("curFilmTitle", searchFilmTitle.getText().toString());
        }

        if (!searchFilmActor.getText().toString().isEmpty())
        outState.putString("curFilmActor", searchFilmActor.getText().toString());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated : ");

        if (savedInstanceState != null) {
            if (savedInstanceState.getString("curFilmTitle") != null) {
                Log.v(TAG, "restored Title : " +savedInstanceState.getString("curFilmTitle"));
                savedTitle = savedInstanceState.getString("curFilmTitle");
            }
            if (savedInstanceState.getString("curFilmActor") != null) {
                savedActor = savedInstanceState.getString("curFilmActor");
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new FilmAdapterFactory())
                .create();

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
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
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

        if (savedTitle != null || savedActor != null) {

            // Restore last state for checked position.
            if (savedInstanceState.getString("curFilmTitle") != null) {
                searchFilmTitle.setText(savedTitle);
            } else {
                searchFilmTitle.setHint(getActivity().getResources().getString(R.string.search_by_title));
            }
            if (savedInstanceState.getString("curFilmActor") != null) {
                searchFilmActor.setText(savedActor);
            } else {
                searchFilmActor.setHint(getActivity().getResources().getString(R.string.search_by_person));
            }
        }

        return view;
    }

    public boolean validateForm () {
        if(searchFilmTitle.getText().toString().isEmpty()  &&
                searchFilmActor.getText().toString().isEmpty()) {
            searchFilmTitle.setError(getActivity().getResources().getString(R.string.SearchEmptyInputsError));
            searchFilmActor.setError(getActivity().getResources().getString(R.string.SearchEmptyInputsError));
            return false;
        }
        if(!searchFilmTitle.getText().toString().isEmpty()  &&
                !searchFilmActor.getText().toString().isEmpty()) {
            searchFilmTitle.setError(getActivity().getResources().getString(R.string.SearchTwoInputsError));
            searchFilmActor.setError(getActivity().getResources().getString(R.string.SearchTwoInputsError));
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
            toast.setText(getActivity().getResources().getString(R.string.ConnexionError));
            toast.show();
        }
    }

    public void goSearchRecent() {
        if (NetworkUtils.checkDeviceConnected(getActivity())) {
            new getRecentMoviesList().execute("");
        } else {
            toast.setText(getActivity().getResources().getString(R.string.ConnexionError));
            toast.show();
        }
    }

    public void goSearchUpcoming() {
        if (NetworkUtils.checkDeviceConnected(getActivity())) {
            new getUpcomigMoviesList().execute("");
        } else {
            toast.setText(getActivity().getResources().getString(R.string.ConnexionError));
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
        this.activity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    private class getActorList extends AsyncTask<String, Void, PersonResultsPage> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getActivity().getResources().getString(R.string.Loading));
            progress.show();
        }

        @Override
        protected PersonResultsPage doInBackground(String... actorName) {
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            SearchService searchService = tmdb.searchService();
            PersonResultsPage actors = searchService.person(actorName[0],1,false,null);

            return actors;
        }

        @Override
        protected void onPostExecute(PersonResultsPage actors) {
            progress.dismiss();
            ((MainActivity) getActivity()).showResultActors(actors.getResults());
        }
    }

    private class getRecentMoviesList extends AsyncTask<String, Void, MovieResultsPage> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getActivity().getResources().getString(R.string.Loading));
            progress.show();
        }

        @Override
        protected MovieResultsPage doInBackground(String... nothing) {
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            MoviesService moviesService = tmdb.moviesService();
            MovieResultsPage movies = moviesService.nowPlaying(null, "fr");

            return movies;
        }

        @Override
        protected void onPostExecute(MovieResultsPage movies) {
            progress.dismiss();
            ((MainActivity) getActivity()).showResultMovies(movies.getResults());
        }
    }

    private class getMoviesList extends AsyncTask<String, Void, MovieResultsPage> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getActivity().getResources().getString(R.string.Loading));
            progress.show();
        }

        @Override
        protected MovieResultsPage doInBackground(String... movieName) {
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            SearchService searchService = tmdb.searchService();
            MovieResultsPage movies = searchService.movie(movieName[0], 1, "fr", null, null, null, null);

            return movies;
        }

        @Override
        protected void onPostExecute(MovieResultsPage movies) {
            progress.dismiss();
            ((MainActivity) getActivity()).showResultMovies(movies.getResults());
        }
    }


    private class getUpcomigMoviesList extends AsyncTask<String, Void, MovieResultsPage> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getActivity().getResources().getString(R.string.Loading));
            progress.show();
        }

        @Override
        protected MovieResultsPage doInBackground(String... movieName) {
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            MoviesService moviesService = tmdb.moviesService();
            MovieResultsPage movies = moviesService.upcoming(null, "fr");

            return movies;
        }

        @Override
        protected void onPostExecute(MovieResultsPage movies) {
            progress.dismiss();
            ((MainActivity) getActivity()).showResultMovies(movies.getResults());
        }
    }

}

