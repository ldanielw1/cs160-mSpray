package edu.berkeley.cs160.mSpray;

import edu.berkeley.cs160.Base.BaseMainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmFendona extends BaseMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_fendona);

        setTitle("Is this correct?");

        Bundle extras = this.getIntent().getExtras();
        final int numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        final int formNumber = extras.getInt(Constants.FORM_NUMBER);
        final int roomsSprayed = extras.getInt(Constants.ROOMS_SPRAYED);
        final int sheltersSprayed = extras.getInt(Constants.SHELTERS_SPRAYED);
        final boolean refilled = extras.getBoolean(Constants.CAN_REFILLED);

        TextView title = (TextView) findViewById(R.id.confirm_fendona_textview_title);
        title.setTypeface(Constants.TYPEFACE);

        /* Populate paperwork table */
        TextView foremanValue = (TextView) findViewById(R.id.confirm_fendona_foreman_value);
        TextView sprayerValue = (TextView) findViewById(R.id.confirm_fendona_sprayer_value);
        TextView roomsSprayedValue = (TextView) findViewById(R.id.confirm_fendona_rooms_sprayed_value);
        TextView sheltersSprayedValue = (TextView) findViewById(R.id.confirm_fendona_shelters_sprayed_value);
        TextView canRefilledValue = (TextView) findViewById(R.id.confirm_fendona_can_refilled_value);

        foremanValue.setText(DataStore.foremanID);
        roomsSprayedValue.setText(Integer.toString(roomsSprayed));
        sheltersSprayedValue.setText(Integer.toString(sheltersSprayed));
        boolean refillFlag = false;

        /* Sprayer ID and Can Refilled */
        if (formNumber == 1) {
            sprayerValue.setText(DataStore.sprayer1ID);
            refillFlag = DataStore.canRefill1;
        } else if (formNumber == 2) {
            sprayerValue.setText(DataStore.sprayer2ID);
            refillFlag = DataStore.canRefill2;
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

        Button backButton = (Button) findViewById(R.id.confirm_fendona_button_backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        backButton.setTypeface(Constants.TYPEFACE);

        Button confirmButton = (Button) findViewById(R.id.confirm_fendona_button_confirmButton);
        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formNumber == 1) {
                    DataStore.homesteadSprayed = true;
                    DataStore.chemicalUsed1 = Constants.FENDONA;
                    DataStore.sprayedRooms1 = roomsSprayed;
                    DataStore.sprayedShelters1 = sheltersSprayed;
                    DataStore.canRefill1 = refilled;
                } else if (formNumber == 2) {
                    DataStore.chemicalUsed2 = Constants.FENDONA;
                    DataStore.sprayedRooms2 = roomsSprayed;
                    DataStore.sprayedShelters2 = sheltersSprayed;
                    DataStore.canRefill2 = refilled;
                }
                if (numSprayers == formNumber) {
                    Intent intent = new Intent(getApplicationContext(), UnsprayedActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ScanSprayer.class);
                    intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                    intent.putExtra(Constants.FORM_NUMBER, formNumber + 1);
                    intent.putExtra(Constants.SPRAY_TYPE, Constants.FENDONA);
                    startActivity(intent);
                }
            }
        });
        confirmButton.setTypeface(Constants.TYPEFACE);
    }

}
