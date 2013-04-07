package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ConfirmNoSpray extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_no_spray);
		Bundle extras = this.getIntent().getExtras();
		int roomsUnsprayed = extras.getInt("roomsUnsprayed");
		int sheltersUnsprayed = extras.getInt("sheltersUnsprayed");
		TextView results = (TextView) findViewById(R.id.confirm_no_spray_textview_contents);
		//NEED TO ADD SPRAYER NAME
		results.setText(String.format("Sprayers: %s\n"
				+"Rooms Unsprayed: %d\n"
				+"Shelters Unsprayed: %d\n"
				+"Can %srefilled", "TO-DO NAME", roomsUnsprayed, sheltersUnsprayed));
	}
}
