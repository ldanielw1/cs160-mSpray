package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StartNewSpray extends Activity {

	RelativeLayout startSpray;
	Button startSprayButton;
	Button completelyFinished;
	TextView tv;
	TimeBomb bomb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_new_spray);

		setTitle("");

		Intent intent = getIntent();

		tv = (TextView) findViewById(R.id.activity_start_new_spray_header);
		if (intent.getStringExtra(Constants.RFID_NAME) != null) {
			tv.setText("I am: " + intent.getStringExtra(Constants.RFID_NAME));
		} else if (DataStore.foremanID != null) {
			tv.setText("I am: " + DataStore.foremanID);
		} else {
			tv.setText("I am: Foreman");
		}
		tv.setTypeface(Constants.TYPEFACE);

		startSpray = (RelativeLayout) findViewById(R.id.activity_start_new_spray_fake_button);
		startSprayButton = (Button) findViewById(R.id.activity_start_new_spray_button);

		startSpray.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DataStore.startNewStoreSession();
				Intent nextIntent = new Intent(getApplicationContext(),
						GetGpsActivity.class);
				startActivity(nextIntent);
			}
		});
		startSprayButton.setTypeface(Constants.TYPEFACE);

		if (DataStore.secondTimeThrough) {
			completelyFinished = (Button) findViewById(R.id.activity_start_new_spray_button_finished);
			completelyFinished.setVisibility(View.VISIBLE);
			completelyFinished.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					DataStore.destroyAllData();
					Toast.makeText(getApplicationContext(), "GOOD BYE",
							Toast.LENGTH_LONG).show();
					bomb.ignite();
				}
			});
			completelyFinished.setTypeface(Constants.TYPEFACE);
		}

		bomb = new TimeBomb() {
			@Override
			public void explode() {
				moveTaskToBack(true);
				finish();
			}
		};

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mspray, menu);
		return true;
	}
}
