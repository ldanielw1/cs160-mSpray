package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PyrethroidActivity extends Activity {

    EditText roomsSprayedValue;
    EditText sheltersSprayedValue;
    RadioGroup canRefilled;
    RadioButton canRefilledYes;
    RadioButton canRefilledNo;
    Button backButton;
    Button confirmButton;

    int numSprayers;
    int formNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprayer_form);

        Bundle extras = this.getIntent().getExtras();
        numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        formNumber = extras.getInt(Constants.FORM_NUMBER);

        roomsSprayedValue = (EditText) findViewById(R.id.sprayer_form_edittext_roomsSprayedValue);
        sheltersSprayedValue = (EditText) findViewById(R.id.sprayer_form_edittext_sheltersSprayedValue);
        canRefilled = (RadioGroup) findViewById(R.id.sprayer_form_radiogroup);
        canRefilledYes = (RadioButton) findViewById(R.id.sprayer_form_radiobutton_canRefilledYes);
        canRefilledNo = (RadioButton) findViewById(R.id.sprayer_form_radiobutton_canRefilledNo);
        backButton = (Button) findViewById(R.id.sprayer_form_button_backButton);
        confirmButton = (Button) findViewById(R.id.sprayer_form_button_confirmButton);

        TextView userName = (TextView) findViewById(R.id.sprayer_form_textview_sprayerName);
        if (formNumber == 1)
            userName.setText(DataStore.sprayer1ID);
        else if (formNumber == 2)
            userName.setText(DataStore.sprayer2ID);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            };
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.PYRETHROID);
                startActivity(intent);
            };
        });

    }

    public void getData() {
        Intent intent = new Intent(this, ConfirmPyrethroid.class);

        if (roomsSprayedValue.getText().toString().equals("")
                || sheltersSprayedValue.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please input a value for every text field",
                    Toast.LENGTH_SHORT).show();
        } else {
            try {
                int roomsSprayed = Integer.parseInt(roomsSprayedValue.getText().toString());
                int sheltersSprayed = Integer.parseInt(sheltersSprayedValue.getText().toString());
                boolean refilled = canRefilled.getCheckedRadioButtonId() == R.id.sprayer_form_radiobutton_canRefilledYes;
                intent.putExtra(Constants.ROOMS_SPRAYED, roomsSprayed);
                intent.putExtra(Constants.SHELTERS_SPRAYED, sheltersSprayed);
                intent.putExtra(Constants.CAN_REFILLED, refilled);
                intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                intent.putExtra(Constants.FORM_NUMBER, formNumber);

                startActivity(intent);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(),
                        "Please input integer values in every text field", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
