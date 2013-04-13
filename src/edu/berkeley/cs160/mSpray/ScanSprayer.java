package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ScanSprayer extends Activity {
	Class nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_rfid);
        
        /* Grab numSprayers to pass on to the Paperwork activity */ 
        Bundle extras = this.getIntent().getExtras();
        final int numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        final int formNumber = extras.getInt(Constants.FORM_NUMBER);

        if (numSprayers == 1) {
        	setTitle("Spray worker");
        	nextActivity = PaperWorkChoiceActivity.class;
        } else if (numSprayers == 2) {
        	setTitle("Spray worker " + formNumber);
        	nextActivity = PaperWorkChoiceActivity.class;
        } else {
        	// Error
        }
        
        /* External Font */
        TextView tv = (TextView) findViewById(R.id.scan_rfid_instructions);
        tv.setTypeface(Constants.TYPEFACE);

        /* RFID Wizard of Oz */
        TimeBomb bomb = new TimeBomb() {
            @Override
            public void explode() {
            	Intent intent = new Intent(getApplicationContext(), PaperWorkChoiceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                intent.putExtra(Constants.FORM_NUMBER, formNumber);
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