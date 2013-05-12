package edu.berkeley.cs160.mSpray;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.berkeley.cs160.Base.BaseMainActivity;

public class ConfirmUnsprayed extends BaseMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_unsprayed);

        setTitle("Is this correct?");

        Bundle extras = this.getIntent().getExtras();
        DataStore.roomsUnsprayed = extras.getInt(Constants.ROOMS_UNSPRAYED);
        DataStore.sheltersUnsprayed = extras.getInt(Constants.SHELTERS_UNSPRAYED);
        DataStore.reasonUnsprayed = extras.getString(Constants.REASON_UNSPRAYED);

        /* Populate paperwork table */
        TextView roomsUnsprayedValue = (TextView) findViewById(R.id.confirm_unsprayed_rooms_unsprayed_value);
        TextView sheltersUnsprayedValue = (TextView) findViewById(R.id.confirm_unsprayed_shelters_unsprayed_value);
        TextView unsprayedReasonValue = (TextView) findViewById(R.id.confirm_unsprayed_reason_value);

        roomsUnsprayedValue.setText(Integer.toString(DataStore.roomsUnsprayed));
        sheltersUnsprayedValue.setText(Integer.toString(DataStore.sheltersUnsprayed));
        unsprayedReasonValue.setText(DataStore.reasonUnsprayed);

        Button backButton = (Button) findViewById(R.id.confirm_unsprayed_button_backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        backButton.setTypeface(Constants.TYPEFACE);

        Button confirmButton = (Button) findViewById(R.id.confirm_unsprayed_button_confirmButton);
        confirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FinishedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        confirmButton.setTypeface(Constants.TYPEFACE);
    }

}
