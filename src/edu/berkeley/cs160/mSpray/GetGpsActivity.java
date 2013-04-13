package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_location);
        
        header = (TextView) findViewById(R.id.gps_location_textview_title);
        header.setTypeface(Constants.TYPEFACE);

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
                        DataStore.lat = "22.879";
                        DataStore.latNS = "S";
                        DataStore.lng = "30.729";
                        DataStore.lngEW = "E";
                        DataStore.accuracy = "7";
                        progDialog.dismiss();
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
        initProgressDialog();
        findGPS();
    }

    private void initProgressDialog() {

    }

    private void findGPS() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                        handler.sendMessage(Message.obtain(handler, Constants.GPS_FOUND));
                    }
                } catch (InterruptedException ex) {
                }

            }
        };

        thread.start();
    }
}
