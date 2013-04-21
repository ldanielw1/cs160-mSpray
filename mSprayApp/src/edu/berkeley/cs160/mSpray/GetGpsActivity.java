package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetGpsActivity extends Activity {
    ProgressDialog progDialog;
    TextView LocationFound;
    TextView header;
    Handler handler;
    Button backButton;
    Button confirmButton;

    double latitude;
    double longitude;
    String latitudeNS;
    String longitudeEW;
    String acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_location);

        setTitle("Where are you?");

        LocationFound = (TextView) findViewById(R.id.gps_location_textview_contents);
        LocationFound.setTypeface(Constants.TYPEFACE);

        backButton = (Button) findViewById(R.id.gps_location_button_backButton);
        backButton.setTypeface(Constants.TYPEFACE);

        confirmButton = (Button) findViewById(R.id.gps_location_button_confirmButton);
        confirmButton.setTypeface(Constants.TYPEFACE);

        progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setMessage("Finding Your Location...");
        progDialog.show();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.GPS_FOUND: {
                        LocationFound.setVisibility(View.VISIBLE);
                        TextView gpsText = (TextView) findViewById(R.id.gps_location_textview_contents);
                        final StringBuilder gpsResultBuilder = new StringBuilder();
                        gpsResultBuilder.append("I am at: ");
                        gpsResultBuilder.append(latitude);
                        gpsResultBuilder.append(" ");
                        gpsResultBuilder.append(latitudeNS);
                        gpsResultBuilder.append(", ");
                        gpsResultBuilder.append(longitude);
                        gpsResultBuilder.append(" ");
                        gpsResultBuilder.append(longitudeEW);
                        gpsResultBuilder.append("\n Accuracy: ");
                        gpsResultBuilder.append(acc);
                        gpsResultBuilder.append(" meters");
                        gpsText.setText(gpsResultBuilder.toString());
                        DataStore.setGPS(latitude, longitude, latitudeNS, longitudeEW, acc);
                        System.out.println(acc);
                        progDialog.dismiss();
                        break;
                    }
                    case Constants.GPS_NOT_FOUND: {
                        // Need feedback for GPS not found in user interface
                        // TODO: Retry Dialog possible????
                        LocationFound.setVisibility(View.VISIBLE);
                        DataStore.setGPS(22.879, 30.729, "S", "E", "7");
                        progDialog.dismiss();
                        break;
                    }
                }
            }
        };

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaperWorkChoiceActivity.class);
                startActivity(intent);
            };
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartNewSpray.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            };
        });

        LocationFound.setVisibility(View.INVISIBLE);
        findGPS();
    }

    private void findGPS() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    getGPS();
                }
            }
        };

        thread.start();
    }

    public void getGPS() {
        LocationManager locManager;
        locManager = (LocationManager) getApplicationContext().getSystemService(
                Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;

        Location location = locManager.getLastKnownLocation(locationProvider);
        if (location == null) {
            System.out.println("trying network instead");
            locationProvider = LocationManager.NETWORK_PROVIDER;
            location = locManager.getLastKnownLocation(locationProvider);
        }
        if (location != null) {
            latitude = location.getLatitude();
            if (latitude > 0) {
                latitudeNS = "N";
            } else {
                latitude *= -1;
                latitudeNS = "S";
            }
            longitude = location.getLongitude();
            if (longitude > 0) {
                longitudeEW = "E";
            } else {
                longitude *= -1;
                longitudeEW = "W";
            }
            acc = "" + location.getAccuracy();
            handler.sendMessage(Message.obtain(handler, Constants.GPS_FOUND));
        } else {
            handler.sendMessage(Message.obtain(handler, Constants.GPS_NOT_FOUND));
        }

    }

}
