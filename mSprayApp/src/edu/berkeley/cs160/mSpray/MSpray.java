package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MSpray extends Activity {

    Button startSpray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mspray);

        startSpray = (Button) findViewById(R.id.activity_mspray_button);
        
        setTitle("Welcome to mSpray");

        // External font
        TextView tv = (TextView) findViewById(R.id.activity_mspray_header);
        Constants.TYPEFACE = Typeface.createFromAsset(getAssets(), Constants.FONT_PATH);

        // Apply fonts
        tv.setTypeface(Constants.TYPEFACE);
        tv.setTextSize(28);

        startSpray.setTypeface(Constants.TYPEFACE);
        startSpray.setTextSize(40);

        startSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataStore.startNewStoreSession();

                // Intent intent = new Intent(getApplicationContext(),
                // SprayerIDScan.class);
                Intent intent = new Intent(getApplicationContext(), ScanForeman.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            };
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mspray, menu);
        return true;
    }

}
