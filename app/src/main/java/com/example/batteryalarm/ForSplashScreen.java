package com.example.batteryalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ForSplashScreen extends AppCompatActivity {

<<<<<<< HEAD
    private final int delay_time = 4500;
=======
    private final int delay_time = 1000;
>>>>>>> 9670caf2a098fdbf3193f08c24b38d87d2011a69

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
<<<<<<< HEAD
                Intent mainPage = new Intent(ForSplashScreen.this, LoginActivity.class);
=======
                Intent mainPage = new Intent(ForSplashScreen.this, MainActivity.class);
>>>>>>> 9670caf2a098fdbf3193f08c24b38d87d2011a69
                startActivity(mainPage);
                finish();
            }
        }, delay_time);

    }
}