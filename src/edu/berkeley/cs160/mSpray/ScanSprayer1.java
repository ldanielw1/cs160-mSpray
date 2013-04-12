package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class ScanSprayer1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_rfid);

        Bundle extras = this.getIntent().getExtras();
        final int numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        final int formNumber = extras.getInt(Constants.FORM_NUMBER);
        final String sprayType = extras.getString(Constants.SPRAY_TYPE);

        setTitle("Identify sprayer ");

        /* RFID Wizard of Oz */
        // TODO: THIS PART IS NOT FINISHED!!!!!!!!!!!!
        TimeBomb bomb = new TimeBomb() {
            @Override
            public void explode() {
                if (formNumber == 1)
                    DataStore.sprayer1ID = "Maringa HW";
                else if (formNumber == 2)
                    DataStore.sprayer2ID = "Mabunda YW";
                Intent intent = null;
                if (sprayType.equals(Constants.DDT))
                    intent = new Intent(getApplicationContext(), DDTActivity.class);
                else if (sprayType.equals(Constants.PYRETHROID))
                    intent = new Intent(getApplicationContext(), PyrethroidActivity.class);
                else if (sprayType.equals(Constants.NO_SPRAY))
                    intent = new Intent(getApplicationContext(), NoSprayActivity.class);
                intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                intent.putExtra(Constants.FORM_NUMBER, formNumber);
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