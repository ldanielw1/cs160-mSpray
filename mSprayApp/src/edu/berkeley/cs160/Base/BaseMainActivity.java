package edu.berkeley.cs160.Base;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;

/*
 * Originally created to help avoid that weird pop up screen in the Nexus when you aren't on the correct 
 * activity and you scan the rfid. 
 */
public class BaseMainActivity extends Activity {
	private NfcAdapter mAdapter; 
	private PendingIntent pendingIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mAdapter != null)
			enableOverrideNexusRfidReader();
	}
	
	private void enableOverrideNexusRfidReader() {
		// set up a PendingIntent to open the app when a tag is scanned
		mAdapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(
		  this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}
	
	/*8
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 * this adapter only works when the activity is in resume.
	 */
	public void onResume(){
		super.onResume();
		mAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
	}
	
	public void onPause(){
		super.onPause();
		mAdapter.disableForegroundDispatch(this);
	}
	
	@Override
	public void onNewIntent(Intent intent) {
			// do nothing. We will override this in the activities that actually need the RFID scanning abilities
	}
}
