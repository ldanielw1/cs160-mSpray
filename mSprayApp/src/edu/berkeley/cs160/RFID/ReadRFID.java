package edu.berkeley.cs160.RFID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Handler;
import android.os.Message;

public class ReadRFID {
    private String returnValue;

    private Handler handler;

    /* Remember to put an intent filter on whatever activity class uses this
     * 
     * use getClass() for the parameter class
     * 
     * establish a handler in the given class */
    @SuppressWarnings("unused")
    public ReadRFID(Context context, Class<?> c, Handler h, Activity act) {
        // mAdapter = NfcAdapter.getDefaultAdapter(context);
        returnValue = "";
        handler = h;
    }

    public void onNewIntent(Intent intent) {
        // write to newly scanned tag
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        ReadTag(tag);
    }

    /**
     * Format a tag and read our NDEF message
     */
    public boolean ReadTag(Tag tag) {

        try {
            // see if tag is already NDEF formatted
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();

                if (!ndef.isWritable()) {
                    handler.sendMessage(Message.obtain(handler, RFIDConstants.RFID_WRONG_TYPE));
                    return false;
                }

                NdefMessage msg = ndef.getCachedNdefMessage();
                NdefRecord cardRecord = msg.getRecords()[0];
                String rfidDataField = new String(cardRecord.getPayload());
                setReturnValue(rfidDataField);
                handler.sendMessage(Message.obtain(handler, RFIDConstants.RFID_SUCCESS));
                return true;
            }
        } catch (Exception e) {
            handler.sendMessage(Message.obtain(handler, RFIDConstants.RFID_UNSUCCESS_READ));

        }

        return false;
    }

    private void setReturnValue(String text) {
        returnValue = text;
    }

    public String getReturnValue() {
        return returnValue;
    }

}
