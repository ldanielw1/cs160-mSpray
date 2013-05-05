package edu.berkeley.cs160.mSpray;

import edu.berkeley.cs160.Base.BaseMainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChooseSprayer extends BaseMainActivity {

    TextView chooseOneSprayerText;
    TextView chooseTwoSprayerText;
    RelativeLayout chooseOneSprayer;
    RelativeLayout chooseTwoSprayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sprayers);

        Bundle extras = this.getIntent().getExtras();
        final String sprayType = extras.getString(Constants.SPRAY_TYPE);

        setTitle("How many sprayers?");

        chooseOneSprayer = (RelativeLayout) findViewById(R.id.activity_choose_sprayers_1_fake_button);
        chooseOneSprayerText = (TextView) findViewById(R.id.activity_choose_sprayer_textView);
        chooseTwoSprayerText = (TextView) findViewById(R.id.activity_choose_sprayer2_textView);

        chooseOneSprayer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanSprayer.class);
                intent.putExtra(Constants.SPRAY_TYPE, sprayType);
                intent.putExtra(Constants.NUM_SPRAYERS, 1);
                intent.putExtra(Constants.FORM_NUMBER, 1);
                startActivity(intent);
            }
        });
        chooseOneSprayerText.setTypeface(Constants.TYPEFACE);

        chooseTwoSprayers = (RelativeLayout) findViewById(R.id.activity_choose_sprayers_2_fake_button);

        chooseTwoSprayers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanSprayer.class);
                intent.putExtra(Constants.SPRAY_TYPE, sprayType);
                intent.putExtra(Constants.FORM_NUMBER, 1);
                intent.putExtra(Constants.NUM_SPRAYERS, 2);
                startActivity(intent);
            }
        });
        chooseTwoSprayerText.setTypeface(Constants.TYPEFACE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mspray, menu);
        return true;
    }
}