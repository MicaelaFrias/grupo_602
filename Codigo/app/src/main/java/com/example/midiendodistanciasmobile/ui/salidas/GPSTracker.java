package com.example.midiendodistanciasmobile.ui.salidas;

import android.Manifest;
import android.app.Activity;
import android.content.*;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.midiendodistanciasmobile.R;
import com.example.midiendodistanciasmobile.Utilities.AlertDialog;

public final class GPSTracker implements LocationListener {

    private final Context mContext;

    public boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    Integer DIST_LIMITE = 8;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    double firstLatitude; // latitude
    double firstLongitude; // longitude
    float distMax = 0;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    /**
     * Function to get the user's current location
     *
     * @return
     */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.v("isGPSEnabled", "=" + isGPSEnabled);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);

            if (isGPSEnabled == false && isNetworkEnabled == false) {
                AlertDialog.displayAlertDialog((Activity) mContext,"GPS Deshabilitado", "El GPS se encuentra deshabilitado", "Ok");
                return null;
            }

            ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            if (isNetworkEnabled) {

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return null;
                }

                location = null;
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("Network", "Network");
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

                    return null;
                }

                location=null;
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                Log.d("GPS Enabled", "GPS Enabled ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Get distance in meters between two points given by two coordinates
     * */

    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        return dist;
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     * */
    public void stopGps() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
            Log.i("GPS-STOP", "Se detiene GPS");
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    public int getLimite(){
        return DIST_LIMITE;
    }

    @Override
    public void onLocationChanged(Location location) {

        if (firstLongitude == 0.0 || firstLatitude == 0.0){
            firstLongitude = location.getLongitude();
            firstLatitude = location.getLatitude();
        }

        Log.i("GPS-INITIAL", "Latitude:" + firstLatitude + ", Longitude:" + firstLongitude);
        Log.i("GPS-CHANGE", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        float dist = this.distFrom(firstLatitude, firstLongitude,location.getLatitude(), location.getLongitude());

        if (dist > distMax){
            distMax = dist;
        }

        if (dist >= DIST_LIMITE) {
            AlertDialog.displayAlertDialog((Activity) mContext,"GPS Distancia", "Distancia recorrida: " + dist + ". Ha superado la distancia límite (" + DIST_LIMITE + "mts). Se registra salida.", "Ok");
            this.stopGps();
            this.locationManager = null;
            Button button= (Button) ((Activity) this.mContext).findViewById(R.id.startButton);
            button.performClick();
        }

        Log.i("GPS-Dist", "Distancia:: " + dist);
    }

    public float getDistMax(){
        return distMax;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }

}
