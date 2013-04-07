package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondChemicalUsedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_chemical_used);

        Bundle extras = this.getIntent().getExtras();
        String firstChemical = extras.getString(Constants.FIRST_CHEMICAL_USED);
        if (firstChemical.equals(Constants.DDT)) {
            Button ddtButton = (Button) findViewById(R.id.second_chemical_used_button_ddt);
            ddtButton.setVisibility(View.GONE);
        } else {
            // If the first chemical wasn't DDT, then it was a pyrethroid
            Button pyrethroidButton = (Button) findViewById(R.id.second_chemical_used_button_pyrethroid);
            pyrethroidButton.setVisibility(View.INVISIBLE);
        }
    }
}
