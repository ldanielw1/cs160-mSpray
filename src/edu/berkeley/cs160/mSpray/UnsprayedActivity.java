package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UnsprayedActivity extends Activity {

    EditText roomsUnsprayedValue;
    EditText sheltersUnsprayedValue;
    Button backButton;
    Button confirmButton;

    int numSprayers;
    int formNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_spray);

        roomsUnsprayedValue = (EditText) findViewById(R.id.no_spray_edittext_roomsUnsprayedValue);
        sheltersUnsprayedValue = (EditText) findViewById(R.id.no_spray_edittext_sheltersUnsprayedValue);
        backButton = (Button) findViewById(R.id.no_spray_button_backButton);
        confirmButton = (Button) findViewById(R.id.no_spray_button_confirmButton);

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
                intent.putExtra(Constants.ROOMS_UNSPRAYED, roomsUnsprayed);
                intent.putExtra(Constants.SHELTERS_UNSPRAYED, sheltersUnsprayed);
                startActivity(intent);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(),
                        "Please input integer values in every text field", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
