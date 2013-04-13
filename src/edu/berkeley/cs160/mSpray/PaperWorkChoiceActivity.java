package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaperWorkChoiceActivity extends Activity {
    Button pyrethroid;
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
        
        pyrethroid = (Button) findViewById(R.id.chemical_used_button_pyrethroid);
        pyrethroid.setTypeface(Constants.TYPEFACE);
        
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
                startActivity(intent);
            };
        });

        pyrethroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.PYRETHROID);
                startActivity(intent);
            };
        });

        noSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseSprayer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.SPRAY_TYPE, Constants.NO_SPRAY);
                startActivity(intent);
            };
        });
    }
}