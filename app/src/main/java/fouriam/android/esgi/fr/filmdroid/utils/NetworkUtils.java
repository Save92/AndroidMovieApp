package fouriam.android.esgi.fr.filmdroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Save92 on 01/07/15.
 */
public class NetworkUtils {
    /**
     * Constructeur privé de la classe.
     * Empèche son instanciation (=> classe statique).
     */
    private NetworkUtils() {

    }

    public static boolean checkDeviceConnected(Context activity) {
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE); // Need permission : android.permission.ACCESS_NETWORK_STATE
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}
