package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DDTActivity extends Activity {

    EditText roomsSprayedValue;
    EditText sheltersSprayedValue;
    EditText roomsUnsprayedValue;
    EditText sheltersUnsprayedValue;
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
        setContentView(R.layout.ddt);

        Bundle extras = this.getIntent().getExtras();
        numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        formNumber = extras.getInt(Constants.FORM_NUMBER);
        canRefilled = (RadioGroup) findViewById(R.id.ddt_radiogroup);
        roomsSprayedValue = (EditText) findViewById(R.id.ddt_edittext_roomsSprayedValue);
        sheltersSprayedValue = (EditText) findViewById(R.id.ddt_edittext_sheltersSprayedValue);
        roomsUnsprayedValue = (EditText) findViewById(R.id.ddt_edittext_roomsUnsprayedValue);
        sheltersUnsprayedValue = (EditText) findViewById(R.id.ddt_edittext_sheltersUnsprayedValue);
        canRefilled = (RadioGroup) findViewById(R.id.ddt_radiogroup);
        canRefilledYes = (RadioButton) findViewById(R.id.ddt_radiobutton_canRefilledYes);
        canRefilledNo = (RadioButton) findViewById(R.id.ddt_radiobutton_canRefilledNo);
        backButton = (Button) findViewById(R.id.ddt_button_backButton);
        confirmButton = (Button) findViewById(R.id.ddt_button_confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            };
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaperWorkChoiceActivity.class);
                startActivity(intent);
            };
        });

    }

    public void getData() {
        Intent intent = new Intent(this, ConfirmDDT.class);

        if (roomsSprayedValue.getText().toString().equals("")
                || sheltersSprayedValue.getText().toString().equals("")
                || roomsUnsprayedValue.getText().toString().equals("")
                || sheltersUnsprayedValue.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please input a value for every text field",
                    Toast.LENGTH_SHORT).show();
        } else {
            try {
                int roomsSprayed = Integer.parseInt(roomsSprayedValue.getText().toString());
                int sheltersSprayed = Integer.parseInt(sheltersSprayedValue.getText().toString());
                int roomsUnsprayed = Integer.parseInt(roomsUnsprayedValue.getText().toString());
                int sheltersUnsprayed = Integer.parseInt(sheltersUnsprayedValue.getText()
                        .toString());
                boolean refilled = canRefilled.getCheckedRadioButtonId() == R.id.ddt_radiobutton_canRefilledYes;
                intent.putExtra(Constants.ROOMS_SPRAYED, roomsSprayed);
                intent.putExtra(Constants.ROOMS_UNSPRAYED, roomsUnsprayed);
                intent.putExtra(Constants.SHELTERS_SPRAYED, sheltersSprayed);
                intent.putExtra(Constants.SHELTERS_UNSPRAYED, sheltersUnsprayed);
                intent.putExtra(Constants.CAN_REFILLED, refilled);
                intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                intent.putExtra(Constants.FORM_NUMBER, formNumber);

                formNumber += 1;

                startActivity(intent);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(),
                        "Please input integer values in every text field", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
