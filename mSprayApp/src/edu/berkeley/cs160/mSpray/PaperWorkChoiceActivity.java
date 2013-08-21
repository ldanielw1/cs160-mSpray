package edu.berkeley.cs160.mSpray;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.berkeley.cs160.Base.BaseMainActivity;

public class PaperWorkChoiceActivity extends BaseMainActivity {
    Button korthrine;
    Button ddt;
    Button noSpray;
    Button fendona;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemical_used);

        setTitle(DataStore.screenTitlePrefix + "Which chemical was sprayed?");

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

    /**
     * Overriding the back button. I thought it was a solid decision because why
     * would they go back after the achieved a GPS? It just makes the app more
     * complicated if you include the ability to go back at this step.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}