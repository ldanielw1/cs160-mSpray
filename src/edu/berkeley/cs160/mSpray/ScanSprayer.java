package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class ScanSprayer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_rfid);

        setTitle("Identify your spray workers");

        /* RFID Wizard of Oz */
        // TODO: THIS PART IS NOT FINISHED!!!!!!!!!!!!
        TimeBomb bomb = new TimeBomb() {
            @Override
            public void explode() {
                Intent intent = new Intent(getApplicationContext(), GetGpsActivity.class);
                startActivity(intent);
            }
        };
        bomb.ignite();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mspray, menu);
        return true;
    }
}