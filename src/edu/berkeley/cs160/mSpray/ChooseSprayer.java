package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ChooseSprayer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sprayers);

        Bundle extras = this.getIntent().getExtras();
        final String sprayType = extras.getString(Constants.SPRAY_TYPE);

        setTitle("How many sprayers?");

        Button oneSprayerButton = (Button) findViewById(R.id.activity_choose_sprayers_1);
        oneSprayerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanSprayer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.SPRAY_TYPE, sprayType);
                intent.putExtra(Constants.NUM_SPRAYERS, 1);
                intent.putExtra(Constants.FORM_NUMBER, 1);
                startActivity(intent);
            }
        });
        oneSprayerButton.setTypeface(Constants.TYPEFACE);
        
        Button twoSprayerButton = (Button) findViewById(R.id.activity_choose_sprayers_2);
        twoSprayerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanSprayer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.SPRAY_TYPE, sprayType);
                intent.putExtra(Constants.FORM_NUMBER, 1);
                intent.putExtra(Constants.NUM_SPRAYERS, 2);
                startActivity(intent);
            }
        });
        twoSprayerButton.setTypeface(Constants.TYPEFACE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mspray, menu);
        return true;
    }
}