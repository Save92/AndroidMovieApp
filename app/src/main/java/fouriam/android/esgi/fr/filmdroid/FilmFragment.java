package fouriam.android.esgi.fr.filmdroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.Models.Film;
import fouriam.android.esgi.fr.filmdroid.Models.Genre;
import fouriam.android.esgi.fr.filmdroid.entities.CastMember;
import fouriam.android.esgi.fr.filmdroid.entities.Credits;
import fouriam.android.esgi.fr.filmdroid.entities.CrewMember;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;
import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;
import fouriam.android.esgi.fr.filmdroid.entities.Person;
import fouriam.android.esgi.fr.filmdroid.entities.PersonCredits;
import fouriam.android.esgi.fr.filmdroid.httpservice.FilmDroidService;
import fouriam.android.esgi.fr.filmdroid.services.MoviesService;
import fouriam.android.esgi.fr.filmdroid.services.SearchService;
import fouriam.android.esgi.fr.filmdroid.sqlite.SQLiteDatabaseHandler;
import fouriam.android.esgi.fr.filmdroid.utils.ErrorsHandlerUtils;
import fouriam.android.esgi.fr.filmdroid.utils.NetworkUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilmFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilmFragment#} factory method to
 * create an instance of this fragment.
 */
public class FilmFragment extends Fragment {


    private static final String TAG = "FilmFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Movie movie;
    private TextView filmTitle;
    private TextView filmYear;
    private TextView filmNationnality;
    private TextView filmGenre;
    private TextView filmRealisator;
    private TextView filmActors;
    private TextView filmSynopsis;
    private ImageView filmImage;
    private RelativeLayout layout;

    private Movie thisMovie;
    private ArrayList<HashMap<Integer, String>> listGenre;
    private FilmDroidService filmDroidService;
    private Toast toast;
    private Button addFavoriteBtn;
    private SQLiteDatabaseHandler db;

    private ProgressDialog progress;

    private OnFragmentInteractionListener mListener;

//    // TODO: Rename and change types and number of parameters
//    public static FilmFragment newInstance(String param1, String param2) {
//        FilmFragment fragment = new FilmFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public FilmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("movie");

        }

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        progress = new ProgressDialog(getActivity());
        db = new SQLiteDatabaseHandler(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_film, container, false);
        filmTitle = (TextView) view.findViewById(R.id.titleFilmText);
        filmYear = (TextView) view.findViewById(R.id.yearText);
        filmNationnality = (TextView) view.findViewById(R.id.nationnalityText);
        filmRealisator = (TextView) view.findViewById(R.id.realisatorText);
        filmActors = (TextView) view.findViewById(R.id.actorsText);
        filmSynopsis = (TextView) view.findViewById(R.id.synopsisText);
        filmSynopsis.setMovementMethod(new ScrollingMovementMethod());
        filmGenre = (TextView) view.findViewById(R.id.genreText);
        filmImage = (ImageView) view.findViewById(R.id.filmImage);
        layout =(RelativeLayout) view.findViewById(R.id.background);
        //putGenreForMovie();
        addFavoriteBtn = (Button) view.findViewById(R.id.addFavoriteBtn);
        addFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thisMovie.getId() != null) {

                    db.addOne(thisMovie);
                    toast.setText("Ajouté!");
                } else {
                    toast.setText("Erreur lors de l'ajout du favoris");
                }
                toast.show();

            }
        });
        goSearchMovie();



        // Inflate the layout for this fragment
        return view;
    }

    public void goSearchMovie() {
        new getMovieDetail().execute(movie.getId());
    }

    public void getCast() { new getCastForMovie().execute(movie.getId());}

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


    private class getCastForMovie extends AsyncTask<Integer, Void, Credits> {
        @Override
        protected void onPreExecute() { // Actions avant d’exe la requete
            super.onPreExecute();
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected Credits doInBackground(Integer... movieId) { // Exe en arriere plan
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            MoviesService moviesService = tmdb.moviesService();

            return moviesService.credits(movieId[0]);
        }

        @Override
        protected void onPostExecute(Credits currentCast) { // Action apres exe !

            String actors = "";
            String realisator = "";
            Integer nbActors = 0;
            Integer nbRealisator = 0;
            List<CrewMember> crew = currentCast.crew;
            for (int i = 0; i < crew.size() && nbRealisator < 2; i++) {
                if(crew.get(i).job.equals("Director"))
                {
                    if (nbRealisator == 0) {
                        realisator += crew.get(i).name;

                        nbRealisator++;
                    } else {
                        realisator += ", " + crew.get(i).name;

                        nbRealisator++;
                    }
                }



            }

            List<CastMember> cast = currentCast.cast;
            for (int i = 0; i < cast.size() && nbActors < 3; i++) {
                    if (nbActors == 0) {
                        actors += cast.get(i).name;
                        nbActors++;
                    } else {
                        actors += ", " + cast.get(i).name;
                        nbActors++;
                    }


            }

            filmActors.setText(actors);
            filmRealisator.setText(realisator);

            progress.dismiss();
        }
    }

    private class getMovieDetail extends AsyncTask<Integer, Void, Movie> {
        @Override
        protected void onPreExecute() { // Actions avant d’exe la requete
            super.onPreExecute();
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected Movie doInBackground(Integer... movieId) { // Exe en arriere plan
            Tmdb tmdb = new Tmdb();
            tmdb.setApiKey("4718f1a9036a1c190dad9301f401fb25");
            MoviesService moviesService = tmdb.moviesService();

            return moviesService.summary(movieId[0], "fr", null);
        }

        @Override
        protected void onPostExecute(Movie currentMovie) { // Action apres exe !
            thisMovie = currentMovie;

            filmTitle.setText(currentMovie.getTitle());
            SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
            if (currentMovie.getRelease_date() != null) {
                filmYear.setText(format.format(currentMovie.getRelease_date()));
            }
            Log.v(TAG, "movieID :"+ currentMovie.getId());
            String nationnality = "";
            if(currentMovie.getProduction_countries() != null) {
                for (int i = 0; i < currentMovie.getProduction_countries().size(); i++) {
                    nationnality += currentMovie.getProduction_countries().get(i).name + " ";
                }
            }
            filmNationnality.setText(nationnality);
            String genres = "";
            if(currentMovie.getGenres() != null) {
                for (int i = 0; i < currentMovie.getGenres().size(); i++) {

                    if(i > 0) {
                        genres += ", " + currentMovie.getGenres().get(i).name;
                    } else {
                        genres += currentMovie.getGenres().get(i).name;
                    }
                }
            }
            filmGenre.setText(genres);
            filmSynopsis.setText(currentMovie.getOverview());
            if(db.isInFavoris(thisMovie.getId())) {
                addFavoriteBtn.setVisibility(View.GONE);
            }
            String url = "http://image.tmdb.org/t/p/w780/"+currentMovie.getPoster_path();
            Picasso.with(getActivity()).load(url).into(filmImage);
            // Set background
            final String urlBack = "http://image.tmdb.org/t/p/w780/"+currentMovie.getBackdrop_path();
            layout.post(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(getActivity())
                            .load(urlBack)
                            .into(new Target() {
                                @Override
                                @TargetApi(16)
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    int sdk = android.os.Build.VERSION.SDK_INT;
                                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                        layout.setBackgroundDrawable(new BitmapDrawable(bitmap));
                                        layout.getBackground().setAlpha(65);
                                    } else {
                                        layout.setBackground(new BitmapDrawable(getResources(), bitmap));
                                        layout.getBackground().setAlpha(65);
                                    }
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                    // use error drawable if desired
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    // use placeholder drawable if desired
                                }
                            });
                }
            });


            getCast();
        }
    }
}
