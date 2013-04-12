package edu.berkeley.cs160.mSpray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gdata.util.ServiceException;

import edu.berkeley.cs160.spreadsheetUploader.GoogleSpreadsheetUploader;

public class ConfirmDDT extends Activity {

    private static final String USERNAME_LABEL = "USERNAME";
    private static final String PASSWORD_LABEL = "PASSWORD";
    private static final String SPREADSHEET_TITLE_LABEL = "SPREADSHEET_TITLE";
    private static final String WORKSHEET_TITLE_LABEL = "WORKSHEET_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_ddt);

        Bundle extras = this.getIntent().getExtras();
        final int numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        final int formNumber = extras.getInt(Constants.FORM_NUMBER);
        final int roomsSprayed = extras.getInt(Constants.ROOMS_SPRAYED);
        final int roomsUnsprayed = extras.getInt(Constants.ROOMS_UNSPRAYED);
        final int sheltersSprayed = extras.getInt(Constants.SHELTERS_SPRAYED);
        final int sheltersUnsprayed = extras.getInt(Constants.SHELTERS_UNSPRAYED);
        final boolean refilled = extras.getBoolean(Constants.CAN_REFILLED);

        String c = refilled ? "" : "not ";
        TextView results = (TextView) findViewById(R.id.confirm_ddt_textview_contents);
        // NEED TO ADD SPRAYER NAME
        results.setText(String.format("Foreman: %s\n" + "Sprayers: %s\n" + "Rooms Sprayed: %d\n"
                + "Shelters Sprayed: %d\n" + "Rooms Unsprayed: %d\n" + "Shelters Unsprayed: %d\n"
                + "Can %srefilled", SprayerIDScan.FOREMAN_NAME, SprayerIDScan.SPRAYER_NAMES,
                roomsSprayed, sheltersSprayed, roomsUnsprayed, sheltersUnsprayed, c));

        Button backButton = (Button) findViewById(R.id.confirm_ddt_button_backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button confirmButton = (Button) findViewById(R.id.confirm_ddt_button_confirmButton);
        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numSprayers == formNumber) {
                    SprayerIDScan.resetNames();

                    try {
                        String username = null;
                        String password = null;
                        String spreadsheetTitle = null;
                        String worksheetTitle = null;
                        Scanner s = new Scanner(getAssets().open("authentication.txt"));
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

                        GoogleSpreadsheetUploader uploader = new GoogleSpreadsheetUploader(
                                username, password, spreadsheetTitle, worksheetTitle);

                        // needed for IMEI
                        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                        HashMap<String, String> uploadData = new HashMap<String, String>();
                        uploadData.put("timeStamp", formatDateTime());
                        uploadData.put("imei", tm.getDeviceId());
                        uploadData.put("lat", DataStore.getLat());
                        uploadData.put("latNS", DataStore.getLatNS());
                        uploadData.put("lng", DataStore.getLng());
                        uploadData.put("lngEW", DataStore.getLngEW());
                        uploadData.put("accuracy", DataStore.getAccuracy());
                        uploadData.put("homesteadSprayed", Boolean.toString(true));
                        uploadData.put("sprayerID", "TESTGOOGLESPREADSHEETUPLOADER");
                        uploadData.put("DDTUsed1", Boolean.toString(true));
                        uploadData.put("DDTSprayedRooms1", Integer.toString(roomsSprayed));
                        uploadData.put("DDTSprayedShelters1", Integer.toString(sheltersSprayed));
                        uploadData.put("DDTRefill1", Boolean.toString(refilled));
                        uploadData.put("pyrethroidUsed1", Boolean.toString(false));
                        uploadData.put("sprayer2ID", "TESTGOOGLESPREADSHEETUPLOADER");
                        uploadData.put("DDTUsed2", Boolean.toString(false));
                        uploadData.put("pyrethroidUsed2", Boolean.toString(false));
                        uploadData.put("unsprayedRooms", Integer.toString(roomsUnsprayed));
                        uploadData.put("unsprayedShelters", Integer.toString(sheltersUnsprayed));
                        uploadData.put("foreman", "TESTGOOGLESPREADSHEETUPLOADER");

                        uploader.addRow(uploadData);

                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),
                                "Error: Could not upload data correctly", Toast.LENGTH_SHORT)
                                .show();
                        e.printStackTrace();
                    } catch (ServiceException e) {
                        Toast.makeText(getApplicationContext(),
                                "Service Error: Could not upload data correctly",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getApplicationContext(), FinishedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), DDTActivity.class);
                    intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                    intent.putExtra(Constants.FORM_NUMBER, formNumber + 1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
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
