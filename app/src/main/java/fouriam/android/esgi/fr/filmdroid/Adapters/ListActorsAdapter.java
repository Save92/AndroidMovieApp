package fouriam.android.esgi.fr.filmdroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fouriam.android.esgi.fr.filmdroid.R;
import fouriam.android.esgi.fr.filmdroid.entities.Movie;
import fouriam.android.esgi.fr.filmdroid.entities.Person;

/**
 * Created by Save92 on 02/07/15.
 */
public class ListActorsAdapter extends ArrayAdapter<Person> {
    private ArrayList<Person> items = new ArrayList<Person>();
    private int itemLayout;
    private Context context;

    public ListActorsAdapter(Context context, int resourceId, ArrayList<Person> actors) {
        super(context, resourceId, actors);
        this.context = context;
        this.items = actors;
    }


    /*private view holder class*/
    private class ViewHolder {
        TextView txtActor;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Person actor = items.get(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_actors, null);
            holder = new ViewHolder();
            holder.txtActor = (TextView) convertView.findViewById(R.id.actorsName);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtActor.setText(actor.getName());

        return convertView;
    }

    public Person getItem(int position) {
        return items.get(position);
    }
}