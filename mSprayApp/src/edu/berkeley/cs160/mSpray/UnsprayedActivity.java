package edu.berkeley.cs160.mSpray;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import edu.berkeley.cs160.Base.BaseMainActivity;

public class UnsprayedActivity extends BaseMainActivity {

    TextView noSprayTitle;
    EditText roomsUnsprayedValue;
    EditText sheltersUnsprayedValue;
    Button backButton;
    Button confirmButton;
    RadioGroup reasonUnsprayed;
    RadioButton open;
    RadioButton locked;
    RadioButton refused;

    int numSprayers;
    int formNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unsprayed);

        setTitle(DataStore.screenTitlePrefix + "Enter Spray Data");

        roomsUnsprayedValue = (EditText) findViewById(R.id.unsprayed_edittext_roomsUnsprayedValue);
        roomsUnsprayedValue.setTypeface(Constants.TYPEFACE);
        sheltersUnsprayedValue = (EditText) findViewById(R.id.unsprayed_edittext_sheltersUnsprayedValue);
        sheltersUnsprayedValue.setTypeface(Constants.TYPEFACE);
        backButton = (Button) findViewById(R.id.unsprayed_button_backButton);
        backButton.setTypeface(Constants.TYPEFACE);
        confirmButton = (Button) findViewById(R.id.unsprayed_button_confirmButton);
        confirmButton.setTypeface(Constants.TYPEFACE);

        reasonUnsprayed = (RadioGroup) findViewById(R.id.unsprayed_radiogroup);

        open = (RadioButton) findViewById(R.id.unsprayed_radiobutton_open);
        open.setTypeface(Constants.TYPEFACE);
        locked = (RadioButton) findViewById(R.id.unsprayed_radiobutton_locked);
        locked.setTypeface(Constants.TYPEFACE);
        refused = (RadioButton) findViewById(R.id.unsprayed_radiobutton_refused);
        refused.setTypeface(Constants.TYPEFACE);

        noSprayTitle = (TextView) findViewById(R.id.unsprayed_textview_title);
        noSprayTitle.setTypeface(Constants.TYPEFACE);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            };
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            };
        });

    }

    public void getData() {
        Intent intent = new Intent(this, ConfirmUnsprayed.class);

        if (roomsUnsprayedValue.getText().toString().equals("")
                || sheltersUnsprayedValue.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please input a value for every text field",
                    Toast.LENGTH_SHORT).show();
        } else {
            try {
                int roomsUnsprayed = Integer.parseInt(roomsUnsprayedValue.getText().toString());
                int sheltersUnsprayed = Integer.parseInt(sheltersUnsprayedValue.getText()
                        .toString());
                String reason = "unknown";
                if (reasonUnsprayed.getCheckedRadioButtonId() == R.id.unsprayed_radiobutton_open) {
                    reason = "open";
                } else if (reasonUnsprayed.getCheckedRadioButtonId() == R.id.unsprayed_radiobutton_locked) {
                    reason = "locked";
                } else if (reasonUnsprayed.getCheckedRadioButtonId() == R.id.unsprayed_radiobutton_refused) {
                    reason = "owner refused";
                }
                intent.putExtra(Constants.ROOMS_UNSPRAYED, roomsUnsprayed);
                intent.putExtra(Constants.SHELTERS_UNSPRAYED, sheltersUnsprayed);
                intent.putExtra(Constants.REASON_UNSPRAYED, reason);
                startActivity(intent);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(),
                        "Please input integer values in every text field", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
