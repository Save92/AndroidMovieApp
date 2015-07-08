package fouriam.android.esgi.fr.filmdroid.utils;

import android.util.Log;
import android.widget.Toast;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Save92 on 01/07/15.
 */
public class ErrorsHandlerUtils implements ErrorHandler {

    private Toast toast;
    String errorDescription;



    @Override public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
//        Log.v("ERROR HANDLER :", "" + r.getStatus());
//
//        Log.v("ERROR HANDLER :", ""  +r.getReason());
//        Log.v("ERROR HANDLER :", ""  +r.getUrl());
        if (r != null && r.getStatus() == 401) {
            errorDescription = "Mauvais identifiants. Veuillez réessayer.";
        } else if (r != null && r.getStatus() ==  403) {
            errorDescription = "Ce compte n'est pas activé ou n'est plus actif.";
        } else if (r != null && r.getStatus() ==  404) {
            errorDescription = "Not Found";
        } else if (r != null && r.getStatus() ==  422) {
            errorDescription = "DATA NOT FOUND";
        } else if (r != null && r.getStatus() == 500) {
            errorDescription = "Une erreur est survenue au niveau de la BDD.";
        } else {
            errorDescription = "Erreur inconnue. Veuillez réessayer.";
        }
        return new Exception(errorDescription);
    }

}