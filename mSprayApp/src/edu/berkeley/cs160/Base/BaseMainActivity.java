package edu.berkeley.cs160.Base;

import edu.berkeley.cs160.mSpray.Constants;
import edu.berkeley.cs160.mSpray.DataStore;
import edu.berkeley.cs160.mSpray.ScanForeman;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

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
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}

	/*
	 * 8 (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume() this adapter only works when the
	 * activity is in resume.
	 */
	public void onResume() {
		super.onResume();
		
		/* If it's been a long time since an upload, then clear user data and restart the entry */
		if (System.currentTimeMillis() - Constants.LAST_UPLOAD_MILLIS > Constants.SESSION_TIMEOUT) {
		     DataStore.destroyAllData();
		     Constants.LAST_UPLOAD_MILLIS = System.currentTimeMillis();
		     Intent intent = new Intent(getApplicationContext(), ScanForeman.class);
             intent.putExtra(Constants.RESCAN_FORMAN, true);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(intent);
		 } else {
		     Log.d("debug123", "Current difference: " + ( System.currentTimeMillis() - Constants.LAST_UPLOAD_MILLIS));
		 }
		
//		Constants.LAST_UPLOAD_MILLIS = System.currentTimeMillis();
		
		if (mAdapter != null)
			mAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
	}

	public void onPause() {
		super.onPause();
		if (mAdapter != null)
			mAdapter.disableForegroundDispatch(this);
	}

	@Override
	public void onNewIntent(Intent intent) {
		// do nothing. We will override this in the activities that actually
		// need the RFID scanning abilities
	}
}
