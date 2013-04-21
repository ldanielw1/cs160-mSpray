package edu.berkeley.cs160.mSpray;

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
import android.widget.TextView;
import edu.berkeley.cs160.RFID.RFIDConstants;
import edu.berkeley.cs160.RFID.ReadRFID;

public class ScanForeman extends Activity {
    private Handler handler;
    private ReadRFID rfidData;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    IntentFilter tagDetected;
    IntentFilter[] filters;
    Button startScan;
    Button skipScan;
    ImageView handHoldingBadge;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_rfid);

        setTitle("Identify yourself");

        /* External Font */
        tv = (TextView) findViewById(R.id.scan_rfid_instructions);
        startScan = (Button) findViewById(R.id.scan_rfid_button_start_scan);
        skipScan = (Button) findViewById(R.id.scan_rfid_button_forgot_badge);
        handHoldingBadge = (ImageView) findViewById(R.id.scan_rfid_image);

        startScan.setVisibility(View.VISIBLE);
        skipScan.setVisibility(View.VISIBLE);
        handHoldingBadge.setVisibility(View.INVISIBLE);
        tv.setTypeface(Constants.TYPEFACE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RFIDConstants.RFID_SUCCESS: {
                        Intent intent = new Intent(getApplicationContext(), StartNewSpray.class);
                        intent.putExtra(Constants.RFID_NAME, rfidData.getReturnValue());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    }
                    case RFIDConstants.RFID_UNSUCCESS_READ: {
                        tv.setText("Unsuccessful Read Try Again");
                        break;

                    }
                    case RFIDConstants.RFID_WRONG_TYPE: {
                        tv.setText("Wrong RFID type");
                        break;
                    }
                }
            }
        };

        startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan.setVisibility(View.INVISIBLE);
                skipScan.setVisibility(View.INVISIBLE);
                handHoldingBadge.setVisibility(View.VISIBLE);
                enableReadMode();
            };
        });

        skipScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartNewSpray.class);
                intent.putExtra(Constants.RFID_NAME, Constants.DOESNT_HAVE_RFID);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            };
        });

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        rfidData = new ReadRFID(this, getClass(), handler, this);
        /* RFID Wizard of Oz */
        // TimeBomb bomb = new TimeBomb() {
        // @Override
        // public void explode() {
        // DataStore.foremanID = "Foreman";
        // Intent intent = new Intent(getApplicationContext(),
        // StartNewSpray.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // startActivity(intent);
        // }
        // };
        // bomb.ignite();

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

    private void enableReadMode() {
        rfidData.mInWriteMode = true;
        // set up a PendingIntent to open the app when a tag is scanned
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(getApplicationContext(),
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
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
