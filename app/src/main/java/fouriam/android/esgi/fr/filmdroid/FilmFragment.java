package fouriam.android.esgi.fr.filmdroid;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.entities.CastMember;
import fouriam.android.esgi.fr.filmdroid.entities.Credits;
import fouriam.android.esgi.fr.filmdroid.entities.CrewMember;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;
import fouriam.android.esgi.fr.filmdroid.services.MoviesService;
import fouriam.android.esgi.fr.filmdroid.sqlite.SQLiteDatabaseHandler;


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

    private Movie movie;
    private TextView filmTitle;
    private TextView filmYear;
    private TextView filmNationnality;
    private TextView filmGenre;
    private TextView filmRealisator;
    private TextView filmActors;
    private TextView filmSynopsis;
    private ImageView filmImage;
    private String urlImage;

    private Movie thisMovie;
    private Toast toast;
    private Button addFavoriteBtn;
    private SQLiteDatabaseHandler db;

    private ProgressDialog progress;

    private OnFragmentInteractionListener mListener;


    public FilmFragment() {
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
        addFavoriteBtn = (Button) view.findViewById(R.id.addFavoriteBtn);
        addFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thisMovie.getId() != null) {

                    db.addOne(thisMovie);
                    toast.setText(getActivity().getResources().getString(R.string.AddDone));
                    addFavoriteBtn.setVisibility(View.GONE);

                } else {
                    toast.setText(getActivity().getResources().getString(R.string.AddFavorisError));
                }
                toast.show();

            }
        });
        if(db.isInFavoris(movie.getId())) {
            addFavoriteBtn.setVisibility(View.GONE);
        }
        goSearchMovie();
        return view;
    }

    public void goSearchMovie() {
        new getMovieDetail().execute(movie.getId());
    }

    public void getCast() { new getCastForMovie().execute(movie.getId());}

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


    private void getMoviePoster(String url) {
        Picasso.with(getActivity()).load(url).into(filmImage);
    }

    private class getCastForMovie extends AsyncTask<Integer, Void, Credits> {
        @Override
        protected void onPreExecute() { // Actions avant d’exe la requete
            super.onPreExecute();
            progress.setMessage(getActivity().getResources().getString(R.string.Loading));
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
            progress.setMessage(getActivity().getResources().getString(R.string.Loading));
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
            urlImage = "http://image.tmdb.org/t/p/w780/"+currentMovie.getPoster_path();
            getMoviePoster(urlImage);
            getCast();
        }
    }
}
