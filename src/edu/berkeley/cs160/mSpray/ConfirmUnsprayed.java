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
import android.os.AsyncTask;
import android.os.Bundle;
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

        Bundle extras = this.getIntent().getExtras();
        DataStore.roomsUnsprayed = extras.getInt(Constants.ROOMS_UNSPRAYED);
        DataStore.sheltersUnsprayed = extras.getInt(Constants.SHELTERS_UNSPRAYED);

        TextView results = (TextView) findViewById(R.id.confirm_unsprayed_textview_contents);
        if (DataStore.sprayer2ID == null)
            results.setText(String.format("Foreman: %s\n" + "Sprayers: %s\n"
                    + "Rooms Unsprayed: %d\n" + "Shelters Unsprayed: %d\n", DataStore.foremanID,
                    DataStore.sprayer1ID, DataStore.roomsUnsprayed, DataStore.sheltersUnsprayed));
        else
            results.setText(String.format("Foreman: %s\n" + "Sprayers: %s\n" + "          %s\n"
                    + "Rooms Unsprayed: %d\n" + "Shelters Unsprayed: %d\n", DataStore.foremanID,
                    DataStore.sprayer1ID, DataStore.sprayer2ID, DataStore.roomsUnsprayed, DataStore.sheltersUnsprayed));

        Button backButton = (Button) findViewById(R.id.confirm_unsprayed_button_backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button confirmButton = (Button) findViewById(R.id.confirm_unsprayed_button_confirmButton);
        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	           GoogleSpreadsheetUploader gssu = new GoogleSpreadersheetUploader();
            }
        });
    }
}
