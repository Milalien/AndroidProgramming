package com.example.android_harjoitukset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ApmReceiver extends BroadcastReceiver {

    public static final String TAG = "AirplaneModeReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {

        boolean state = intent.getBooleanExtra("state", false);
        CharSequence textOn = context.getResources().getString(R.string.apmTextOn);
        CharSequence textOff = context.getResources().getString(R.string.apmTextOff);

        int duration = Toast.LENGTH_LONG;

        Log.i(TAG, "State: " + state);
        Toast toast;
        if (state) {
            toast = Toast.makeText(context, textOn, duration);
        } else {
            toast = Toast.makeText(context, textOff, duration);
        }
        toast.show();
    }
}