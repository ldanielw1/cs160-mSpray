package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class SecondChemicalUsedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_chemical_used);

        Bundle extras = this.getIntent().getExtras();
        String firstChemical = extras.getString(Constants.FIRST_CHEMICAL_USED);
        Button ddtButton = (Button) findViewById(R.id.second_chemical_used_button_ddt);
        Button pyrethroidButton = (Button) findViewById(R.id.second_chemical_used_button_pyrethroid);
        if (firstChemical.equals(Constants.DDT)) {
            // ddtButton.setVisibility(View.GONE);
            ddtButton.setEnabled(false);
        } else {
            // If the first chemical wasn't DDT, then it was a pyrethroid
            // pyrethroidButton.setVisibility(View.INVISIBLE);
            pyrethroidButton.setEnabled(false);
        }
    }
}
