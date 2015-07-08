package fouriam.android.esgi.fr.filmdroid.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fouriam.android.esgi.fr.filmdroid.entities.Movie;

/**
 * Created by Save92 on 06/07/15.
 */
public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "SQLiteDatabaseHandler";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FavorisDB";
    private static final String TABLE_NAME = "FavorisTable";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MOVIE_ID = "id_movie";
    private static final String[] COLONNES = { KEY_ID, KEY_NAME, KEY_MOVIE_ID};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG,"SQLite DB : Constructeur ");

    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

        String CREATION_TABLE_FAVORIS = "CREATE TABLE FavorisTable ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
                + "id_movie INTEGER)";

        arg0.execSQL(CREATION_TABLE_FAVORIS);
        Log.i("SQLite DB", "Creation");

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(arg0);
        Log.i("SQLite DB", "Upgrade");


    }

    public void deleteOne(Movie movie) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, // table
                "id_movie = ?", new String[] { String.valueOf(movie.getId()) });
        db.close();
        Log.i("SQLite DB : Delete : ", movie.toString());

    }

    public Movie showOne(int id_movie) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,COLONNES, " id_movie = ?",
                new String[] { String.valueOf(id_movie) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Movie movie = new Movie();
        movie.setId(Integer.parseInt(cursor.getString(2)));
        movie.setTitle(cursor.getString(1));
        // log
        Log.i(TAG,"SQLite DB : Show one  : id=  "+id_movie +" : " +movie.getTitle());

        return movie;
    }

    public boolean isInFavoris(int id_movie) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,COLONNES, " id_movie = ?",
                new String[] { String.valueOf(id_movie) }, null, null, null, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<Movie> showAll() {

        List<Movie> movies = new LinkedList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Movie current_movie = null;
        if (cursor.moveToFirst()) {
            do {
                current_movie = new Movie();
                current_movie.setId(Integer.parseInt(cursor.getString(2)));
                current_movie.setTitle(cursor.getString(1));
                movies.add(current_movie);
            } while (cursor.moveToNext());
        }
        for (int i = 0; i < movies.size(); i++) {
            Log.i(TAG, "SQLite DB : Show All  : " + movies.get(i).getTitle());
        }
        return movies;
    }

    public void addOne(Movie movie) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, movie.getTitle());
        values.put(KEY_MOVIE_ID, movie.getId());
        // insertion
        db.insert(TABLE_NAME, null, values);

        db.close();
        Log.i(TAG,"SQLite DB : Add one  : id=  "+movie.getId() + " : " + movie.getTitle());
    }

    public int updateOne(Movie movie) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, movie.getTitle());
        values.put(KEY_MOVIE_ID, movie.getId());

        int i = db.update(TABLE_NAME, values,"id = ?",
                new String[] { String.valueOf(movie.getId()) });

        db.close();
        Log.i(TAG,"SQLite DB : Update one  : id=  "+movie.getId() + " : "+ movie.getTitle());

        return i;
    }

}
