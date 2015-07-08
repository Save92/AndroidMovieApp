package fouriam.android.esgi.fr.filmdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.Adapters.ListMovieAdapter;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;
import fouriam.android.esgi.fr.filmdroid.sqlite.SQLiteDatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavorisFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavorisFragment#} factory method to
 * create an instance of this fragment.
 */
public class FavorisFragment extends Fragment {
    private static final String TAG = "FavorisFragment";

    SQLiteDatabaseHandler db;
    private ArrayList<Movie> listFilms;
    private List<Movie> list;
    private ListMovieAdapter filmsAdapter;
    private ListView favorisListView;
    private OnFragmentInteractionListener mListener;

    public FavorisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SQLiteDatabaseHandler(getActivity());
        list = db.showAll();
       listFilms =  new ArrayList<>(list.size());
        listFilms.addAll(list);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "on createView");
        View view = inflater.inflate(R.layout.fragment_favoris, container, false);
        favorisListView = (ListView) view.findViewById(R.id.favorisListView);
        filmsAdapter = new ListMovieAdapter(getActivity(), R.layout.list_films, listFilms);
        favorisListView.setAdapter(filmsAdapter);
        // ListView Item Click Listener
        favorisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.v(TAG, "onItemClick:" + position);
                // ListView Clicked item value
                Movie movie = (Movie) favorisListView.getItemAtPosition(position);


                Log.v(TAG, "movie Selected id::" + movie.getId());
                ((MainActivity) getActivity()).showDetailFilm(movie);

            }
        });
        favorisListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, "onItemLongClick:" + position);
                // ListView Clicked item value
                final Movie movie = (Movie) favorisListView.getItemAtPosition(position);
                new AlertDialog.Builder(getActivity())
                        .setTitle(getActivity().getResources().getString(R.string.deleteDialogTitle))
                        .setMessage(getActivity().getResources().getString(R.string.deleteDialogContent))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                db.deleteOne(movie);
                                filmsAdapter.remove(movie);
                                filmsAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(getActivity().getResources().getString(R.string.no), null).show();
                return true;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

}
