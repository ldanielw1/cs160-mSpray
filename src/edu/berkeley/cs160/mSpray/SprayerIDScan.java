package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SprayerIDScan extends Activity {
	String RFIDmode = "foreman";

	ImageView rfidPic;
	Button backButton;
	Button confirmButton;
	Button addSprayer;
	TextView scannedName;
	TextView scannedName2;
	TextView header;

	RelativeLayout scanRFIDLayout;
	RelativeLayout sprayersLayout;
	Handler handler;
	Boolean secondAdd;
	Boolean collectedSprayersRFIDs;
	Boolean collectedForemanRFIDs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_sprayer);

		addSprayer = (Button) findViewById(R.id.add_sprayer_button_addSprayer);
		backButton = (Button) findViewById(R.id.add_sprayer_button_back);
		confirmButton = (Button) findViewById(R.id.add_sprayer_button_confirm);

		scanRFIDLayout = (RelativeLayout) findViewById(R.id.scanRFIDLayout);
		sprayersLayout = (RelativeLayout) findViewById(R.id.sprayersListLayout);
		scannedName = (TextView) findViewById(R.id.add_sprayer_textView_PersonName);
		scannedName2 = (TextView) findViewById(R.id.add_sprayer_textView_PersonName2);
		header = (TextView) findViewById(R.id.add_sprayer_textview_header);

		// Initial visibility
		scannedName.setVisibility(View.GONE);
		scannedName2.setVisibility(View.GONE);

		secondAdd = false;
		collectedSprayersRFIDs = false;
		collectedForemanRFIDs = false;

		// Scan foreman first
		scanForemanID();

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
				if (!collectedForemanRFIDs) {
					scanForemanID();
					return;
				}
				if (!collectedSprayersRFIDs) {
					addSprayers();
					return;
				}

				if (collectedForemanRFIDs && collectedSprayersRFIDs) {
					Intent intent = new Intent(getApplicationContext(),
							GetGpsActivity.class);
					startActivity(intent);
				}

			};
		});

		addSprayer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setRFIDScannerVisibility(View.VISIBLE);
				header.setText("Sprayer IDs");
				TimeBomb();
			};
		});

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.TIME_BOMB_SWITH: {

					setRFIDScannerVisibility(View.GONE);

					if (RFIDmode.equals("foreman")) {
						scannedName.setVisibility(View.VISIBLE);
						addSprayer.setVisibility(View.INVISIBLE);
						collectedForemanRFIDs = true;
						return;
					}

					if (!secondAdd) {
						scannedName.setVisibility(View.VISIBLE);
						addSprayer.setText("Add another sprayer");
						secondAdd = true;
						return;
					}
					if (secondAdd) {
						scannedName2.setVisibility(View.VISIBLE);
						collectedSprayersRFIDs = true;
						return;
					}
				}
				}
			}
		};
	}

	private void changeRFIDMode(String m) {
		if (m.equals("sprayer")) {
			RFIDmode = "sprayer";
		} else if (m.equals("foreman")) {
			RFIDmode = "foreman";
		}
	}

	private void addSprayers() {
		Log.d("xyz", "addSprayers");

		changeRFIDMode("sprayer");

		scannedName.setText("BE02 - Mabunda YW");
		scannedName2.setText("BE05 - Simba TS");
		// header.setText("Sprayer IDs");
		header.setVisibility(View.INVISIBLE);
		addSprayer.setText("Add Sprayer");
		addSprayer.setVisibility(View.VISIBLE);

		scannedName.setVisibility(View.INVISIBLE);
		scannedName2.setVisibility(View.INVISIBLE);
		setRFIDScannerVisibility(View.INVISIBLE);

		// Resize button --> large
		MarginLayoutParams marginParams = new MarginLayoutParams(
				addSprayer.getLayoutParams());
		marginParams.setMargins(16, 0, 16, 0);
		addSprayer.setLayoutParams(marginParams);
		addSprayer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	private void scanForemanID() {
		changeRFIDMode("foreman");

		secondAdd = false;
		// Change data
		scannedName.setText("Simba R.");
		scannedName2.setText("");
		scannedName.setVisibility(View.INVISIBLE);
		scannedName2.setVisibility(View.INVISIBLE);
		header.setText("Your ID");

		setRFIDScannerVisibility(View.VISIBLE);
		addSprayer.setVisibility(View.INVISIBLE);
		// addSprayer.setText("Rescan your ID"); // TODO: not needed. "Back"
		// should do that.
		TimeBomb();
	}

	private void setRFIDScannerVisibility(int vis) {
		if (vis == View.GONE) {
			scanRFIDLayout.setVisibility(View.GONE);
			addSprayer.setVisibility(View.VISIBLE);
			sprayersLayout.setVisibility(View.VISIBLE);
			sprayersLayout.setVisibility(View.VISIBLE);
			header.setVisibility(View.VISIBLE);

			// Resize button --> small
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