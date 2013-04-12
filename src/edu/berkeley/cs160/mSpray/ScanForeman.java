package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ScanForeman extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_rfid);

        setTitle("Identify yourself");
        
        /* External Font */
        TextView tv = (TextView) findViewById(R.id.scan_rfid_instructions);
        tv.setTypeface(Constants.TYPEFACE);

        /* RFID Wizard of Oz */
        TimeBomb bomb = new TimeBomb() {
            @Override
            public void explode() {
                DataStore.foremanID = "Chauke RT";
                Intent intent = new Intent(getApplicationContext(), StartNewSpray.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
