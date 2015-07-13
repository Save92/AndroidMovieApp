package fouriam.android.esgi.fr.filmdroid.entities;

import java.io.Serializable;
import java.util.List;

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
