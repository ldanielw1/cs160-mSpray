package edu.berkeley.cs160.mSpray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gdata.util.ServiceException;

import edu.berkeley.cs160.Base.BaseMainActivity;
import edu.berkeley.cs160.spreadsheetUploader.GoogleSpreadsheet;

public class ConfirmUnsprayed extends BaseMainActivity {

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
        DataStore.reasonUnsprayed = extras.getString(Constants.REASON_UNSPRAYED);

        /* Populate paperwork table */
        TextView roomsUnsprayedValue = (TextView) findViewById(R.id.confirm_unsprayed_rooms_unsprayed_value);
        TextView sheltersUnsprayedValue = (TextView) findViewById(R.id.confirm_unsprayed_shelters_unsprayed_value);
        TextView unsprayedReasonValue = (TextView) findViewById(R.id.confirm_unsprayed_reason_value);

        roomsUnsprayedValue.setText(Integer.toString(DataStore.roomsUnsprayed));
        sheltersUnsprayedValue.setText(Integer.toString(DataStore.sheltersUnsprayed));
        unsprayedReasonValue.setText(DataStore.reasonUnsprayed);

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
        final HashMap<String, String> uploadData = organizeUploadData();
        List<String> uploadCredentials = getUploadCredentials();

        final String username = uploadCredentials.get(0);
        final String password = uploadCredentials.get(1);
        final String spreadsheetTitle = uploadCredentials.get(2);
        final String worksheetTitle = uploadCredentials.get(3);

        AsyncTask<String, Void, String> uploadTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    GoogleSpreadsheet uploader = new GoogleSpreadsheet(username, password,
                            spreadsheetTitle, worksheetTitle);
                    uploader.addRow(uploadData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        uploadTask.execute();
        Intent intent = new Intent(getApplicationContext(), FinishedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private HashMap<String, String> organizeUploadData() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        HashMap<String, String> uploadData = new HashMap<String, String>();
        uploadData.put("timeStamp", formatDateTime());
        if (tm.getDeviceId() != null)
            uploadData.put("imei", tm.getDeviceId());
        else
            uploadData.put("imei", generateSubstituteSignature());

        uploadData.put("lat", DataStore.lat);
        uploadData.put("latNS", DataStore.latNS);
        if (DataStore.latNS.equals("S"))
            uploadData.put("mapLat", "-" + DataStore.lat);
        else
            uploadData.put("mapLat", DataStore.lat);
        uploadData.put("lng", DataStore.lng);
        uploadData.put("lngEW", DataStore.lngEW);
        if (DataStore.lngEW.equals("W"))
            uploadData.put("mapLng", "-" + DataStore.lng);
        else
            uploadData.put("mapLng", DataStore.lng);
        uploadData.put("accuracy", DataStore.accuracy);

        uploadData.put("homesteadSprayed", Boolean.toString(DataStore.homesteadSprayed));
        uploadData.put("foreman", DataStore.foremanID);
        uploadData.put("sprayerID", DataStore.sprayer1ID);
        if (DataStore.homesteadSprayed) {
            uploadData.put("chemicalUsed1", DataStore.chemicalUsed1);
            uploadData.put("sprayedRooms1", Integer.toString(DataStore.sprayedRooms1));
            uploadData.put("sprayedShelters1", Integer.toString(DataStore.sprayedShelters1));
            uploadData.put("canRefill1", Boolean.toString(DataStore.canRefill1));
            if (DataStore.chemicalUsed2 != null) {
                uploadData.put("chemicalUsed2", DataStore.chemicalUsed2);
                uploadData.put("sprayer2ID", DataStore.sprayer2ID);
                uploadData.put("sprayedRooms2", Integer.toString(DataStore.sprayedRooms2));
                uploadData.put("sprayedShelters2", Integer.toString(DataStore.sprayedShelters2));
                uploadData.put("canRefill2", Boolean.toString(DataStore.canRefill2));
            }
        } else if (DataStore.sprayer2ID != null) {
            uploadData.put("sprayer2ID", DataStore.sprayer2ID);
        }
        uploadData.put("unsprayedRooms", Integer.toString(DataStore.roomsUnsprayed));
        uploadData.put("unsprayedShelters", Integer.toString(DataStore.sheltersUnsprayed));
        uploadData.put("unsprayedReason", DataStore.reasonUnsprayed);

        return uploadData;
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

    private List<String> getUploadCredentials() {
        String username = null;
        String password = null;
        String spreadsheetTitle = null;
        String worksheetTitle = null;
        try {
            Scanner s;
            s = new Scanner(getAssets().open("authentication.txt"));

            while (s.hasNextLine()) {
                String[] nextLine = s.nextLine().split("=");
                if (nextLine[0].equals(USERNAME_LABEL))
                    username = nextLine[1];
                else if (nextLine[0].equals(PASSWORD_LABEL))
                    password = nextLine[1];
                else if (nextLine[0].equals(SPREADSHEET_TITLE_LABEL))
                    spreadsheetTitle = nextLine[1];
                else if (nextLine[0].equals(WORKSHEET_TITLE_LABEL))
                    worksheetTitle = nextLine[1];
                else
                    throw new IllegalArgumentException("Invalid authentication file");
            }

            if (username == null || password == null || spreadsheetTitle == null
                    || worksheetTitle == null) {
                Toast.makeText(getApplicationContext(),
                        "Authentication Error: Could not upload data", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error: Could not upload data correctly",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "Error: Could not upload data correctly",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }

        List<String> credentials = new ArrayList<String>();
        credentials.add(username);
        credentials.add(password);
        credentials.add(spreadsheetTitle);
        credentials.add(worksheetTitle);
        return credentials;
    }

    @SuppressLint("SimpleDateFormat")
    private static String formatDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d = new Date(System.currentTimeMillis());
        
        Constants.LAST_UPLOAD_MILLIS = System.currentTimeMillis();
        
        String[] formattedDateArray = df.format(d).split(" ");

        String[] splitDate = formattedDateArray[0].split("/");
        int month = Integer.parseInt(splitDate[0]);
        int day = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);

        return month + "/" + day + "/" + year + " " + formattedDateArray[1];
    }

}
