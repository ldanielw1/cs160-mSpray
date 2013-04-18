package com.example.readrfid;

import java.io.IOException;
import java.nio.charset.Charset;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	Button readRFID;
	TextView RFIDtext;
	private NfcAdapter mAdapter;
	private boolean mInWriteMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		readRFID = (Button) findViewById(R.id.activity_main_button_readRFID);
		RFIDtext = (TextView) findViewById(R.id.activity_main_textview_RFIDtext);

		readRFID.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enableReadMode();
			};
		});

		mAdapter = NfcAdapter.getDefaultAdapter(this);

	}

	/**
	 * Called when our blank tag is scanned executing the PendingIntent
	 */
	@Override
	public void onNewIntent(Intent intent) {
		if (mInWriteMode) {
			mInWriteMode = false;

			// write to newly scanned tag
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			ReadTag(tag);
		}
	}

	/**
	 * Format a tag and read our NDEF message
	 */
	private boolean ReadTag(Tag tag) {

		try {
			// see if tag is already NDEF formatted
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();

				if (!ndef.isWritable()) {
					reject("Read-only tag.");
					return false;
				}

				// NdefMessage msg = (NdefMessage) rawMsgs[0];
				// NdefRecord cardRecord = msg.getRecords()[0];
				// String consoleName = new String(cardRecord.getPayload());

				NdefMessage msg = ndef.getCachedNdefMessage();
				NdefRecord cardRecord = msg.getRecords()[0];
				String consoleName = new String(cardRecord.getPayload());

				// String decoded = new String(tag.getId(), "UTF-8");
				reject(consoleName);
				return true;
				// } else {
				// // attempt to format tag
				// NdefFormatable format = NdefFormatable.get(tag);
				// if (format != null) {
				// try {
				// format.connect();
				// format.format(message);
				// reject("Tag written successfully!\nClose this app and scan tag.");
				// return true;
				// } catch (IOException e) {
				// reject("Unable to format tag to NDEF.");
				// return false;
				// }
				// } else {
				// reject("Tag doesn't appear to support NDEF format.");
				// return false;
				// }
			}
		} catch (Exception e) {
			reject("Failed to write tag");
		}

		return false;
	}

	private void disableWriteMode() {
		mAdapter.disableForegroundDispatch(this);
	}

	private void reject(String text) {
		RFIDtext.setText(text);
	}

	private void enableReadMode() {
		mInWriteMode = true;

		// set up a PendingIntent to open the app when a tag is scanned
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, getClass())
						.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(
				NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter[] filters = new IntentFilter[] { tagDetected };

		mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
