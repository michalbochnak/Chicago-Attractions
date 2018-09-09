//
// Michal Bochnak
// CS 478, Project #3
// UIC, April 2, 2018
//


package com.webvideoplayer.cs478project03chicagoattractionsinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;


//
// Reacts to the broadcast send from the apps with
// “edu.uic.cs478.sp18.project3 permission
//
public class Receiver extends BroadcastReceiver {

    private static final String TAG = "Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        intent.getAction();
        // lunch default activity
        PackageManager pm = context.getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(
                "com.webvideoplayer.cs478project03chicagoattractionsinfo");
        launchIntent.putExtra("activity", getActivityType(intent.getAction()));
        Log.i(TAG, "act type: " + getActivityType(intent.getAction()));
        context.startActivity(launchIntent);
    }

    // returns the activity to be launched
    private String getActivityType(String action) {
        if (action.contains("attractions"))
            return "attractions";
        else if (action.contains("restaurants"))
            return "restaurants";
        else
            return "none";
    }
}


