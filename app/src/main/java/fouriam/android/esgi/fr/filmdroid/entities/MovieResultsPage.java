package fouriam.android.esgi.fr.filmdroid.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FPierre on 01/06/15.
 */
public class MovieResultsPage implements Serializable{

    private List<Movie> results;

    public MovieResultsPage() {

    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
