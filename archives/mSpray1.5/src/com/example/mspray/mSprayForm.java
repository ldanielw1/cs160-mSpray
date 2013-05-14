package com.example.mspray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class mSprayForm extends Activity {
    private static Context mContext;
    private static final String LOG_TAG = "FormWebView";
    private WebView mWebView;
    private Handler mHandler = new Handler();
    private String mStr;

    // CommentsDataSource dataSource;

    /** For exporting to Google spreadsheets. */
    private final String SPREADSHEET_FEED = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
    private final String USERNAME = "mSprayApp";
    private final String PASSWORD = "mSprayApp1.0";
    private final String spreadsheetTitle = "mSpray v1.5 Results";
    private final String[] uploadFieldsArray = { "timeStamp", "imei", "lat",
            "latNS", "lng", "lngEW", "accuracy", "homesteadSprayed",
            "sprayerID", "DDTUsed1", "DDTSprayedRooms1", "DDTSprayedShelters1",
            "DDTRefill1", "pyrethroidUsed1", "pyrethroidSprayedRooms1",
            "pyrethroidSprayedShelters1", "pyrethroidRefill1", "sprayer2ID",
            "DDTUsed2", "DDTSprayedRooms2", "DDTSprayedShelters2",
            "DDTRefill2", "pyrethroidUsed2", "pyrethroidSprayedRooms2",
            "pyrethroidSprayedShelters2", "pyrethroidRefill2",
            "unsprayedRooms", "unsprayedShelters", "foreman" };

    /**
     * @Override protected void onResume() { dataSource.open();
     *           super.onResume(); }
     * @Override protected void onPause() { dataSource.close(); super.onPause();
     *           }
     */
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_form);

        // dataSource = new CommentsDataSource(this);
        // dataSource.open();

        mContext = this;
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath("/mSpray/geodata");
        mWebView.setWebChromeClient(new CustomWebChromeClient());
        mWebView.addJavascriptInterface(new WebViewJavaScriptInterface(),
                "Android");

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "No SDCARD");
        } else {
            mWebView.loadUrl("file://"
                    + Environment.getExternalStorageDirectory()
                    + "/mSprayForm/newMSprayEntry.html");
        }

    }

    final class WebViewJavaScriptInterface {
        WebViewJavaScriptInterface() {
        }

        /*
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        public void submitToast(final String str) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mStr = str;

                    // do something with the results
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // go outside of the UI thread to do the network
                            // stuff.
                            // this is needed for OS 3 and above

                            /**
                             * get IMEI to uniquely identify users' posts to the
                             * server
                             */
                            TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                            String imei = mTelephonyMgr.getDeviceId(); // Requires
                            // READ_PHONE_STATE
                            if (TextUtils.isEmpty(imei))
                                imei = "0";

                            /** Get timeStamp for upload */
                            Date date = new Date();
                            Timestamp ts = new Timestamp(date.getTime());

                            writeFile(ts + "||" + imei + "||" + mStr);
                            writeGoogleSheet(ts + "||" + imei + "||" + mStr);
                        }
                    }).start();

                    Toast.makeText(mContext, "Thanks!", Toast.LENGTH_SHORT)
                            .show();
                    ((Activity) mContext).finish();
                    Log.d(LOG_TAG, str);
                }
            });
        }

        /** Show a toast from the web page */
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        public void cancelForm() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    ((Activity) mContext).finish();
                    Log.d(LOG_TAG, "form canceled");
                }
            });
        }

    }

    /**
     * Provides a hook for calling "alert" from javascript. Useful for debugging
     * your javascript.
     */
    final class CustomWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                JsResult result) {
            Log.d(LOG_TAG, message);
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            result.confirm();
            return true;
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }

    }

    public void writeFile(String str) {
        File myfile;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            try {
                File root = new File(Environment.getExternalStorageDirectory()
                        + "/mSpray");

                if (!root.exists()) {
                    // make directory if needed
                    root.mkdir();
                }
                if (root.canWrite()) {
                    // write the data to file on the device's SD card
                    myfile = new File(root, "mSpray.txt");
                    myfile.createNewFile();
                    FileWriter mywriter = new FileWriter(myfile, true);
                    BufferedWriter out = new BufferedWriter(mywriter);

                    // use out.write statements to write data...
                    Log.d(LOG_TAG, str);

                    out.write(str + "\n");
                    out.close();
                    out = null;
                    mywriter = null;
                    myfile = null;
                }
            } catch (IOException e) {
                Log.e("mSpray", "Could not write file " + e.getMessage());
            }

            // ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>)
            // getListAdapter();
            // Comment comment = dataSource.createComment(str);
            // adapter.add(comment);

        }
    }

    public void writeGoogleSheet(String str) {
        SurveyToGoogleSpreadsheetExporter exporter = new SurveyToGoogleSpreadsheetExporter(
                this.SPREADSHEET_FEED, this.USERNAME, this.PASSWORD,
                this.spreadsheetTitle, this.uploadFieldsArray);
        exporter.uploadAfterParsingString(str);
    }

}
