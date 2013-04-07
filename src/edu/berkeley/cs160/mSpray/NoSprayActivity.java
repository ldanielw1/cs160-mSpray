package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NoSprayActivity extends Activity {

	EditText roomsSprayedValue;
	EditText sheltersSprayedValue;
	EditText roomsUnsprayedValue;
	EditText sheltersUnsprayedValue;
	RadioGroup canRefilled;
	RadioButton canRefilledYes;
	RadioButton canRefilledNo;
	Button backButton;
	Button confirmButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.no_spray);  
		getData();
	}

	public void getData(){
		Intent i = new Intent(this, ConfirmNoSpray.class);
		String numbers ="0123456789";
		roomsUnsprayedValue = (EditText) findViewById(R.id.no_spray_textview_roomsUnsprayed);
		sheltersUnsprayedValue = (EditText) findViewById(R.id.no_spray_edittext_roomsUnsprayedValue);
		backButton = (Button) findViewById(R.id.ddt_button_backButton);
		confirmButton = (Button) findViewById(R.id.ddt_button_confirmButton);
		if(roomsUnsprayedValue.getText().toString().equals("")
				|| sheltersUnsprayedValue.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "Please input a value for every text field", Toast.LENGTH_SHORT).show();
		} else if (numbers.contains(roomsUnsprayedValue.getText().toString())
				|| numbers.contains(sheltersUnsprayedValue.getText().toString())){
			int roomsUnsprayed = Integer.valueOf(roomsUnsprayedValue.getText().toString());
			int sheltersUnsprayed = Integer.valueOf(sheltersUnsprayedValue.getText().toString());
			//ADD STUFF FOR BACK BUTTON AND CONFIRM BUTTON
			i.putExtra("roomsUnsprayed", roomsUnsprayed);
			i.putExtra("sheltersUnsprayed", sheltersUnsprayed);
			startActivity(i);	
		}
		else
			Toast.makeText(getApplicationContext(), "Please input integer values in every text field", Toast.LENGTH_SHORT).show();
	}
}
