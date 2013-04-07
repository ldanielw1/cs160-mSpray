package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OtherChemicalUsedActivity extends Activity {

	private String firstChemical;
	Button noButton;
	Button yesButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_chemical_used);

		yesButton = (Button) findViewById(R.id.other_chemical_used_button_yesButton);
		noButton = (Button) findViewById(R.id.other_chemical_used_button_noButton);

		Bundle extras = this.getIntent().getExtras();
		firstChemical = extras.getString(Constants.FIRST_CHEMICAL_USED);

		noButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						FinishedActivity.class);
				startActivity(intent);
			}
		});

		yesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						SecondChemicalUsedActivity.class);
				if (firstChemical.equals(Constants.DDT))
					intent.putExtra(Constants.FIRST_CHEMICAL_USED,
							Constants.DDT);
				// If the first chemical isn't DDT, it's a pyrethroid
				else
					intent.putExtra(Constants.FIRST_CHEMICAL_USED,
							Constants.PYRETHROID);
				startActivity(intent);
			}
		});
	}
}
