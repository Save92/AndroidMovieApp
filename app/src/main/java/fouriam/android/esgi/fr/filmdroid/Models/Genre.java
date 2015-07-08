package fouriam.android.esgi.fr.filmdroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Save92 on 02/07/15.
 */
public class Genre implements Parcelable{
    private Integer id;
    private String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public Genre(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        @Override
        public Genre createFromParcel(Parcel source)
        {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size)
        {
            return new Genre[size];
        }
    };

    public Genre() {

    }

    public Genre(Integer id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
