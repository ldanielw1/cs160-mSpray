package edu.berkeley.cs160.GPS;

import edu.berkeley.cs160.mSpray.Constants;
import edu.berkeley.cs160.mSpray.DataStore;
import edu.berkeley.cs160.mSpray.PaperWorkChoiceActivity;
import edu.berkeley.cs160.mSpray.R;
import edu.berkeley.cs160.mSpray.R.id;
import edu.berkeley.cs160.mSpray.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetGpsActivity extends Activity {
	ProgressDialog progDialog;
	TextView LocationFound;
	TextView header;
	Handler handler;
	Button backButton;
	Button confirmButton;

	double latitude;
	double longitude;
	String latitudeNS;
	String longitudeEW;
	String acc;

	boolean gpsFound = false;
	boolean usingDummyGps = false;

	private int gpsAttempts = 0;
	
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gps_location);

		setTitle("Where are you?");
		
		context = getApplicationContext();
		
		
		
		LocationFound = (TextView) findViewById(R.id.gps_location_textview_contents);
		LocationFound.setTypeface(Constants.TYPEFACE);

		confirmButton = (Button) findViewById(R.id.gps_location_button_confirmButton);
		confirmButton.setTypeface(Constants.TYPEFACE);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						PaperWorkChoiceActivity.class);
				startActivity(intent);
			};
		});

		LocationFound.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onStart() {
		super.onStart();

		progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setMessage("Finding Your Location...");
		progDialog.show();

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.GPS_FOUND: {
					LocationFound.setVisibility(View.VISIBLE);
					TextView gpsText = (TextView) findViewById(R.id.gps_location_textview_contents);
					final StringBuilder gpsResultBuilder = new StringBuilder();
					gpsResultBuilder.append("I am at: ");
					gpsResultBuilder.append(latitude);
					gpsResultBuilder.append(" ");
					gpsResultBuilder.append(latitudeNS);
					gpsResultBuilder.append(", ");
					gpsResultBuilder.append(longitude);
					gpsResultBuilder.append(" ");
					gpsResultBuilder.append(longitudeEW);
					gpsResultBuilder.append("\n Accuracy: ");
					gpsResultBuilder.append(acc);
					gpsResultBuilder.append(" meters");
					gpsText.setText(gpsResultBuilder.toString());
					DataStore.setGPS(latitude, longitude, latitudeNS,
							longitudeEW, acc);
					System.out.println(acc);
					progDialog.dismiss();
					break;
				}
				case Constants.GPS_NOT_FOUND: {
					progDialog.dismiss();
					retryDialog("Gps Not Found", GetGpsActivity.this);
					break;
				}
				case Constants.GPS_NOT_ACCURATE_ENOUGH: {
					progDialog.dismiss();
					retryDialog("Gps Not Accurate Enough", GetGpsActivity.this);
					break;
				}
				case Constants.GPS_RETRY:{
					LocationHelper.getLocation(context);
					break;
				}
				}
			}
		};
		checkGpsEnable();
		findGPS();
	}

	public void retryDialog(String text, Context context) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle(text);
		alertDialogBuilder.setMessage("Retry?");
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						{
							dialog.dismiss();
							getGPS();
						}
						
					}
				});
		alertDialogBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						DataStore.setGPS(22.879, 30.729, "S", "E", "7");
						LocationFound.setVisibility(View.VISIBLE);
						LocationFound.setText("OLD " + LocationFound.getText());
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	private void findGPS() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				synchronized (this) {
					getGPS();
				}
			}
		};

		thread.start();
	}

	/**
	 * tries to get a reading under our current amount 50 times. 
	 */
	public void getGPS() {
		LocationManager locManager;
		locManager = (LocationManager) getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locManager.getBestProvider(criteria, false);
		
		Location location = locManager.getLastKnownLocation(provider);
		if (location == null) {
			System.out.println("trying network instead");
			provider = LocationManager.NETWORK_PROVIDER;
			location = locManager.getLastKnownLocation(provider);
		}

		if (location != null) {
			if (accurateEnough(location)) {
				hemisphereCoordinate(location);
				acc = "" + location.getAccuracy();
				handler.sendMessage(Message
						.obtain(handler, Constants.GPS_FOUND));
			} else {
				if (gpsAttempts < 50) {
					gpsAttempts++;
					handler.sendMessage(Message.obtain(handler,
							Constants.GPS_RETRY));
					getGPS();
				} else {
					handler.sendMessage(Message.obtain(handler,
							Constants.GPS_NOT_ACCURATE_ENOUGH));
				}
			}
		} else {
			usingDummyGps = true;
			handler.sendMessage(Message
					.obtain(handler, Constants.GPS_NOT_FOUND));
		}

	}

	public void checkGpsEnable() {
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// Check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to
		// go to the settings
		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
	}

	public boolean accurateEnough(Location loc) {
		if (loc.getAccuracy() < Constants.GPS_ACCURACY_LIMIT) {
			gpsFound = true;
			return true;
		}
		return false;
	}

	public void hemisphereCoordinate(Location location) {
		latitude = Math.round(location.getLatitude());
		if (latitude > 0) {
			latitudeNS = "N";
		} else {
			latitude *= -1;
			latitudeNS = "S";
		}
		longitude = Math.round(location.getLongitude());
		if (longitude > 0) {
			longitudeEW = "E";
		} else {
			longitude *= -1;
			longitudeEW = "W";
		}
	}

}
