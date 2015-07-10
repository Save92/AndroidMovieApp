package fouriam.android.esgi.fr.filmdroid;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import fouriam.android.esgi.fr.filmdroid.Adapters.ListMovieAdapter;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;

import java.util.ArrayList;


public class ResultsFilmsFragment extends Fragment {

    private static final String TAG = "ResultsFilmsFragments";
    private OnFragmentInteractionListener mListener;

    private ArrayList<Movie> listFilms;
    private ListMovieAdapter filmsAdapter;
    private ListView resultsFilmsList;


    public ResultsFilmsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "on create");
        if (getArguments() != null) {
            listFilms = (ArrayList<Movie>) getArguments().getSerializable("listFilms");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(TAG, "on createView");
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        resultsFilmsList = (ListView) view.findViewById(R.id.resultsFilmList);
        filmsAdapter = new ListMovieAdapter(getActivity(), R.layout.list_films, listFilms);
        resultsFilmsList.setAdapter(filmsAdapter);
        // ListView Item Click Listener
        resultsFilmsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.v(TAG, "onItemClick:" + position);
                // ListView Clicked item value
                Movie movie = (Movie) resultsFilmsList.getItemAtPosition(position);
                ((MainActivity) getActivity()).showDetailFilm(movie);

            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

}
