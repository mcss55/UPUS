package com.mcss.upus.Util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.mcss.upus.Core.PackageReceiverSec;

public class BackgroundWorks {

    public static void set(PackageReceiverSec packageReceiverSec, Context context){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_FULLY_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGES_SUSPENDED);
        intentFilter.addAction(Intent.ACTION_INSTALL_PACKAGE);
        intentFilter.addAction(Intent.ACTION_UNINSTALL_PACKAGE);
        intentFilter.addDataScheme("Package");

        context.registerReceiver(packageReceiverSec, intentFilter);

    }

}
