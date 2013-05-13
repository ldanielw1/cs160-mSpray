package edu.berkeley.cs160.GPS;

import android.app.Service;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class LocationHelper {

    static LocationListener locationListener;
    static LocationManager locationManager;

    public static void getLocation(Context context) {

        locationListener = new LocationListener() {

            @Override
            public void onProviderEnabled(String provider) {
                // Do nothing
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Do nothing
            }

            @Override
            public void onLocationChanged(Location location) {
                // Do nothing
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Do nothing
            }
        };

        locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);

        Log.v("Location Provider", "Location Provider: " + provider);

        locationManager.requestSingleUpdate(provider, locationListener, Looper.myLooper());
    }

    public static void terminate() {
        if (locationListener != null && locationManager != null)
            locationManager.removeUpdates(locationListener);
    }
}
