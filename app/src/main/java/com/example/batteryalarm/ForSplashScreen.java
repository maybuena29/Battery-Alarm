package com.example.batteryalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ForSplashScreen extends AppCompatActivity {

    private final int delay_time = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent mainPage = new Intent(ForSplashScreen.this, MainActivity.class);
                startActivity(mainPage);
                finish();
            }
        }, delay_time);

    }
}