package com.comrade.comrade.brodcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {

                //write code for internet is disconnected...

                Toast.makeText(context,status,Toast.LENGTH_LONG).show();

            } else {

                //write code for internet is connected...
                Toast.makeText(context,status,Toast.LENGTH_LONG).show();
            }
        }
    }
}