package com.example.batteryalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FrameLayout frmLayout;
    ImageView imgView;
    Animation animation;
    TextView txtView;
    ObjectAnimator scaleDownX, scaleDownY, txtScaleX, txtScaleY;
    AnimatorSet scaleDown = new AnimatorSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frmLayout = findViewById(R.id.myFrame);
        imgView = findViewById(R.id.myImgView);
        txtView = findViewById(R.id.mainTitle);

        //For animation - scale down of image
        scaleDownX = ObjectAnimator.ofFloat(imgView, "scaleX", 0.6f);
        scaleDownY = ObjectAnimator.ofFloat(imgView, "scaleY", 0.6f);
        scaleDownX.setDuration(1500);
        scaleDownY.setDuration(1500);

        scaleDown.play(scaleDownX).with(scaleDownY);
        scaleDown.start();

        //For animation - scale down of text
        txtView.setTextColor(Color.WHITE);
        txtView.bringToFront();
        txtScaleX = ObjectAnimator.ofFloat(txtView, "scaleX", 0.5f);
        txtScaleY = ObjectAnimator.ofFloat(txtView, "scaleY", 0.5f);
        txtScaleX.setDuration(1500);
        txtScaleY.setDuration(1500);
        scaleDown.play(txtScaleX).with(txtScaleY);
        scaleDown.start();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up_animation);
        txtView.startAnimation(animation);

        //For animation - Move FrameLayout
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_left_animation);
        imgView.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_animation);
        frmLayout.startAnimation(animation);


    }
}