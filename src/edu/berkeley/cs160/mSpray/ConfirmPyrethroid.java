package edu.berkeley.cs160.mSpray;

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
        int roomsSprayed = extras.getInt("roomsSprayed");
        int roomsUnsprayed = extras.getInt("roomsUnsprayed");
        int sheltersSprayed = extras.getInt("sheltersSprayed");
        int sheltersUnsprayed = extras.getInt("sheltersUnsprayed");
        boolean refilled = extras.getBoolean("canRefilled");
        String c = refilled ? "" : "not ";
        TextView results = (TextView) findViewById(R.id.confirm_pyrethroid_textview_contents);
        // NEED TO ADD SPRAYER NAME
        results.setText(String.format("Sprayers: %s\n" + "Rooms Sprayed: %d\n"
                + "Shelters Sprayed: %d\n" + "Rooms Unsprayed: %d\n" + "Shelters Unsprayed: %d\n"
                + "Can %srefilled", "TO-DO NAME", roomsSprayed, sheltersSprayed, roomsUnsprayed,
                sheltersUnsprayed, c));

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
                Intent intent = new Intent(getApplicationContext(), OtherChemicalUsedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.FIRST_CHEMICAL_USED, Constants.PYRETHROID);
                startActivity(intent);
            }
        });
    }
}
