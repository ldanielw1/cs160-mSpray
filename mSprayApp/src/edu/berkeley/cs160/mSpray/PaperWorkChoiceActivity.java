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
    Button fendona;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemical_used);

        setTitle("Which chemical was sprayed?");

        header = (TextView) findViewById(R.id.chemical_used_header);
        header.setTypeface(Constants.TYPEFACE);

        ddt = (Button) findViewById(R.id.chemical_used_button_ddt);
        ddt.setTypeface(Constants.TYPEFACE);

        fendona = (Button) findViewById(R.id.chemical_used_button_fendona);
        fendona.setTypeface(Constants.TYPEFACE);

        korthrine = (Button) findViewById(R.id.chemical_used_button_korthrine);
        korthrine.setTypeface(Constants.TYPEFACE);

        noSpray = (Button) findViewById(R.id.chemical_used_button_noSpray);
        noSpray.setTypeface(Constants.TYPEFACE);

        fendona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.FENDONA);
                DataStore.sprayType = Constants.FENDONA;
                startActivity(intent);
            };
        });

        korthrine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.KORTHRINE);
                DataStore.sprayType = Constants.KORTHRINE;
                startActivity(intent);
            };
        });

        ddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.DDT);
                DataStore.sprayType = Constants.DDT;
                startActivity(intent);
            };
        });

        noSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.NO_SPRAY);
                DataStore.sprayType = Constants.NO_SPRAY;
                startActivity(intent);
            };
        });
    }
}