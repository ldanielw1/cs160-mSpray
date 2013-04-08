package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmNoSpray extends Activity {
    Bundle extras;
    TextView results;
    Button backButton;
    Button confirmButton;

    int roomsUnsprayed;
    int sheltersUnsprayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_no_spray);

        results = (TextView) findViewById(R.id.confirm_no_spray_textview_contents);
        backButton = (Button) findViewById(R.id.confirm_no_spray_button_backButton);
        confirmButton = (Button) findViewById(R.id.confirm_no_spray_button_confirmButton);

        extras = this.getIntent().getExtras();

        roomsUnsprayed = extras.getInt(Constants.ROOMS_UNSPRAYED);
        sheltersUnsprayed = extras.getInt(Constants.SHELTERS_UNSPRAYED);

        // NEED TO ADD SPRAYER NAME
        results.setText(String.format("Foreman Name: %s\n" + "Sprayers: %s\n"
                + "Rooms Unsprayed: %d\n" + "Shelters Unsprayed: %d\n", SprayerIDScan.FOREMAN_NAME,
                SprayerIDScan.SPRAYER_NAMES, roomsUnsprayed, sheltersUnsprayed));

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
