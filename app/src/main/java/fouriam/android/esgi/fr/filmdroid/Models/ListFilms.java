package fouriam.android.esgi.fr.filmdroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Save92 on 01/07/15.
 */
public class ListFilms extends ArrayList<Film> implements Parcelable {
    private ArrayList<Film> listFilms;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(listFilms);
    }

    public ListFilms(Parcel in) {
        this.listFilms = in.readArrayList(ArrayList.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        @Override
        public ListFilms createFromParcel(Parcel source)
        {
            return new ListFilms(source);
        }

        @Override
        public ListFilms[] newArray(int size)
        {
            return new ListFilms[size];
        }
    };


    public ListFilms() {

    }

    public ListFilms(ArrayList<Film> list) {
        this.setListFilms(list);
    }

    public ArrayList<Film> getListFilms() {
        return listFilms;
    }

    public void setListFilms(ArrayList<Film> listFilms) {
        this.listFilms = listFilms;
    }
}
