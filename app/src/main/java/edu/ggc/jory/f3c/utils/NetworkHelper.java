package edu.ggc.jory.f3c.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Jory on 3/11/17.
 */

public class NetworkHelper {

    public static boolean hasNetworkAccess(Context context) {
        ConnectivityManager cn  = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);


        try {
            NetworkInfo ni = cn.getActiveNetworkInfo();
            return ni != null && ni.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}
