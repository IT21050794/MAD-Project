package com.tech.dresshub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private ArrayList<Map<String, String>> myArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                    Intent mainIntent = new Intent(MainActivity.this,ChooseActor.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}