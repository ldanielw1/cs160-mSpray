package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SprayerIDScan extends Activity {

	ImageView rfidPic;
	Button backButton;
	Button confirmButton;
	Button addSprayer;
	TextView sprayerName;
	TextView sprayerName2;

	RelativeLayout scanRFIDLayout;
	RelativeLayout sprayersLayout;
	Handler handler;
	Boolean secondAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_sprayer);

		addSprayer = (Button) findViewById(R.id.add_sprayer_button_addSprayer);
		backButton = (Button) findViewById(R.id.add_sprayer_button_back);
		confirmButton = (Button) findViewById(R.id.add_sprayer_button_confirm);

		scanRFIDLayout = (RelativeLayout) findViewById(R.id.scanRFIDLayout);
		sprayersLayout = (RelativeLayout) findViewById(R.id.sprayersListLayout);
		sprayerName = (TextView) findViewById(R.id.add_sprayer_textView_PersonName);
		sprayerName2 = (TextView) findViewById(R.id.add_sprayer_textView_PersonName2);

		// Initial visibility
		sprayerName.setVisibility(View.GONE);
		sprayerName2.setVisibility(View.GONE);

		secondAdd = false;

		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MSpray.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			};
		});

		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO
			};
		});

		addSprayer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setRFIDScannerVisibility(View.VISIBLE);
				TimeBomb();
			};
		});

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.TIME_BOMB_SWITH: {

					setRFIDScannerVisibility(View.GONE);

					if (!secondAdd) {
						sprayerName.setVisibility(View.VISIBLE);
						addSprayer.setText(addSprayer.getText().toString()
								+ " (additional)");
					}
					if (secondAdd) {
						sprayerName2.setVisibility(View.VISIBLE);
					}
					secondAdd = true;
				}
				}
			}
		};
	}

	private void setRFIDScannerVisibility(int vis) {
		if (vis == View.GONE) {
			scanRFIDLayout.setVisibility(View.GONE);
			addSprayer.setVisibility(View.VISIBLE);
			sprayersLayout.setVisibility(View.VISIBLE);
			sprayersLayout.setVisibility(View.VISIBLE);

			// Resize button
			MarginLayoutParams marginParams = new MarginLayoutParams(
					addSprayer.getLayoutParams());
			marginParams.setMargins(0, 0, 0, 0);
			addSprayer.setLayoutParams(marginParams);

			addSprayer.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		} else if (vis == View.VISIBLE) {
			scanRFIDLayout.setVisibility(View.VISIBLE);
			addSprayer.setVisibility(View.GONE);
			sprayersLayout.setVisibility(View.GONE);
			sprayersLayout.setVisibility(View.GONE);
		}
	}

	public void TimeBomb() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(3000);
						handler.sendMessage(Message.obtain(handler,
								Constants.TIME_BOMB_SWITH));

					}
				} catch (InterruptedException ex) {
				}

			}
		};

		thread.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mspray, menu);
		return true;
	}

}