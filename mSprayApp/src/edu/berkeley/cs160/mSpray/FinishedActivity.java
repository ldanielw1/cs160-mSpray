package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

public class FinishedActivity extends Activity {
	TextView chemicalValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finished);

		Button finishedButton = (Button) findViewById(R.id.finished_button_finishedButton);
		finishedButton.setTypeface(Constants.TYPEFACE);

		/* Populate paperwork table */
		TextView foremanValue = (TextView) findViewById(R.id.finished_foreman_value);
		TextView sprayerValue = (TextView) findViewById(R.id.finished_sprayer_value);
		chemicalValue = (TextView) findViewById(R.id.finished_chemical_value);
		TextView roomsSprayedValue = (TextView) findViewById(R.id.finished_rooms_sprayed_value);
		TextView sheltersSprayedValue = (TextView) findViewById(R.id.finished_shelters_sprayed_value);
		TextView canRefilledValue = (TextView) findViewById(R.id.finished_can_refilled_value);
		TextView sprayerValue2 = (TextView) findViewById(R.id.finished_sprayer2_value);
		TextView roomsSprayedValue2 = (TextView) findViewById(R.id.finished_rooms_sprayed2_value);
		TextView canRefilledValue2 = (TextView) findViewById(R.id.finished_can_refilled2_value);
		TextView sheltersSprayedValue2 = (TextView) findViewById(R.id.finished_shelters_sprayed2_value);
		TextView latitude = (TextView) findViewById(R.id.finished_latitude_value);
		TextView longitude = (TextView) findViewById(R.id.finished_longitude_value);

		boolean refillFlag = false;
		boolean refillFlag2 = false;

		foremanValue.setText(DataStore.foremanID);
		sprayerValue.setText(DataStore.sprayer1ID);

		setProperChemicalValue();

		if (DataStore.sprayer2ID != null) {
			sprayerValue2.setText(DataStore.sprayer2ID);
		} else {
			TableRow sprayer2 = (TableRow) findViewById(R.id.finished_sprayer2_row);
			TableRow sprayer2SprayedRooms = (TableRow) findViewById(R.id.finished_rooms_sprayed2_row);
			TableRow sprayer2SprayedShelters = (TableRow) findViewById(R.id.finished_shelters_sprayed2_row);
			TableRow sprayer2RefilledCans = (TableRow) findViewById(R.id.finished_can_refilled2_row);
			sprayer2.setVisibility(View.GONE);
			sprayer2SprayedRooms.setVisibility(View.GONE);
			sprayer2SprayedShelters.setVisibility(View.GONE);
			sprayer2RefilledCans.setVisibility(View.GONE);
		}

		if (DataStore.sprayType.equals(Constants.DDT)
				|| DataStore.sprayType.equals(Constants.KORTHRINE)
				|| DataStore.sprayType.equals(Constants.FENDONA)) {
			roomsSprayedValue
					.setText(Integer.toString(DataStore.sprayedRooms1));
			sheltersSprayedValue.setText(Integer
					.toString(DataStore.sprayedShelters1));
			refillFlag = DataStore.canRefill1;
			if (DataStore.sprayer2ID != null) {
				roomsSprayedValue2.setText(Integer
						.toString(DataStore.sprayedRooms2));
				sheltersSprayedValue2.setText(Integer
						.toString(DataStore.sprayedShelters2));
				refillFlag2 = DataStore.canRefill2;
			}
		} else if (DataStore.sprayType.equals(Constants.NO_SPRAY)) {
			roomsSprayedValue.setText("No spray");
			sheltersSprayedValue.setText("No spray");
			if (DataStore.sprayer2ID != null) {
				roomsSprayedValue2.setText("No spray");
				sheltersSprayedValue2.setText("No spray");
			}
		}

		/* Refill for Sprayer 1 */
		if (refillFlag) {
			canRefilledValue.setText("YES");
		} else {
			canRefilledValue.setText("NO");
		}

		/* Refill for Sprayer 2 */
		if (refillFlag2) {
			canRefilledValue2.setText("YES");
		} else {
			canRefilledValue2.setText("NO");
		}

		latitude.setText(DataStore.lat);
		longitude.setText(DataStore.lng);

		/* External font */
		foremanValue.setTypeface(Constants.TYPEFACE);
		sprayerValue.setTypeface(Constants.TYPEFACE);
		roomsSprayedValue.setTypeface(Constants.TYPEFACE);
		sheltersSprayedValue.setTypeface(Constants.TYPEFACE);
		sprayerValue2.setTypeface(Constants.TYPEFACE);
		roomsSprayedValue2.setTypeface(Constants.TYPEFACE);
		sheltersSprayedValue2.setTypeface(Constants.TYPEFACE);
		latitude.setTypeface(Constants.TYPEFACE);
		longitude.setTypeface(Constants.TYPEFACE);

		finishedButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DataStore.secondTimeThrough = true;
				Intent intent = new Intent(getApplicationContext(),
						StartNewSpray.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		finishedButton.setTypeface(Constants.TYPEFACE);
	}

	public void setProperChemicalValue() {
		if (DataStore.chemicalUsed2 == null) {
			chemicalValue.setText(DataStore.chemicalUsed1);
		} else if (chemicalValue != null) {
			chemicalValue.setText(DataStore.chemicalUsed2);
		} else {
			chemicalValue.setText("None");
		}
		chemicalValue.setTypeface(Constants.TYPEFACE);
	}
}
