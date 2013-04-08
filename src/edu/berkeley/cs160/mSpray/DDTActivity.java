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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddt);
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
        boolean refilled = false;
        String numbers = "0123456789";

        if (roomsSprayedValue.getText().toString().equals("")
                || sheltersSprayedValue.getText().toString().equals("")
                || roomsUnsprayedValue.getText().toString().equals("")
                || sheltersUnsprayedValue.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please input a value for every text field",
                    Toast.LENGTH_SHORT).show();
        } else if (numbers.contains(roomsSprayedValue.getText().toString())
                || numbers.contains(sheltersSprayedValue.getText().toString())
                || numbers.contains(roomsUnsprayedValue.getText().toString())
                || numbers.contains(sheltersUnsprayedValue.getText().toString())) {
            int roomsSprayed = Integer.valueOf(roomsSprayedValue.getText().toString());
            int sheltersSprayed = Integer.valueOf(sheltersSprayedValue.getText().toString());
            int roomsUnsprayed = Integer.valueOf(roomsUnsprayedValue.getText().toString());
            int sheltersUnsprayed = Integer.valueOf(sheltersUnsprayedValue.getText().toString());
            if (canRefilled.getCheckedRadioButtonId() == R.id.ddt_radiobutton_canRefilledYes) {
                refilled = true;
            }
            intent.putExtra("roomsSprayed", roomsSprayed);
            intent.putExtra("roomsUnsprayed", roomsUnsprayed);
            intent.putExtra("sheltersSprayed", sheltersSprayed);
            intent.putExtra("sheltersUnsprayed", sheltersUnsprayed);
            intent.putExtra("canRefilled", refilled);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(),
                    "Please input integer values in every text field", Toast.LENGTH_SHORT).show();
    }
}
