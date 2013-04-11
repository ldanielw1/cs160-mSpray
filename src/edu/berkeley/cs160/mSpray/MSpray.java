package edu.berkeley.cs160.mSpray;

import java.io.IOException;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MSpray extends Activity {

    Button startSpray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mspray);

        startSpray = (Button) findViewById(R.id.activity_mspray_button);

         // External font
         TextView tv = (TextView) findViewById(R.id.activity_mspray_header);
         String fontPath = "fonts/life.ttf";
         Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
         

         // Apply fonts
         tv.setTypeface(tf);
         tv.setTextSize(28);
         
         startSpray.setTypeface(tf);
         startSpray.setTextSize(40);

        startSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Scanner s = new Scanner(getAssets().open("authentication.txt"));
                    while (s.hasNextLine())
                        System.err.println(s.nextLine());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), SprayerIDScan.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            };
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mspray, menu);
        return true;
    }

}
