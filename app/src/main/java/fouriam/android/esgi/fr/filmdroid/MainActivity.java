package fouriam.android.esgi.fr.filmdroid;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import fouriam.android.esgi.fr.filmdroid.entities.Movie;

import java.util.ArrayList;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.Models.Film;
import fouriam.android.esgi.fr.filmdroid.entities.Person;
import fouriam.android.esgi.fr.filmdroid.entities.PersonResultsPage;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, ResultsFilmsFragment.OnFragmentInteractionListener, FilmFragment.OnFragmentInteractionListener, FavorisFragment.OnFragmentInteractionListener,
        ResultsActorsFragment.OnFragmentInteractionListener,ActorDetailFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActivity";
    private SearchFragment searchFragment;
    private ResultsFilmsFragment resultFragment;
    private FilmFragment filmFragment;
    private ActorDetailFragment actorDetailFragment;
    private ResultsActorsFragment resultsActorsFragment;
    private FavorisFragment favorisFragment;
    private ArrayList<Film> listFilms;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchFragment = new SearchFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.main_frame_layout, searchFragment, null)
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    public void showResultMovies(List<Movie> movies) {
        resultFragment = new ResultsFilmsFragment();
        Bundle extra = new Bundle();
        extra.putSerializable("listFilms", new ArrayList<>(movies));
        resultFragment.setArguments(extra);
        fragmentManager.beginTransaction()
                .add(R.id.main_frame_layout, resultFragment, null)
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    public void showResultActors(List<Person> actors) {
        Log.v(TAG, "showResultActor");
        resultsActorsFragment = new ResultsActorsFragment();
        Bundle extra = new Bundle();
        extra.putSerializable("listActors", new ArrayList<>(actors));
        resultsActorsFragment.setArguments(extra);
        //FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.main_frame_layout, resultsActorsFragment, null)
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    public void showDetailFilm(Movie movie) {
        Log.v(TAG, "showDetailFilm");
        filmFragment = new FilmFragment();
        Bundle extra = new Bundle();
        extra.putSerializable("movie", movie);
        filmFragment.setArguments(extra);
        fragmentManager.beginTransaction()
                .add(R.id.main_frame_layout, filmFragment, null)
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    public void showDetailActor(Person person) {
        Log.v(TAG, "showDetailActor");
        actorDetailFragment = new ActorDetailFragment();
        Bundle extra = new Bundle();
        extra.putSerializable("actor", person);
        actorDetailFragment.setArguments(extra);
        fragmentManager.beginTransaction()
                .add(R.id.main_frame_layout, actorDetailFragment, null)
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();


    }

    public void showFavoris() {
        Log.v(TAG, "showDetailActor");

        favorisFragment = new FavorisFragment();
        fragmentManager.beginTransaction()
                .add(R.id.main_frame_layout, favorisFragment, null)
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() ==1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
