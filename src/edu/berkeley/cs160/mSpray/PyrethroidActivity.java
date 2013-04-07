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

public class PyrethroidActivity extends Activity {

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
        setContentView(R.layout.pyrethroid);
        canRefilled = (RadioGroup) findViewById(R.id.pyrethroid_radiogroup);
        roomsSprayedValue = (EditText) findViewById(R.id.pyrethroid_edittext_roomsSprayedValue);
        sheltersSprayedValue = (EditText) findViewById(R.id.pyrethroid_edittext_sheltersSprayedValue);
        roomsUnsprayedValue = (EditText) findViewById(R.id.pyrethroid_edittext_roomsUnsprayedValue);
        sheltersUnsprayedValue = (EditText) findViewById(R.id.pyrethroid_edittext_sheltersUnsprayedValue);
        canRefilled = (RadioGroup) findViewById(R.id.pyrethroid_radiogroup);
        canRefilledYes = (RadioButton) findViewById(R.id.pyrethroid_radiobutton_CanRefilledYes);
        canRefilledNo = (RadioButton) findViewById(R.id.pyrethroid_radiobutton_CanRefilledNo);
        backButton = (Button) findViewById(R.id.pyrethroid_button_backButton);
        confirmButton = (Button) findViewById(R.id.pyrethroid_button_confirmButton);

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
        Intent i = new Intent(this, ConfirmPyrethroid.class);
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
            if (canRefilled.getCheckedRadioButtonId() == R.id.pyrethroid_radiobutton_CanRefilledYes) {
                refilled = true;
            }
            i.putExtra("roomsSprayed", roomsSprayed);
            i.putExtra("roomsUnsprayed", roomsUnsprayed);
            i.putExtra("sheltersSprayed", sheltersSprayed);
            i.putExtra("sheltersUnsprayed", sheltersUnsprayed);
            i.putExtra("canRefilled", refilled);
            startActivity(i);
        } else
            Toast.makeText(getApplicationContext(),
                    "Please input integer values in every text field", Toast.LENGTH_SHORT).show();
    }
}
