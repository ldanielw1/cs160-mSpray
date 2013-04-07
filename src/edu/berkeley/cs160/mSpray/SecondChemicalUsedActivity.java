package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondChemicalUsedActivity extends Activity {
	Button ddtButton;
	Button pyrethroidButton;
	Button backButton;

	Bundle extras;

	String firstChemical;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_chemical_used);

		ddtButton = (Button) findViewById(R.id.second_chemical_used_button_ddt);
		pyrethroidButton = (Button) findViewById(R.id.second_chemical_used_button_pyrethroid);
		backButton = (Button) findViewById(R.id.second_chemical_used_button_backButton);
		
		pyrethroidButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), PyrethroidActivity.class);
				startActivity(intent);
			}
		});
		
		ddtButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), DDTform.class);
				startActivity(intent);
			}
		});

		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		extras = this.getIntent().getExtras();
		firstChemical = extras.getString(Constants.FIRST_CHEMICAL_USED);

		if (firstChemical.equals(Constants.DDT)) {
			ddtButton.setVisibility(View.GONE);
		} else if (firstChemical.equals(Constants.PYRETHROID)) {

			pyrethroidButton.setVisibility(View.INVISIBLE);
		}
	}
}
