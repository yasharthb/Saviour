package com.saviour.yasharth.saviour;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class GPSTracker extends Service implements LocationListener {

    private final Context context;

    boolean isGPSEnabled=false;
    boolean isNetworkEnabled =false;
    boolean canGetLocation=false;

    Location location;
    protected LocationManager locationManager;
    public GPSTracker(Context context){
        this.context=context;
    }
    //Create a get location Method.
    public Location getLocation(){
        try{

            locationManager=(LocationManager) context.getSystemService(LOCATION_SERVICE);

        }catch (Exception ex){

        }
    }

    public void onStatusChanged(String Provider, int status, Bundle extras){

    }
    //followings are the default method if we implement LocationListener
    public void onLocationChanged(Location location){

    }
    public void onProviderEnabled(String Provider){

    }
    public void onProviderDisabled(String Provider){

    }
    public IBinder onBind(Intent arg0){
    return null;
    }
}
