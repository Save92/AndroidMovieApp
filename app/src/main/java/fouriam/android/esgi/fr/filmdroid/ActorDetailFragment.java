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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import fouriam.android.esgi.fr.filmdroid.entities.AppendToDiscoverResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.Adapters.ListMovieAdapter;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;
import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;
import fouriam.android.esgi.fr.filmdroid.entities.Person;
import fouriam.android.esgi.fr.filmdroid.services.DiscoverService;
import fouriam.android.esgi.fr.filmdroid.services.PeopleService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActorDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActorDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActorDetailFragment extends Fragment {

    private static final String TAG = "DetailActorFragment";

    private Person person;
    private Toast toast;
    private ProgressDialog progress;
    private ListMovieAdapter listMovieAdapter;

    private TextView personName;
    private TextView personBirthDay;
    private TextView personPlaceBirth;
    private ListView filmography;

    private OnFragmentInteractionListener mListener;

    public static ActorDetailFragment newInstance() {
        ActorDetailFragment fragment = new ActorDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ActorDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            person = (Person) getArguments().getSerializable("actor");
        }
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        progress = new ProgressDialog(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actor_detail, container, false);
        personName = (TextView) view.findViewById(R.id.personNameText);
        personBirthDay = (TextView) view.findViewById(R.id.personBirthdayText);
        personPlaceBirth = (TextView) view.findViewById(R.id.personPlaceBithText);
        filmography = (ListView) view.findViewById(R.id.filmographyList);

        goSearchActor();

        return view;
    }

    public void goSearchActor() {
        new getPersonDetail().execute(person.getId());
    }

    public void goSearchFilmography() {
        new getPersonFilmography().execute(person.getId());
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void populateListMovies(List<Movie> movies){
         listMovieAdapter= new ListMovieAdapter(getActivity(), R.layout.list_films, (ArrayList<Movie>) movies);
        filmography.setAdapter(listMovieAdapter);
        // ListView Item Click Listener
        filmography.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.v(TAG, "onItemClick:" + position);
                // ListView Clicked item value
                Movie movie = (Movie) filmography.getItemAtPosition(position);
                ((MainActivity) getActivity()).showDetailFilm(movie);

            }
        });
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    private class getPersonDetail extends AsyncTask<Integer, Void, Person> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getActivity().getResources().getString(R.string.Loading));
            progress.show();
        }

        @Override
        protected Person doInBackground(Integer... personId) {
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            PeopleService peopleService = tmdb.personService();

            return peopleService.summary(personId[0]);
        }

        @Override
        protected void onPostExecute(Person currentPerson) {
            progress.dismiss();
            personName.setText(currentPerson.getName());
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            personBirthDay.setText(format.format(currentPerson.getBirthday()));
            personPlaceBirth.setText(currentPerson.getPlace_of_birth());
            goSearchFilmography();
        }
    }

    private class getPersonFilmography extends AsyncTask<Integer, Void, MovieResultsPage> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setMessage(getActivity().getResources().getString(R.string.Loading));
            progress.show();
        }

        @Override
        protected MovieResultsPage doInBackground(Integer... personId) {
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            DiscoverService discoverService = tmdb.discoverService();

            return discoverService.discoverMovie(false, false, "en", 1, null, null, null, null, null, null, null, null, null, null, new AppendToDiscoverResponse(personId),null,null,null,null,null,null);
        }

        @Override
        protected void onPostExecute(MovieResultsPage movies) {
            progress.dismiss();
            populateListMovies(movies.getResults());
        }
    }

}
