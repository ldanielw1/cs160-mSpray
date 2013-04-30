package edu.berkeley.cs160.mSpray;

import edu.berkeley.cs160.RFID.RFIDConstants;
import edu.berkeley.cs160.RFID.ReadRFID;
import edu.berkeley.cs160.mSpray.R.drawable;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScanSprayer extends Activity {
	private Handler handler;
	private ReadRFID rfidData;
	private NfcAdapter mAdapter;
	private PendingIntent pendingIntent;
	IntentFilter tagDetected;
	IntentFilter[] filters;
	ImageView handHoldingBadge;
	TextView tv;
	Button startScan;
	Button skipScan;
	LinearLayout scanForemanLayout;
	RelativeLayout scanSelf;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_rfid);

		Bundle extras = this.getIntent().getExtras();
		final int numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
		final int formNumber = extras.getInt(Constants.FORM_NUMBER);
		final String sprayType = extras.getString(Constants.SPRAY_TYPE);

		scanSelf = (RelativeLayout) findViewById(R.id.activity_scan_rfid_fake_button);
		startScan = (Button) findViewById(R.id.scan_rfid_button_start_scan);
		skipScan = (Button) findViewById(R.id.scan_rfid_button_forgot_badge);
		handHoldingBadge = (ImageView) findViewById(R.id.scan_rfid_image);
		scanForemanLayout = (LinearLayout) findViewById(R.id.scan_rfid_layout);
		mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());

		scanForemanLayout.setVisibility(View.VISIBLE);
		handHoldingBadge.setVisibility(View.INVISIBLE);

		setTitle("Identify sprayer ");
		setSprayerText();

		startScan.setCompoundDrawablesWithIntrinsicBounds(0,
				drawable.sprayworker, 0, 0);

		/* External Font */
		tv = (TextView) findViewById(R.id.scan_rfid_instructions);
		tv.setTypeface(Constants.TYPEFACE);
		tv.setText(R.string.scanBadge);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case RFIDConstants.RFID_SUCCESS: {
					if (formNumber == 1)
						DataStore.sprayer1ID = rfidData.getReturnValue();
					else if (formNumber == 2)
						DataStore.sprayer2ID = rfidData.getReturnValue();
					setSprayerFlag();
					Intent intent = null;
					if (sprayType.equals(Constants.DDT))
						intent = new Intent(getApplicationContext(),
								DDTActivity.class);
					else if (sprayType.equals(Constants.KORTHRINE))
						intent = new Intent(getApplicationContext(),
								KOrthrineActivity.class);
					else if (sprayType.equals(Constants.FENDONA))
						intent = new Intent(getApplicationContext(),
								FendonaActivity.class);
					else if (sprayType.equals(Constants.NO_SPRAY))
						intent = new Intent(getApplicationContext(),
								NoSprayActivity.class);
					intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
					intent.putExtra(Constants.FORM_NUMBER, formNumber);
					startActivity(intent);
					break;
				}
				case RFIDConstants.RFID_UNSUCCESS_READ: {
					Toast.makeText(getApplicationContext(),
							"Unsuccessful Read Try Again", Toast.LENGTH_LONG)
							.show();
					break;

				}
				case RFIDConstants.RFID_WRONG_TYPE: {
					Toast.makeText(getApplicationContext(), "Wrong RFID type",
							Toast.LENGTH_LONG).show();
					break;
				}
				}
			}
		};

		scanSelf.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				scanForemanLayout.setVisibility(View.INVISIBLE);
				handHoldingBadge.setVisibility(View.VISIBLE);
				tv.setText(R.string.scanBadge);
				enableReadMode();
			};
		});

		startScan.setTypeface(Constants.TYPEFACE);

		skipScan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!DataStore.scannedFirstSprayer) {
					DataStore.sprayer1ID = "Sprayer 1";
				} else
					DataStore.sprayer2ID = "Sprayer 2";

				Intent intent = null;
				if (sprayType.equals(Constants.DDT))
					intent = new Intent(getApplicationContext(),
							DDTActivity.class);
				else if (sprayType.equals(Constants.KORTHRINE))
					intent = new Intent(getApplicationContext(),
							KOrthrineActivity.class);
				else if (sprayType.equals(Constants.FENDONA))
					intent = new Intent(getApplicationContext(),
							FendonaActivity.class);
				else if (sprayType.equals(Constants.NO_SPRAY))
					intent = new Intent(getApplicationContext(),
							NoSprayActivity.class);
				intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
				intent.putExtra(Constants.FORM_NUMBER, formNumber);
				startActivity(intent);
			};
		});
		skipScan.setTypeface(Constants.TYPEFACE);

		mAdapter = NfcAdapter.getDefaultAdapter(this);
		rfidData = new ReadRFID(this, getClass(), handler, this);

	}

	@Override
	public void onNewIntent(Intent intent) {
		if (rfidData.mInWriteMode) {
			rfidData.mInWriteMode = false;

			// write to newly scanned tag
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			rfidData.ReadTag(tag);

		}
	}

	public void setSprayerText() {
		if (!DataStore.scannedFirstSprayer) {
			startScan.setText(Constants.SCAN_SPRAYER + " 1");
		} else
			startScan.setText(Constants.SCAN_SPRAYER + " 2");
	}
	
	/**
	 * This is an important flag to set because you want to have the correct labels 
	 * depending if you are on the second or first sprayer. 
	 * 
	 */
	public void setSprayerFlag(){
		if (!DataStore.scannedFirstSprayer) {
			DataStore.scannedFirstSprayer = true;
		}
	}

	private void enableReadMode() {
		rfidData.mInWriteMode = true;
		// set up a PendingIntent to open the app when a tag is scanned
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(
				getApplicationContext(), getClass())
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		filters = new IntentFilter[] { tagDetected };

		mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mspray, menu);
		return true;
	}
}