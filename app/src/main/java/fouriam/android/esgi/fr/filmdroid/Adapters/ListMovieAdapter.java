package fouriam.android.esgi.fr.filmdroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.MainActivity;
import fouriam.android.esgi.fr.filmdroid.Models.Film;
import fouriam.android.esgi.fr.filmdroid.R;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;
import fouriam.android.esgi.fr.filmdroid.entities.MovieResultsPage;

/**
 * Created by Save92 on 02/07/15.
 */
public class ListMovieAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> items = new ArrayList<Movie>();
    private int itemLayout;
    private Context context;
    private Movie movie;

    public ListMovieAdapter(Context context, int resourceId, ArrayList<Movie> films) {
        super(context, resourceId, films);
        this.context = context;
        this.items = films;
    }


    /*private view holder class*/
    private class ViewHolder {
        TextView txtFilm;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        movie = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_films, null);
            holder = new ViewHolder();
            holder.txtFilm = (TextView) convertView.findViewById(R.id.filmTitle);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.txtFilm.setText(movie.getTitle());

        return convertView;
    }
    public Movie getItem(int position) {
        return items.get(position);
    }

}
