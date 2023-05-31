package com.mcss.upus.Core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class PackageReceiverSec extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onReceive(Context context, Intent intent) {

        // send this data to log
        String data = intent.getDataString() + "| Action: " + intent.getAction();

        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
    }
}
