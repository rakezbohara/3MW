package com.app.rakez.a3mw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.app.rakez.a3mw.services.DataExchange;

/**
 * Created by RAKEZ on 7/15/2017.
 */

public class UpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("NET", "Event Fired");
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        if (isConnected){
            Intent in = new Intent(context, DataExchange.class);
            context.startService(in);
            Log.i("NET", "connecte" +isConnected);
        }
        else{
            Log.i("NET", "not connecte" +isConnected);
        }
    }
}
