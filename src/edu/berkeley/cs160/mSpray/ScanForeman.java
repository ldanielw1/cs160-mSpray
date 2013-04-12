package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

public class ScanForeman extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_rfid);
        
        setTitle("Identify yourself");
        
        /* RFID Wizard of Oz */
        TimeBomb bomb = new TimeBomb() {
        	public void explode() {
        		Intent intent = new Intent(getApplicationContext(), GetGpsActivity.class);
                startActivity(intent);        		
        	}
        };
        bomb.ignite();
        
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mspray, menu);
		return true;
	}
}