package com.example.writetag;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button scanButton;
	EditText newTagLabel;
	private NfcAdapter mAdapter;
	private boolean mInWriteMode;
	TextView status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		scanButton = (Button) findViewById(R.id.activity_main_button_changeTag);
		newTagLabel = (EditText) findViewById(R.id.activity_main_editText_tagNewValue);
		status = (TextView) findViewById(R.id.activity_main_textView_status);
		
		mAdapter = NfcAdapter.getDefaultAdapter(this);

		scanButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (newTagLabel.getText().toString().equals(""))
					reject("Please insert something into the EditText to write");
				else
					enableWriteMode();
			};
		});
	}
	
	private void disableWriteMode() {
		mAdapter.disableForegroundDispatch(this);
	}

	private void reject(String text) {
		status.setText(text);
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
			writeTag(tag);
		}
	}

	/**
	 * Format a tag and write our NDEF message
	 */
	private boolean writeTag(Tag tag) {
		// record to launch Play Store if app is not installed
		NdefRecord appRecord = NdefRecord
				.createApplicationRecord("com.netmagazine.nfcdemo");

		// record that contains our custom "retro console" game data, using
		// custom MIME_TYPE
		byte[] payload = newTagLabel.getText().toString().getBytes();
		// byte[] mimeBytes = MimeType.NFC_DEMO.getBytes(Charset
		// .forName("US-ASCII"));
		byte[] test = "tester".getBytes(Charset.forName("US-ASCII"));
		NdefRecord cardRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				test, new byte[0], payload);
		NdefMessage message = new NdefMessage(new NdefRecord[] { cardRecord
				//,appRecord
				});

		try {
			// see if tag is already NDEF formatted
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();

				if (!ndef.isWritable()) {
					reject("Read-only tag.");
					return false;
				}

				// work out how much space we need for the data
				int size = message.toByteArray().length;
				if (ndef.getMaxSize() < size) {
					reject("Tag doesn't have enough free space.");
					return false;
				}

				ndef.writeNdefMessage(message);
				reject("Tag written successfully.");
				return true;
			} else {
				// attempt to format tag
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						format.format(message);
						reject("Tag written successfully!\nClose this app and scan tag.");
						return true;
					} catch (IOException e) {
						reject("Unable to format tag to NDEF.");
						return false;
					}
				} else {
					reject("Tag doesn't appear to support NDEF format.");
					return false;
				}
			}
		} catch (Exception e) {
			reject("Failed to write tag");
		}

		return false;
	}

	private void enableWriteMode() {
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
