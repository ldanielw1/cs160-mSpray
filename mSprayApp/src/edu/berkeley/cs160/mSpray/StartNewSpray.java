package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StartNewSpray extends Activity {
	
	RelativeLayout startSpray;
	Button startSprayButton;
	Button doneButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_new_spray);

        setTitle("");

        TextView tv = (TextView) findViewById(R.id.activity_start_new_spray_header);
        tv.setText("I am: Foreman");
        tv.setTypeface(Constants.TYPEFACE);

        startSpray = (RelativeLayout) findViewById(R.id.activity_start_new_spray_fake_button);
        startSprayButton = (Button) findViewById(R.id.activity_start_new_spray_button);
        doneButton = (Button) findViewById(R.id.activity_done_spraying_button);
        
        startSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetGpsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        startSprayButton.setTypeface(Constants.TYPEFACE);
        
        
        doneButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MSpray.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
        doneButton.setTypeface(Constants.TYPEFACE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mspray, menu);
        return true;
    }
}