package fouriam.android.esgi.fr.filmdroid;

import android.support.v4.app.Fragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fouriam.android.esgi.fr.filmdroid.entities.Movie;

import java.util.ArrayList;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.entities.Person;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, ResultsFilmsFragment.OnFragmentInteractionListener, FilmFragment.OnFragmentInteractionListener, FavorisFragment.OnFragmentInteractionListener,
        ResultsActorsFragment.OnFragmentInteractionListener,ActorDetailFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActivity";
    private Fragment searchFragment, resultFragment, filmFragment, actorDetailFragment, resultsActorsFragment, favorisFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            searchFragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_frame_layout, searchFragment, null)
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        } else {
            Log.v(TAG, "onRestoreInstanceState" + savedInstanceState.getString("curFilmTitle"));
            searchFragment = getSupportFragmentManager().getFragment(savedInstanceState,"searchFragment");
        }
     }

    public void showResultMovies(List<Movie> movies) {
        resultFragment = new ResultsFilmsFragment();
        Bundle extra = new Bundle();
        extra.putSerializable("listFilms", new ArrayList<>(movies));
        resultFragment.setArguments(extra);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame_layout, resultFragment, null)
                .addToBackStack(null)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void showResultActors(List<Person> actors) {
        Log.v(TAG, "showResultActor");
        resultsActorsFragment = new ResultsActorsFragment();
        Bundle extra = new Bundle();
        extra.putSerializable("listActors", new ArrayList<>(actors));
        resultsActorsFragment.setArguments(extra);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame_layout, resultsActorsFragment, null)
                .addToBackStack(null)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void showDetailFilm(Movie movie) {
        Log.v(TAG, "showDetailFilm");
        filmFragment = new FilmFragment();
        Bundle extra = new Bundle();
        extra.putSerializable("movie", movie);
        filmFragment.setArguments(extra);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame_layout, filmFragment, null)
                .addToBackStack(null)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void showDetailActor(Person person) {
        Log.v(TAG, "showDetailActor");
        actorDetailFragment = new ActorDetailFragment();
        Bundle extra = new Bundle();
        extra.putSerializable("actor", person);
        actorDetailFragment.setArguments(extra);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame_layout, actorDetailFragment, null)
                .addToBackStack(null)
                .commit();
        getSupportFragmentManager().executePendingTransactions();


    }

    public void showFavoris() {
        Log.v(TAG, "showDetailActor");

        favorisFragment = new FavorisFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame_layout, favorisFragment, null)
                .addToBackStack(null)
                .commit();
        getSupportFragmentManager().executePendingTransactions();


    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        searchFragment.onSaveInstanceState(outState);
        Log.v(TAG, "onSaveInstanceState" + outState.getString("curFilmTitle"));
        getSupportFragmentManager().putFragment(outState, "searchFragment",searchFragment);
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() ==1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
