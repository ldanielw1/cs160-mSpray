package edu.berkeley.cs160.mSpray;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmPyrethroid extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_pyrethroid);

        Bundle extras = this.getIntent().getExtras();
        final int numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        final int formNumber = extras.getInt(Constants.FORM_NUMBER);
        final int roomsSprayed = extras.getInt(Constants.ROOMS_SPRAYED);
        final int sheltersSprayed = extras.getInt(Constants.SHELTERS_SPRAYED);
        final boolean refilled = extras.getBoolean(Constants.CAN_REFILLED);
        String c = refilled ? "" : "not ";

        TextView results = (TextView) findViewById(R.id.confirm_pyrethroid_textview_contents);
        if (formNumber == 1)
            results.setText(String.format("Foreman: %s\n" + "Sprayers: %s\n"
                    + "Rooms Sprayed: %d\n" + "Shelters Sprayed: %d\n" + "Can %srefilled",
                    DataStore.foremanID, DataStore.sprayer1ID, roomsSprayed, sheltersSprayed, c));
        else if (formNumber == 2)
            results.setText(String.format("Foreman: %s\n" + "Sprayers: %s\n"
                    + "Rooms Sprayed: %d\n" + "Shelters Sprayed: %d\n" + "Can %srefilled",
                    DataStore.foremanID, DataStore.sprayer2ID, roomsSprayed, sheltersSprayed, c));

        Button backButton = (Button) findViewById(R.id.confirm_pyrethroid_button_backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button confirmButton = (Button) findViewById(R.id.confirm_pyrethroid_button_confirmButton);
        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formNumber == 1) {
                    DataStore.homesteadSprayed = true;
                    DataStore.pyrethroidUsed1 = true;
                    DataStore.pyrethroidSprayedRooms1 = roomsSprayed;
                    DataStore.pyrethroidSprayedShelters1 = sheltersSprayed;
                    DataStore.pyrethroidRefill1 = refilled;
                } else if (formNumber == 2) {
                    DataStore.pyrethroidUsed2 = true;
                    DataStore.pyrethroidSprayedRooms2 = roomsSprayed;
                    DataStore.pyrethroidSprayedShelters2 = sheltersSprayed;
                    DataStore.pyrethroidRefill2 = refilled;
                }
                if (numSprayers == formNumber) {
                    SprayerIDScan.resetNames();
                    Intent intent = new Intent(getApplicationContext(), UnsprayedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    // Intent intent = new Intent(getApplicationContext(),
                    // PyrethroidActivity.class);
                    Intent intent = new Intent(getApplicationContext(), ScanSprayer1.class);
                    intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                    intent.putExtra(Constants.FORM_NUMBER, formNumber + 1);
                    intent.putExtra(Constants.SPRAY_TYPE, Constants.PYRETHROID);
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
