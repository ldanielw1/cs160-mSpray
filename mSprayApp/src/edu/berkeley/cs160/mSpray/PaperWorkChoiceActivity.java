package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaperWorkChoiceActivity extends Activity {
    Button korthrine;
    Button ddt;
    Button noSpray;
    Button back;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemical_used);

        setTitle("Which chemical was sprayed?");

        header = (TextView) findViewById(R.id.textView1);
        header.setTypeface(Constants.TYPEFACE);

        back = (Button) findViewById(R.id.chemical_used_button_back);
        back.setTypeface(Constants.TYPEFACE);

        ddt = (Button) findViewById(R.id.chemical_used_button_ddt);
        ddt.setTypeface(Constants.TYPEFACE);

        korthrine = (Button) findViewById(R.id.chemical_used_button_korthrine);
        korthrine.setTypeface(Constants.TYPEFACE);

        noSpray = (Button) findViewById(R.id.chemical_used_button_noSpray);
        noSpray.setTypeface(Constants.TYPEFACE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetGpsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            };
        });

        ddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.DDT);
                DataStore.sprayType = Constants.DDT;
                startActivity(intent);
            };
        });

        korthrine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.KORTHRINE);
                DataStore.sprayType = Constants.KORTHRINE;
                startActivity(intent);
            };
        });

        noSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.NO_SPRAY);
                DataStore.sprayType = Constants.NO_SPRAY;
                startActivity(intent);
            };
        });
    }
}