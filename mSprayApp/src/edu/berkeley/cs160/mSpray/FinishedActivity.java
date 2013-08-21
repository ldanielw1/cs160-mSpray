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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gdata.util.ServiceException;

import edu.berkeley.cs160.Base.BaseMainActivity;
import edu.berkeley.cs160.spreadsheetUploader.GoogleSpreadsheet;
import edu.berkeley.cs160.spreadsheetUploader.UploadConstants;

public class FinishedActivity extends BaseMainActivity {

    private static final String USERNAME_LABEL = "USERNAME";
    private static final String PASSWORD_LABEL = "PASSWORD";
    private static final String SPREADSHEET_TITLE_LABEL = "SPREADSHEET_TITLE";
    private static final String WORKSHEET_TITLE_LABEL = "WORKSHEET_TITLE";

    TextView chemicalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finished);

        setTitle(DataStore.screenTitlePrefix + "Upload Spray Data");

        /* Populate paperwork table */
        TextView finishedTitle = (TextView) findViewById(R.id.finished_textview_title);
        TextView foremanValue = (TextView) findViewById(R.id.finished_foreman_value);
        TextView sprayerValue = (TextView) findViewById(R.id.finished_sprayer_value);
        chemicalValue = (TextView) findViewById(R.id.finished_chemical_value);
        TextView roomsSprayedValue = (TextView) findViewById(R.id.finished_rooms_sprayed_value);
        TextView sheltersSprayedValue = (TextView) findViewById(R.id.finished_shelters_sprayed_value);
        TextView canRefilledValue = (TextView) findViewById(R.id.finished_can_refilled_value);
        TableRow sprayer2Row = (TableRow) findViewById(R.id.finished_sprayer2_row);
        TableRow sprayer2RoomsRow = (TableRow) findViewById(R.id.finished_rooms_sprayed2_row);
        TableRow sprayer2SheltersRow = (TableRow) findViewById(R.id.finished_shelters_sprayed2_row);
        TextView sprayerValue2 = (TextView) findViewById(R.id.finished_sprayer2_value);
        TextView roomsSprayedValue2 = (TextView) findViewById(R.id.finished_rooms_sprayed2_value);
        TextView canRefilledValue2 = (TextView) findViewById(R.id.finished_can_refilled2_value);
        TableRow canRefilledRow2 = (TableRow) findViewById(R.id.finished_can_refilled2_row);
        TextView sheltersSprayedValue2 = (TextView) findViewById(R.id.finished_shelters_sprayed2_value);
        TextView roomsUnsprayed = (TextView) findViewById(R.id.finished_rooms_unsprayed_label);
        TextView roomsUnsprayedValue = (TextView) findViewById(R.id.finished_rooms_unsprayed_value);
        TableRow unsprayedReasonRow = (TableRow) findViewById(R.id.finished_unsprayed_reason_row);
        TextView sheltersUnsprayed = (TextView) findViewById(R.id.finished_shelters_unsprayed_label);
        TextView sheltersUnsprayedValue = (TextView) findViewById(R.id.finished_shelters_unsprayed_value);
        TextView reasonUnsprayedValue = (TextView) findViewById(R.id.finished_unsprayed_reason_value);
        TextView latitude = (TextView) findViewById(R.id.finished_latitude_value);
        TextView longitude = (TextView) findViewById(R.id.finished_longitude_value);

        boolean refillFlag = false;
        boolean refillFlag2 = false;
        
        /* Set title if mop-up spray */
        if (!DataStore.screenTitlePrefix.equals("")) {
        	finishedTitle.setText(R.string.mopupRecorded);
        }

        foremanValue.setText(DataStore.foremanID);
        sprayerValue.setText(DataStore.sprayer1ID);

        setProperChemicalValue();

        /* If no rooms unsprayed and no shelters unsprayed, hide that info */
        if (DataStore.roomsUnsprayed == 0 && DataStore.sheltersUnsprayed == 0) {
            roomsUnsprayed.setVisibility(View.GONE);
            roomsUnsprayedValue.setVisibility(View.GONE);
            sheltersUnsprayed.setVisibility(View.GONE);
            sheltersUnsprayedValue.setVisibility(View.GONE);
            canRefilledRow2.setVisibility(View.GONE);
            unsprayedReasonRow.setVisibility(View.GONE);
        }

        /* Sprayer 2 */
        if (DataStore.sprayer2ID != null) {
            sprayerValue2.setText(DataStore.sprayer2ID);
        } else {
            sprayer2Row.setVisibility(View.GONE);
            sprayer2RoomsRow.setVisibility(View.GONE);
            sprayer2SheltersRow.setVisibility(View.GONE);
            canRefilledRow2.setVisibility(View.GONE);
        }

        if (DataStore.sprayType.equals(Constants.DDT)
                || DataStore.sprayType.equals(Constants.KORTHRINE)
                || DataStore.sprayType.equals(Constants.FENDONA)) {
            roomsSprayedValue.setText(Integer.toString(DataStore.sprayedRooms1));
            sheltersSprayedValue.setText(Integer.toString(DataStore.sprayedShelters1));
            refillFlag = DataStore.canRefill1;
            if (DataStore.sprayer2ID != null) {
                roomsSprayedValue2.setText(Integer.toString(DataStore.sprayedRooms2));
                sheltersSprayedValue2.setText(Integer.toString(DataStore.sprayedShelters2));
                refillFlag2 = DataStore.canRefill2;
            }
        } else if (DataStore.sprayType.equals(Constants.NO_SPRAY)) {
            roomsSprayedValue.setText("No spray");
            sheltersSprayedValue.setText("No spray");
            if (DataStore.sprayer2ID != null) {
                roomsSprayedValue2.setText("No spray");
                sheltersSprayedValue2.setText("No spray");
            }
        }

        /* Refill for Sprayer 1 */
        if (refillFlag) {
            canRefilledValue.setText("YES");
        } else {
            canRefilledValue.setText("NO");
        }

        /* Refill for Sprayer 2 */
        if (refillFlag2) {
            canRefilledValue2.setText("YES");
        } else {
            canRefilledValue2.setText("NO");
        }

        /* Unsprayed Data */
        roomsUnsprayedValue.setText(Integer.toString(DataStore.roomsUnsprayed));
        sheltersUnsprayedValue.setText(Integer.toString(DataStore.sheltersUnsprayed));
        reasonUnsprayedValue.setText(DataStore.reasonUnsprayed);

        latitude.setText(DataStore.lat);
        longitude.setText(DataStore.lng);

        /* External font */
        foremanValue.setTypeface(Constants.TYPEFACE);
        sprayerValue.setTypeface(Constants.TYPEFACE);
        roomsSprayedValue.setTypeface(Constants.TYPEFACE);
        sheltersSprayedValue.setTypeface(Constants.TYPEFACE);
        sprayerValue2.setTypeface(Constants.TYPEFACE);
        roomsSprayedValue2.setTypeface(Constants.TYPEFACE);
        sheltersSprayedValue2.setTypeface(Constants.TYPEFACE);
        latitude.setTypeface(Constants.TYPEFACE);
        longitude.setTypeface(Constants.TYPEFACE);

        Button finishedButton = (Button) findViewById(R.id.finished_button_finishedButton);
        finishedButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DataStore.secondTimeThrough = true;
                upload();
                Intent intent = new Intent(getApplicationContext(), StartNewSpray.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        finishedButton.setTypeface(Constants.TYPEFACE);

        Button backButton = (Button) findViewById(R.id.finished_button_backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        backButton.setTypeface(Constants.TYPEFACE);

    }

    public void setProperChemicalValue() {
        if (DataStore.chemicalUsed != null && !DataStore.chemicalUsed.equals("")) {
            chemicalValue.setText(DataStore.chemicalUsed);
        } else {
            chemicalValue.setText("None");
        }
        chemicalValue.setTypeface(Constants.TYPEFACE);
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
    }

    private HashMap<String, String> organizeUploadData() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        HashMap<String, String> uploadData = new HashMap<String, String>();
        uploadData.put(UploadConstants.TIMESTAMP, formatDateTime());
        
        if (tm.getDeviceId() != null)
            uploadData.put(UploadConstants.IMEI, tm.getDeviceId());
        else
            uploadData.put(UploadConstants.IMEI, generateSubstituteSignature());

        uploadData.put(UploadConstants.LAT, DataStore.lat);
        uploadData.put(UploadConstants.LAT_NS, DataStore.latNS);
        if (DataStore.latNS.equals(UploadConstants.SOUTH))
            uploadData.put(UploadConstants.MAP_LAT, "-" + DataStore.lat);
        else
            uploadData.put(UploadConstants.MAP_LAT, DataStore.lat);
        uploadData.put(UploadConstants.LNG, DataStore.lng);
        uploadData.put(UploadConstants.LNG_EW, DataStore.lngEW);
        if (DataStore.lngEW.equals(UploadConstants.WEST))
            uploadData.put(UploadConstants.MAP_LNG, "-" + DataStore.lng);
        else
            uploadData.put(UploadConstants.MAP_LNG, DataStore.lng);
        uploadData.put(UploadConstants.ACCURACY, DataStore.accuracy);

        uploadData.put(UploadConstants.HOMESTEAD_SPRAYED,
                Boolean.toString(DataStore.homesteadSprayed));
        uploadData.put(UploadConstants.FOREMAN, DataStore.foremanID);
        uploadData.put(UploadConstants.SPRAYER_1_ID, DataStore.sprayer1ID);
        if (DataStore.homesteadSprayed) {
            uploadData.put(UploadConstants.CHEMICAL_USED, DataStore.chemicalUsed);
            uploadData.put(UploadConstants.SPRAYED_ROOMS_1,
                    Integer.toString(DataStore.sprayedRooms1));
            uploadData.put(UploadConstants.SPRAYED_SHELTERS_1,
                    Integer.toString(DataStore.sprayedShelters1));
            uploadData.put(UploadConstants.CAN_REFILL_1, Boolean.toString(DataStore.canRefill1));
            if (DataStore.sprayer2ID != null) {
                uploadData.put(UploadConstants.SPRAYER_2_ID, DataStore.sprayer2ID);
                uploadData.put(UploadConstants.SPRAYED_ROOMS_2,
                        Integer.toString(DataStore.sprayedRooms2));
                uploadData.put(UploadConstants.SPRAYED_SHELTERS_2,
                        Integer.toString(DataStore.sprayedShelters2));
                uploadData
                        .put(UploadConstants.CAN_REFILL_2, Boolean.toString(DataStore.canRefill2));
            }
        } else if (DataStore.sprayer2ID != null) {
            uploadData.put(UploadConstants.SPRAYER_2_ID, DataStore.sprayer2ID);
        }
        uploadData.put(UploadConstants.UNSPRAYED_ROOMS, Integer.toString(DataStore.roomsUnsprayed));
        uploadData.put(UploadConstants.UNSPRAYED_SHELTERS,
                Integer.toString(DataStore.sheltersUnsprayed));
        uploadData.put(UploadConstants.UNSPRAYED_REASON, DataStore.reasonUnsprayed);

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

        SessionConstants.LAST_IMPORTANT_ACTION = System.currentTimeMillis();

        String[] formattedDateArray = df.format(d).split(" ");

        String[] splitDate = formattedDateArray[0].split("/");
        int month = Integer.parseInt(splitDate[0]);
        int day = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);

        return month + "/" + day + "/" + year + " " + formattedDateArray[1];
    }

}
