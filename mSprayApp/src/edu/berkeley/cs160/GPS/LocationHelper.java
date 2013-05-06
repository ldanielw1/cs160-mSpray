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
				Log.v("LocationListener", "onProviderEnabled");
			}

			@Override
			public void onProviderDisabled(String provider) {
				Log.v("LocationListener", "onProviderDisabled");
			}

			@Override
			public void onLocationChanged(Location location) {
				Log.v("LocationListener", "onLocationChanged");
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}
		};

		LocationManager locationManager = (LocationManager) context
				.getSystemService(Service.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(criteria, true);

		Log.v("Provider", provider);

		locationManager.requestSingleUpdate(provider, locationListener,
				Looper.myLooper());
	}

	public static void terminate() {
		if (locationListener != null && locationManager != null)
			locationManager.removeUpdates(locationListener);
	}
}
