package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaperWorkChoiceActivity extends Activity {
    Button pyrethroid;
    Button ddt;
    Button noSpray;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemical_used);

        Bundle extras = this.getIntent().getExtras();
        final int numSprayers = extras.getInt(Constants.NUM_SPRAYERS);

        back = (Button) findViewById(R.id.chemical_used_button_back);
        ddt = (Button) findViewById(R.id.chemical_used_button_ddt);
        pyrethroid = (Button) findViewById(R.id.chemical_used_button_pyrethroid);
        noSpray = (Button) findViewById(R.id.chemical_used_button_noSpray);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            };
        });

        ddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DDTActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                intent.putExtra(Constants.FORM_NUMBER, 1);
                startActivity(intent);
            };
        });

        pyrethroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PyrethroidActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                intent.putExtra(Constants.FORM_NUMBER, 1);
                startActivity(intent);
            };
        });

        noSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoSprayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.NUM_SPRAYERS, numSprayers);
                intent.putExtra(Constants.FORM_NUMBER, 1);
                startActivity(intent);
            };
        });
    }
}