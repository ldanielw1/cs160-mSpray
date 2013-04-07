package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ConfirmDDT extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_ddt);
		Bundle extras = this.getIntent().getExtras();
		int roomsSprayed = extras.getInt("roomsSprayed");
		int roomsUnsprayed = extras.getInt("roomsUnsprayed");
		int sheltersSprayed = extras.getInt("sheltersSprayed");
		int sheltersUnsprayed = extras.getInt("sheltersUnsprayed");
		boolean refilled = extras.getBoolean("canRefilled");
		String c = refilled ? "" : "not ";
		TextView results = (TextView) findViewById(R.id.confirm_ddt_textview_contents);
		//NEED TO ADD SPRAYER NAME
		results.setText(String.format("Sprayers: %s\n"
				+"Rooms Sprayed: %d\n"
				+"Shelters Sprayed: %d\n"
				+"Rooms Unsprayed: %d\n"
				+"Shelters Unsprayed: %d\n"
				+"Can %srefilled", "TO-DO NAME",roomsSprayed, sheltersSprayed, roomsUnsprayed, sheltersUnsprayed, c));
	}
}
