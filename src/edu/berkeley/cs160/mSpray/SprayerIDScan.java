package edu.berkeley.cs160.mSpray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SprayerIDScan extends Activity {

	ImageView rfidPic;
	Button backButton;
	Button confirmButton;
	Button addSprayer;
	TextView sprayerName;
	TextView sprayerName2;
	Handler handler;
	Boolean secondAdd;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sprayer);
        rfidPic = (ImageView)findViewById(R.id.add_sprater_imageView_RFIDPic);
        backButton = (Button)findViewById(R.id.add_sprayer_button_back);
        confirmButton = (Button)findViewById(R.id.add_sprayer_button_confirm);
        addSprayer = (Button)findViewById(R.id.add_sprayer_button_addSprayer);
        sprayerName = (TextView)findViewById(R.id.add_sprayer_textView_PersonName);
        sprayerName2 = (TextView)findViewById(R.id.add_sprayer_textView_PersonName2);
        
        sprayerName.setVisibility(View.INVISIBLE);
        sprayerName2.setVisibility(View.INVISIBLE);
        secondAdd = false;
        
        backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MSpray.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			};
		});
        
        confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				
			};
		});
        
       addSprayer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				rfidPic.setVisibility(View.VISIBLE);
				addSprayer.setVisibility(View.INVISIBLE);
				sprayerName.setVisibility(View.INVISIBLE);
				sprayerName2.setVisibility(View.INVISIBLE);
				TimeBomb();
			};
		});
       
       handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.TIME_BOMB_SWITH: {
					rfidPic.setVisibility(View.INVISIBLE);
					addSprayer.setVisibility(View.VISIBLE);
					sprayerName.setVisibility(View.VISIBLE);
					if (!secondAdd){
						addSprayer.setText(addSprayer.getText().toString() + " Another");
					}
					if (secondAdd){
						sprayerName2.setVisibility(View.VISIBLE);
					}
					secondAdd = true;
				}
				}
			}
		};
    }
    
    
    public void TimeBomb(){
    	Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(3000);
						handler.sendMessage(Message.obtain(handler,
								Constants.TIME_BOMB_SWITH));
	
					}
				} catch (InterruptedException ex) {
				}

			}
		};

		thread.start();
    }
    
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mspray, menu);
        return true;
    }

}