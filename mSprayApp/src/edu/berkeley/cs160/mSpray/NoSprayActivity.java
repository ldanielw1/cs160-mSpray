package edu.berkeley.cs160.mSpray;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.berkeley.cs160.Base.BaseMainActivity;

public class NoSprayActivity extends BaseMainActivity {

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
        setContentView(R.layout.no_spray);

        Bundle extras = this.getIntent().getExtras();
        numSprayers = extras.getInt(Constants.NUM_SPRAYERS);
        formNumber = extras.getInt(Constants.FORM_NUMBER);

        backButton = (Button) findViewById(R.id.confirm_no_spray_button_backButton);
        confirmButton = (Button) findViewById(R.id.confirm_no_spray_button_confirmButton);

        backButton.setTypeface(Constants.TYPEFACE);
        confirmButton = (Button) findViewById(R.id.confirm_no_spray_button_confirmButton);
        confirmButton.setTypeface(Constants.TYPEFACE);

        /* Name of sprayer */
        userName = (TextView) findViewById(R.id.confirm_no_spray_textview_contents);
        if (formNumber == 1)
            userName.setText(DataStore.sprayer1ID);
        else if (formNumber == 2)
            userName.setText(DataStore.sprayer2ID);
        userName.setTypeface(Constants.TYPEFACE);

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
                    DataStore.homesteadSprayed = false;
                    Intent intent = new Intent(getApplicationContext(), UnsprayedActivity.class);
                    startActivity(intent);
                }
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
