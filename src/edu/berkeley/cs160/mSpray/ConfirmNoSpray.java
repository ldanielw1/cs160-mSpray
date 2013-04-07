package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmNoSpray extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_no_spray);
        Bundle extras = this.getIntent().getExtras();
        int roomsUnsprayed = extras.getInt("roomsUnsprayed");
        int sheltersUnsprayed = extras.getInt("sheltersUnsprayed");
        TextView results = (TextView) findViewById(R.id.confirm_no_spray_textview_contents);
        // NEED TO ADD SPRAYER NAME
        results.setText(String.format("Sprayers: %s\n" + "Rooms Unsprayed: %d\n"
                + "Shelters Unsprayed: %d\n" + "Can %srefilled", "TO-DO NAME", roomsUnsprayed,
                sheltersUnsprayed));

        Button backButton = (Button) findViewById(R.id.confirm_no_spray_button_backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button confirmButton = (Button) findViewById(R.id.no_spray_button_confirmButton);
        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FinishedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
