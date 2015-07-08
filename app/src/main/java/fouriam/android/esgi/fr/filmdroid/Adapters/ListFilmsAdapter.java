package fouriam.android.esgi.fr.filmdroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.Models.Film;
import fouriam.android.esgi.fr.filmdroid.R;

/**
 * Created by Save92 on 01/07/15.
 */
public class ListFilmsAdapter extends ArrayAdapter<Film>{
    private List<Film> items = new ArrayList<Film>();
    private int itemLayout;
    private Context context;

    public ListFilmsAdapter(Context context, int resourceId, List<Film> films) {
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
        Film film = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_films, null);
            holder = new ViewHolder();
            holder.txtFilm = (TextView) convertView.findViewById(R.id.filmTitle);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtFilm.setText(film.getTitle());

        return convertView;
    }


}


