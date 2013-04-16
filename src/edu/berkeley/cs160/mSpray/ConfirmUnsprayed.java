package edu.berkeley.cs160.mSpray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gdata.util.ServiceException;

import edu.berkeley.cs160.spreadsheetUploader.GoogleSpreadsheetUploader;

public class ConfirmUnsprayed extends Activity {

    private static final String USERNAME_LABEL = "USERNAME";
    private static final String PASSWORD_LABEL = "PASSWORD";
    private static final String SPREADSHEET_TITLE_LABEL = "SPREADSHEET_TITLE";
    private static final String WORKSHEET_TITLE_LABEL = "WORKSHEET_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_unsprayed);

        setTitle("Is this correct?");

        Bundle extras = this.getIntent().getExtras();
        DataStore.roomsUnsprayed = extras.getInt(Constants.ROOMS_UNSPRAYED);
        DataStore.sheltersUnsprayed = extras.getInt(Constants.SHELTERS_UNSPRAYED);

        /* Populate paperwork table */
        TextView roomsUnsprayedValue = (TextView) findViewById(R.id.confirm_unsprayed_rooms_unsprayed_value);
        TextView sheltersUnsprayedValue = (TextView) findViewById(R.id.confirm_unsprayed_shelters_unsprayed_value);

        roomsUnsprayedValue.setText(Integer.toString(DataStore.roomsUnsprayed));
        sheltersUnsprayedValue.setText(Integer.toString(DataStore.sheltersUnsprayed));

        Button backButton = (Button) findViewById(R.id.confirm_unsprayed_button_backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        backButton.setTypeface(Constants.TYPEFACE);

        Button confirmButton = (Button) findViewById(R.id.confirm_unsprayed_button_confirmButton);
        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        confirmButton.setTypeface(Constants.TYPEFACE);
    }

    private void upload() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        final HashMap<String, String> uploadData = new HashMap<String, String>();
        uploadData.put("timeStamp", formatDateTime());
        if (tm.getDeviceId() != null)
            uploadData.put("imei", tm.getDeviceId());
        else
            uploadData.put("imei", generateSubstituteSignature());
        uploadData.put("lat", DataStore.lat);
        uploadData.put("latNS", DataStore.latNS);
        uploadData.put("lng", DataStore.lng);
        uploadData.put("lngEW", DataStore.lngEW);
        uploadData.put("accuracy", DataStore.accuracy);
        if (DataStore.homesteadSprayed) {
            uploadData.put("homesteadSprayed", Boolean.toString(true));
            uploadData.put("sprayerID", DataStore.sprayer1ID);
            if (DataStore.ddtUsed1) {
                uploadData.put("pyrethroidUsed1", Boolean.toString(false));
                uploadData.put("pyrethroidUsed2", Boolean.toString(false));
                uploadData.put("DDTUsed1", Boolean.toString(true));
                uploadData.put("DDTSprayedRooms1", Integer.toString(DataStore.ddtSprayedRooms1));
                uploadData.put("DDTSprayedShelters1",
                        Integer.toString(DataStore.ddtSprayedShelters1));
                uploadData.put("DDTRefill1", Boolean.toString(DataStore.ddtRefill1));
                uploadData.put("DDTUsed2", Boolean.toString(DataStore.ddtUsed2));
                if (DataStore.ddtUsed2) {
                    uploadData.put("sprayer2ID", DataStore.sprayer2ID);
                    uploadData
                            .put("DDTSprayedRooms2", Integer.toString(DataStore.ddtSprayedRooms2));
                    uploadData.put("DDTSprayedShelters2",
                            Integer.toString(DataStore.ddtSprayedShelters2));
                    uploadData.put("DDTRefill2", Boolean.toString(DataStore.ddtRefill2));
                }
            } else if (DataStore.pyrethroidUsed1) {
                uploadData.put("DDTUsed1", Boolean.toString(false));
                uploadData.put("DDTUsed2", Boolean.toString(false));
                uploadData.put("pyrethroidUsed1", Boolean.toString(true));
                uploadData.put("pyrethroidSprayedRooms1",
                        Integer.toString(DataStore.pyrethroidSprayedRooms1));
                uploadData.put("pyrethroidSprayedShelters1",
                        Integer.toString(DataStore.pyrethroidSprayedShelters1));
                uploadData.put("pyrethroidRefill1", Boolean.toString(DataStore.pyrethroidRefill1));
                uploadData.put("pyrethroidUsed2", Boolean.toString(DataStore.pyrethroidUsed2));
                if (DataStore.pyrethroidUsed2) {
                    uploadData.put("sprayer2ID", DataStore.sprayer2ID);
                    uploadData.put("pyrethroidSprayedRooms2",
                            Integer.toString(DataStore.pyrethroidSprayedRooms2));
                    uploadData.put("pyrethroidSprayedShelters2",
                            Integer.toString(DataStore.pyrethroidSprayedShelters2));
                    uploadData.put("pyrethroidRefill2",
                            Boolean.toString(DataStore.pyrethroidRefill2));
                }
            }
        } else {
            uploadData.put("homesteadSprayed", Boolean.toString(false));
            uploadData.put("pyrethroidUsed1", Boolean.toString(false));
            uploadData.put("pyrethroidUsed2", Boolean.toString(false));
            uploadData.put("DDTUsed1", Boolean.toString(false));
            uploadData.put("DDTUsed2", Boolean.toString(false));
            uploadData.put("sprayerID", DataStore.sprayer1ID);
            if (DataStore.sprayer2ID != null)
                uploadData.put("sprayer2ID", DataStore.sprayer2ID);
        }
        uploadData.put("unsprayedRooms", Integer.toString(DataStore.roomsUnsprayed));
        uploadData.put("unsprayedShelters", Integer.toString(DataStore.sheltersUnsprayed));
        uploadData.put("foreman", DataStore.foremanID);

        String usernameinFile = null;
        String passwordInFile = null;
        String spreadsheetTitleInFile = null;
        String worksheetTitleInFile = null;
        try {
            Scanner s;
            s = new Scanner(getAssets().open("authentication.txt"));

            while (s.hasNextLine()) {
                String[] nextLine = s.nextLine().split("=");
                if (nextLine[0].equals(USERNAME_LABEL))
                    usernameinFile = nextLine[1];
                else if (nextLine[0].equals(PASSWORD_LABEL))
                    passwordInFile = nextLine[1];
                else if (nextLine[0].equals(SPREADSHEET_TITLE_LABEL))
                    spreadsheetTitleInFile = nextLine[1];
                else if (nextLine[0].equals(WORKSHEET_TITLE_LABEL))
                    worksheetTitleInFile = nextLine[1];
                else
                    throw new IllegalArgumentException("Invalid authentication file");
            }

            if (usernameinFile == null || passwordInFile == null || spreadsheetTitleInFile == null
                    || worksheetTitleInFile == null) {
                Toast.makeText(getApplicationContext(),
                        "Authentication Error: Could not upload data", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error: Could not upload data correctly",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }

        final ProgressDialog progDialog = new ProgressDialog(ConfirmUnsprayed.this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setMessage("Uploading Data...");
        progDialog.show();

        final Handler uploadHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == Constants.UPLOAD_SUCCESSFUL) {
                    progDialog.dismiss();

                    Intent intent = new Intent(getApplicationContext(), FinishedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if (msg.what == Constants.UPLOAD_UNSUCCESSFUL) {
                    Toast.makeText(getApplicationContext(),
                            "Error: Could not upload data to server", Toast.LENGTH_SHORT).show();
                    progDialog.dismiss();
                }
            }
        };

        final String username = usernameinFile;
        final String password = passwordInFile;
        final String spreadsheetTitle = spreadsheetTitleInFile;
        final String worksheetTitle = worksheetTitleInFile;

        AsyncTask<String, Void, String> uploadTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    GoogleSpreadsheetUploader uploader = new GoogleSpreadsheetUploader(username,
                            password, spreadsheetTitle, worksheetTitle);
                    uploader.addRow(uploadData, uploadHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        uploadTask.execute();
    }

    private String generateSubstituteSignature() {
        // Unique identifier for some tablets
        String androidTabletID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        if (androidTabletID != null)
            return androidTabletID;

        final StringBuilder result = new StringBuilder();
        if (android.os.Build.MODEL != null) {
            result.append(android.os.Build.MODEL);
            result.append("||");
        }
        if (android.os.Build.FINGERPRINT != null) {
            result.append(android.os.Build.FINGERPRINT);
            result.append("||");
        }
        if (android.os.Build.ID != null) {
            result.append(android.os.Build.ID);
            result.append("||");
        }

        if (result.toString().equals(""))
            return "0";
        return result.toString();
    }

    @SuppressLint("SimpleDateFormat")
    private static String formatDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d = new Date(System.currentTimeMillis());
        String[] formattedDateArray = df.format(d).split(" ");

        String[] splitDate = formattedDateArray[0].split("/");
        int month = Integer.parseInt(splitDate[0]);
        int day = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);

        return month + "/" + day + "/" + year + " " + formattedDateArray[1];
    }
}
