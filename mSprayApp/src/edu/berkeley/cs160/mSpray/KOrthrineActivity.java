package edu.berkeley.cs160.mSpray;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import edu.berkeley.cs160.Base.BaseMainActivity;

public class KOrthrineActivity extends BaseMainActivity {

    TextView SprayerName;
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

        setTitle(DataStore.screenTitlePrefix + "Enter Spray Data");

        Bundle extras = this.getIntent().getExtras();
        numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        formNumber = extras.getInt(Constants.FORM_NUMBER);

        roomsSprayedValue = (EditText) findViewById(R.id.sprayer_form_edittext_roomsSprayedValue);
        roomsSprayedValue.setTypeface(Constants.TYPEFACE);
        sheltersSprayedValue = (EditText) findViewById(R.id.sprayer_form_edittext_sheltersSprayedValue);
        sheltersSprayedValue.setTypeface(Constants.TYPEFACE);
        canRefilled = (RadioGroup) findViewById(R.id.sprayer_form_radiogroup);
        canRefilledYes = (RadioButton) findViewById(R.id.sprayer_form_radiobutton_canRefilledYes);
        canRefilledYes.setTypeface(Constants.TYPEFACE);
        canRefilledNo = (RadioButton) findViewById(R.id.sprayer_form_radiobutton_canRefilledNo);
        canRefilledNo.setTypeface(Constants.TYPEFACE);
        backButton = (Button) findViewById(R.id.sprayer_form_button_backButton);
        backButton.setTypeface(Constants.TYPEFACE);
        confirmButton = (Button) findViewById(R.id.sprayer_form_button_confirmButton);
        confirmButton.setTypeface(Constants.TYPEFACE);

        /* External font for labels */
        TextView roomsSprayed = (TextView) findViewById(R.id.sprayer_form_textview_roomsSprayed);
        roomsSprayed.setTypeface(Constants.TYPEFACE);
        TextView sheltersSprayed = (TextView) findViewById(R.id.sprayer_form_textview_sheltersSprayed);
        sheltersSprayed.setTypeface(Constants.TYPEFACE);
        TextView canRefilledTextView = (TextView) findViewById(R.id.sprayer_form_textview_canRefilled);
        canRefilledTextView.setTypeface(Constants.TYPEFACE);

        /* Sprayed By Header */
        TextView header = (TextView) findViewById(R.id.sprayer_form_textview_header);
        header.setText("K-Orthrine " + header.getText());
        header.setTypeface(Constants.TYPEFACE);

        /* Name of sprayer */
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
                if (DataStore.scannedFirstSprayer) {
                    DataStore.scannedFirstSprayer = false;
                }
                onBackPressed();
            };
        });

    }

    public void getData() {
        Intent intent = new Intent(this, ConfirmKOrthrine.class);

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

                // Store refilled flag in the data store
                if (formNumber == 1) {
                    DataStore.canRefill1 = refilled;
                } else if (formNumber == 1) {
                    DataStore.canRefill2 = refilled;
                }

                startActivity(intent);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(),
                        "Please input integer values in every text field", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * Overrode the button back so if you go back after scanning the first
     * sprayer the app will know that you actually haven't finished scanning the
     * first sprayer.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (DataStore.scannedFirstSprayer) {
                DataStore.scannedFirstSprayer = false;
            }

            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
