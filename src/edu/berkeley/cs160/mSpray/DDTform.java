package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DDTform extends Activity {

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
        setContentView(R.layout.ddt);  
        getData();
    }
    
    public void getData(){
    	Intent i = new Intent(this, ConfirmDDT.class);
    	boolean refilled=false;
    	roomsSprayedValue = (EditText) findViewById(R.id.ddt_edittext_roomsSprayedValue);
    	sheltersSprayedValue = (EditText) findViewById(R.id.ddt_edittext_sheltersSprayedValue);
    	roomsUnsprayedValue = (EditText) findViewById(R.id.ddt_edittext_roomsUnsprayedValue);
    	sheltersUnsprayedValue = (EditText) findViewById(R.id.ddt_edittext_sheltersUnsprayedValue);
    	canRefilledYes = (RadioButton) findViewById(R.id.ddt_radiobutton_canRefilledYes);
    	canRefilledNo = (RadioButton) findViewById(R.id.ddt_radiobutton_canRefilledNo);
    	backButton = (Button) findViewById(R.id.ddt_button_backButton);
    	confirmButton = (Button) findViewById(R.id.ddt_button_confirmButton);
    	canRefilled = (RadioGroup) findViewById(R.id.ddt_radiogroup);
    	int roomsSprayed = Integer.valueOf(roomsSprayedValue.getText().toString());
    	int sheltersSprayed = Integer.valueOf(sheltersSprayedValue.getText().toString());
    	int roomsUnsprayed = Integer.valueOf(roomsUnsprayedValue.getText().toString());
    	int sheltersUnsprayed = Integer.valueOf(sheltersUnsprayedValue.getText().toString());
    	if(canRefilled.getCheckedRadioButtonId() == R.id.ddt_radiobutton_canRefilledYes){
    		refilled=true;
    	}
    	//ADD STUFF FOR BACK BUTTON AND CONFIRM BUTTON
    	i.putExtra("roomsSprayed", roomsSprayed);
    	i.putExtra("roomsUnsprayed", roomsUnsprayed);
    	i.putExtra("sheltersSprayed", sheltersSprayed);
    	i.putExtra("sheltersUnsprayed", sheltersUnsprayed);
    	i.putExtra("canRefilled", refilled);
    	startActivity(i);	
    }
}
