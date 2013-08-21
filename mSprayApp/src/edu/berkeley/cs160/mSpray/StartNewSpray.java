package edu.berkeley.cs160.mSpray;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.berkeley.cs160.Base.BaseMainActivity;
import edu.berkeley.cs160.GPS.GetGpsActivity;

public class StartNewSpray extends BaseMainActivity {

    LinearLayout startSpray;
    TextView startSprayText;
    Button completelyFinished;
    Button startMopup;
    TextView tv;
    TimeBomb bomb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_new_spray);

        setTitle("");

        Intent intent = getIntent();

        if (DataStore.secondTimeThrough)
            Toast.makeText(getApplicationContext(), "Data Saved. Thank you!", Toast.LENGTH_SHORT)
                    .show();

        tv = (TextView) findViewById(R.id.activity_start_new_spray_header);
        if (intent.getStringExtra(Constants.RFID_NAME) != null) {
            tv.setText("I am: " + intent.getStringExtra(Constants.RFID_NAME));
        } else if (DataStore.foremanID != null) {
            tv.setText("I am " + DataStore.foremanID);
        } else {
            tv.setText("I am not identified");
        }

        tv.setTypeface(Constants.TYPEFACE);

        startSpray = (LinearLayout) findViewById(R.id.activity_start_new_spray_fake_button);
        startSprayText = (TextView) findViewById(R.id.activity_start_new_spray_text);

        startSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataStore.startNewStoreSession();
                DataStore.screenTitlePrefix = Constants.MOPUP_SPRAY_TITLE_PREFIX;
                Intent nextIntent = new Intent(getApplicationContext(), GetGpsActivity.class);
                startActivity(nextIntent);
            }
        });
        startSprayText.setTypeface(Constants.TYPEFACE);
        
        /** Mop-up sprays are like normal sprays after the first screen */
        startMopup = (Button) findViewById(R.id.activity_start_mopup_spray_button);
        startMopup.setTypeface(Constants.TYPEFACE);
        startMopup.setVisibility(View.VISIBLE);
        
        startMopup.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		DataStore.startNewStoreSession();
        		DataStore.screenTitlePrefix = Constants.MOPUP_SPRAY_TITLE_PREFIX;
        		Intent nextIntent = new Intent(getApplicationContext(), GetGpsActivity.class);
                startActivity(nextIntent);
        	}
        });

        /**
         * Note that completelyFinished in the 1st iteration standpoint asks the
         * foreman whether they are the correct person while in the 2nd and
         * onward ask if the Foreman want to terminate the session.
         */
        completelyFinished = (Button) findViewById(R.id.activity_start_new_spray_button_finished);
        completelyFinished.setTypeface(Constants.TYPEFACE);
        completelyFinished.setVisibility(View.VISIBLE);

        if (DataStore.secondTimeThrough) {
            completelyFinished.setText(R.string.DoneForTheDay);
            completelyFinished.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    completelyFinished.setVisibility(View.INVISIBLE);
                    completelyFinished.setEnabled(false);
                    startSpray.setEnabled(false);
                    DataStore.destroyAllData();
                    Toast.makeText(getApplicationContext(), "Good bye", Toast.LENGTH_LONG).show();
                    DataStore.doneForDay = true;
                    bomb.ignite();
                }
            });
        } else {
            completelyFinished.setText("Scan badge again");
            completelyFinished.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextIntent = new Intent(getApplicationContext(), ScanForeman.class);
                    nextIntent.putExtra(Constants.RESCAN_FORMAN, true);
                    nextIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(nextIntent);
                }
            });
        }

        bomb = new TimeBomb() {
            @Override
            public void explode() {
                Intent nextIntent = new Intent(getApplicationContext(), ScanForeman.class);
                nextIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nextIntent);
            }
        };

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!DataStore.secondTimeThrough) {
                onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mspray, menu);
        return true;
    }
}
