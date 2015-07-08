package fouriam.android.esgi.fr.filmdroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Save92 on 01/07/15.
 */
public class Film implements Parcelable {
    @SerializedName("id")
    private Integer filmId;
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String year;
    private String actors;
    @SerializedName("overview")
    private String synopsis;
    private String realisator;
    private ArrayList<Genre> genres = new ArrayList<>();
    private String nationality;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(year);
        parcel.writeString(actors);
        parcel.writeString(synopsis);
        parcel.writeString(realisator);
        parcel.writeTypedList(getGenres());
        parcel.writeString(nationality);
    }

    public Film(Parcel in) {
        this.title = in.readString();
        this.year = in.readString();
        this.actors = in.readString();
        this.synopsis = in.readString();
        this.realisator = in.readString();
        in.readTypedList(getGenres(), Genre.CREATOR);
        this.nationality = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        @Override
        public Film createFromParcel(Parcel source)
        {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size)
        {
            return new Film[size];
        }
    };

    public Film() {
        setGenres(new ArrayList<Genre>());
    }

    public Film(String title, String year, String actors, String synopsis, String realisator, ArrayList<Genre> genres, String nationality) {
        setTitle(title);
        setYear(year);
        setActors(actors);
        setSynopsis(synopsis);
        setRealisator(realisator);
        setGenres(genres);
        setNationality(nationality);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRealisator() {
        return realisator;
    }

    public void setRealisator(String realisator) {
        this.realisator = realisator;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
