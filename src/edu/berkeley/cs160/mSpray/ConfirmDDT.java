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

public class ConfirmDDT extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_ddt);
        
        setTitle("Is this correct?");

        Bundle extras = this.getIntent().getExtras();
        final int numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        final int formNumber = extras.getInt(Constants.FORM_NUMBER);
        final int roomsSprayed = extras.getInt(Constants.ROOMS_SPRAYED);
        final int sheltersSprayed = extras.getInt(Constants.SHELTERS_SPRAYED);
        final boolean refilled = extras.getBoolean(Constants.CAN_REFILLED);
        String c = refilled ? "" : "not ";
        
        TextView title = (TextView) findViewById(R.id.confirm_ddt_textview_title);
        title.setTypeface(Constants.TYPEFACE);
        
        /* Populate paperwork table */
        TextView foremanValue = (TextView) findViewById(R.id.confirm_ddt_foreman_value);
        TextView sprayerValue = (TextView) findViewById(R.id.confirm_ddt_sprayer_value);
        TextView roomsSprayedValue = (TextView) findViewById(R.id.confirm_ddt_rooms_sprayed_value);
        TextView sheltersSprayedValue = (TextView) findViewById(R.id.confirm_ddt_shelters_sprayed_value);
        TextView canRefilledValue = (TextView) findViewById(R.id.confirm_ddt_can_refilled_value);
        
        foremanValue.setText(DataStore.foremanID);
        roomsSprayedValue.setText(Integer.toString(roomsSprayed));
        sheltersSprayedValue.setText(Integer.toString(sheltersSprayed));
        boolean refillFlag = false;
        
        /* Sprayer ID and Can Refilled*/
        if (formNumber == 1) {
        	sprayerValue.setText(DataStore.sprayer1ID);
        	refillFlag = DataStore.ddtRefill1;
        } else if (formNumber == 2) {
        	sprayerValue.setText(DataStore.sprayer2ID);
        	refillFlag = DataStore.ddtRefill2;
        }
        
        /* Refill can? */
        if (refillFlag) {
        	canRefilledValue.setText("YES");
    	} else {
    		canRefilledValue.setText("NO");
    	}
        
        /* External font */
        foremanValue.setTypeface(Constants.TYPEFACE);
        sprayerValue.setTypeface(Constants.TYPEFACE);
        roomsSprayedValue.setTypeface(Constants.TYPEFACE);
        sheltersSprayedValue.setTypeface(Constants.TYPEFACE);
        canRefilledValue.setTypeface(Constants.TYPEFACE);
        
        Button backButton = (Button) findViewById(R.id.confirm_ddt_button_backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        backButton.setTypeface(Constants.TYPEFACE);
        
        Button confirmButton = (Button) findViewById(R.id.confirm_ddt_button_confirmButton);
        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formNumber == 1) {
                    DataStore.homesteadSprayed = true;
                    DataStore.ddtUsed1 = true;
                    DataStore.ddtSprayedRooms1 = roomsSprayed;
                    DataStore.ddtSprayedShelters1 = sheltersSprayed;
                    DataStore.ddtRefill1 = refilled;
                } else if (formNumber == 2) {
                    DataStore.ddtUsed2 = true;
                    DataStore.ddtSprayedRooms2 = roomsSprayed;
                    DataStore.ddtSprayedShelters2 = sheltersSprayed;
                    DataStore.ddtRefill2 = refilled;
                }
                if (numSprayers == formNumber) {
                    Intent intent = new Intent(getApplicationContext(), UnsprayedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    // Intent intent = new Intent(getApplicationContext(),
                    // DDTActivity.class);
                    Intent intent = new Intent(getApplicationContext(), ScanSprayer.class);
                    intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                    intent.putExtra(Constants.FORM_NUMBER, formNumber + 1);
                    intent.putExtra(Constants.SPRAY_TYPE, Constants.DDT);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        confirmButton.setTypeface(Constants.TYPEFACE);
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
