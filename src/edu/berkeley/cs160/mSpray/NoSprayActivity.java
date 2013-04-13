package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//<<<<<<< HEAD
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//=======
import android.widget.TextView;
//>>>>>>> a8c18ca2821cc819d765afc857fed7908bffe61d

public class NoSprayActivity extends Activity {

    TextView userName;
    Button backButton;
    Button confirmButton;
    TextView roomsUnsprayedLabel;
    TextView sheltersUnsprayedLabel;

    private int numSprayers;
    private int formNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//<<<<<<< HEAD
//        setContentView(R.layout.no_spray);
//        roomsUnsprayedValue = (EditText) findViewById(R.id.no_spray_edittext_roomsUnsprayedValue);
//        roomsUnsprayedValue.setTypeface(Constants.TYPEFACE);
//        sheltersUnsprayedValue = (EditText) findViewById(R.id.no_spray_edittext_sheltersUnsprayedValue);
//        sheltersUnsprayedValue.setTypeface(Constants.TYPEFACE);
        
//=======
        setContentView(R.layout.confirm_no_spray);

        Bundle extras = this.getIntent().getExtras();
        numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        formNumber = extras.getInt(Constants.FORM_NUMBER);

        userName = (TextView) findViewById(R.id.confirm_no_spray_textview_contents);
        backButton = (Button) findViewById(R.id.confirm_no_spray_button_backButton);
        confirmButton = (Button) findViewById(R.id.confirm_no_spray_button_confirmButton);
//>>>>>>> a8c18ca2821cc819d765afc857fed7908bffe61d

        backButton.setTypeface(Constants.TYPEFACE);
        confirmButton.setTypeface(Constants.TYPEFACE);
        
        roomsUnsprayedLabel = (TextView) findViewById(R.id.no_spray_textview_roomsUnsprayed);
        roomsUnsprayedLabel.setTypeface(Constants.TYPEFACE);
        
        sheltersUnsprayedLabel = (TextView) findViewById(R.id.no_spray_textview_sheltersUnsprayed);
        sheltersUnsprayedLabel.setTypeface(Constants.TYPEFACE);

        if (formNumber == 1)
            userName.setText("You are: " + DataStore.sprayer1ID);
        else if (formNumber == 2)
            userName.setText("You are: " + DataStore.sprayer2ID);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formNumber != numSprayers) {
                    Intent intent = new Intent(getApplicationContext(), ScanSprayer.class);
                    intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                    intent.putExtra(Constants.FORM_NUMBER, formNumber + 1);
                    intent.putExtra(Constants.SPRAY_TYPE, Constants.NO_SPRAY);
                    startActivity(intent);
                } else if (formNumber == numSprayers) {
                    Intent intent = new Intent(getApplicationContext(), UnsprayedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            };
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.NO_SPRAY);
                startActivity(intent);
            };
        });

    }
}
