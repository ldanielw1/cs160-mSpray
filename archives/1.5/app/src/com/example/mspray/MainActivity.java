package com.example.mspray;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    private static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        ImageButton buttonPlus = (ImageButton) findViewById(R.id.imageButton);
        buttonPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch webform
                startActivity(new Intent(mContext, mSprayForm.class));
            }
        });

        ImageButton buttonInfo = (ImageButton) findViewById(R.id.imageButton1);
        buttonInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch About
                startActivity(new Intent(mContext, AboutActivity.class));
            }
        });

    }

    /* Handles item selections */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            // start the About view
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
