package edu.berkeley.cs160.mSpray;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.berkeley.cs160.Base.BaseMainActivity;
import edu.berkeley.cs160.RFID.RFIDConstants;
import edu.berkeley.cs160.RFID.ReadRFID;

public class ScanForeman extends BaseMainActivity {
    private Handler handler;
    private ReadRFID rfidData;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    IntentFilter tagDetected;
    IntentFilter[] filters;
    TextView startScan;
    Button skipScan;
    ImageView handHoldingBadge;
    TextView tv;
    LinearLayout scanForemanLayout;
    LinearLayout scanSelf; // fake button
    private boolean scanning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_rfid);

        if (DataStore.doneForDay) {
            DataStore.doneForDay = false;
            moveTaskToBack(true);
        }

        setTitle("Welcome to mSpray");

        // External font
        Constants.TYPEFACE = Typeface.createFromAsset(getAssets(), Constants.FONT_PATH);

        scanSelf = (LinearLayout) findViewById(R.id.activity_scan_rfid_fake_button);
        tv = (TextView) findViewById(R.id.scan_rfid_instructions);
        startScan = (TextView) findViewById(R.id.scan_rfid_textView_start_scan);
        skipScan = (Button) findViewById(R.id.scan_rfid_button_forgot_badge);
        handHoldingBadge = (ImageView) findViewById(R.id.scan_rfid_image);
        scanForemanLayout = (LinearLayout) findViewById(R.id.scan_rfid_layout);
        mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());

        scanForemanLayout.setVisibility(View.VISIBLE);
        handHoldingBadge.setVisibility(View.INVISIBLE);
        tv.setTypeface(Constants.TYPEFACE);
        tv.setText(R.string.welcome);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RFIDConstants.RFID_SUCCESS: {
                        DataStore.foremanID = rfidData.getReturnValue();
                        Intent intent = new Intent(getApplicationContext(), StartNewSpray.class);
                        intent.putExtra(Constants.RFID_NAME, rfidData.getReturnValue());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

        scanSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter != null) {
                    scanning = true;
                    scanForemanLayout.setVisibility(View.INVISIBLE);
                    handHoldingBadge.setVisibility(View.VISIBLE);
                    tv.setText(R.string.scanBadge);
                    enableReadMode();
                } else
                    Toast.makeText(getApplicationContext(), "Your Phone Can't Scan RFIDs",
                            Toast.LENGTH_LONG).show();
            };
        });
        startScan.setTypeface(Constants.TYPEFACE);

        skipScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataStore.foremanID = "not identified";
                Intent intent = new Intent(getApplicationContext(), StartNewSpray.class);
                intent.putExtra(Constants.RFID_NAME, Constants.DOESNT_HAVE_RFID);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            };
        });
        skipScan.setTypeface(Constants.TYPEFACE);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        rfidData = new ReadRFID(this, getClass(), handler, this);
        // reScanning();
    }

    @Override
    public void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        rfidData.ReadTag(tag);
    }

    private void enableReadMode() {
        // set up a PendingIntent to open the app when a tag is scanned
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(getApplicationContext(),
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        filters = new IntentFilter[] { tagDetected };

        mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (scanning) {
                scanForemanLayout.setVisibility(View.VISIBLE);
                handHoldingBadge.setVisibility(View.INVISIBLE);
            } else
                onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Problems with implementing this. There's still issues with the app not
     * being in resume when this method is called.
     */
    public void reScanning() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            boolean rescanning = bundle.getBoolean(Constants.RESCAN_FORMAN);
            if (rescanning) {
                if (mAdapter != null) {
                    scanForemanLayout.setVisibility(View.INVISIBLE);
                    handHoldingBadge.setVisibility(View.VISIBLE);
                    tv.setText(R.string.scanBadge);
                    enableReadMode();
                } else
                    Toast.makeText(getApplicationContext(), "Your Phone Can't Scan RFIDs",
                            Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mspray, menu);
        return true;
    }
}
