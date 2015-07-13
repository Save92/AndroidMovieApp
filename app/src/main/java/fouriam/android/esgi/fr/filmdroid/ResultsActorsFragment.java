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

import java.util.ArrayList;

import fouriam.android.esgi.fr.filmdroid.Adapters.ListActorsAdapter;
import fouriam.android.esgi.fr.filmdroid.entities.Person;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultsActorsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultsActorsFragment#} factory method to
 * create an instance of this fragment.
 */
public class ResultsActorsFragment extends Fragment {

    private static final String TAG = "RestulActorFragment";
    private OnFragmentInteractionListener mListener;

    private ArrayList<Person> listActors;
    private ListActorsAdapter actorsAdapter;
    private ListView resultsActorsList;


    public ResultsActorsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "OnCreate");
        if (getArguments() != null) {
            listActors = (ArrayList<Person>) getArguments().getSerializable("listActors");
        }

        Log.v(TAG, "OnCreate End");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(TAG, "OnCreateView");
        View view = inflater.inflate(R.layout.fragment_results_actors, container, false);
        resultsActorsList = (ListView) view.findViewById(R.id.resultsPersonList);
        actorsAdapter = new ListActorsAdapter(getActivity(), R.layout.list_actors, listActors);
        resultsActorsList.setAdapter(actorsAdapter);
        // ListView Item Click Listener
        Log.v(TAG, "Before clickListener");
        resultsActorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.v(TAG, "In clickListener");
                // ListView Clicked item value
                Person actor = actorsAdapter.getItem(position);
                ((MainActivity) getActivity()).showDetailActor(actor);

            }
        });
        Log.v(TAG, "OnCreateView END");
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