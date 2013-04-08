package edu.berkeley.cs160.mSpray;

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
        Bundle extras = this.getIntent().getExtras();
        int roomsSprayed = extras.getInt(Constants.ROOMS_SPRAYED);
        int roomsUnsprayed = extras.getInt(Constants.ROOMS_UNSPRAYED);
        int sheltersSprayed = extras.getInt(Constants.SHELTERS_SPRAYED);
        int sheltersUnsprayed = extras.getInt(Constants.SHELTERS_UNSPRAYED);
        boolean refilled = extras.getBoolean(Constants.CAN_REFILLED);
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
                SprayerIDScan.resetNames();
                Intent intent = new Intent(getApplicationContext(), FinishedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
