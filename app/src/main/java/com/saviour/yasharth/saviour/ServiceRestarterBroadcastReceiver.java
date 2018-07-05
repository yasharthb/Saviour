package com.saviour.yasharth.saviour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceRestarterBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(ServiceRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops!!");
        context.startService(new Intent(context, ShakeService.class));;
    }
}
