package com.example.batteryalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FrameLayout frmLayout;
    ImageView imgView;
    Animation animation;
    TextView txtView;
    Button btnStart, btnStop;
    EditText battInput;
    ObjectAnimator scaleDownX, scaleDownY, txtScaleX, txtScaleY;
    AnimatorSet scaleDown = new AnimatorSet();
    Spinner ringtoneSpinner;

    String[] items =  {"Select Ringtone","Design","Components","Android","5.0 Lollipop"};
    ArrayAdapter<String> adapterItems;

    private TextView battLevel;
    private Ringtone ringtone;
    private int myProgress = 0;
    private int currentBattInput = 0;
    private int currentBatt = 0;

    Runnable stopPlayerTask = new Runnable(){
        @Override
        public void run() {
            ringtone.stop();
            battInput.setEnabled(true);
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frmLayout = findViewById(R.id.myFrame);
        imgView = findViewById(R.id.myImgView);
        txtView = findViewById(R.id.mainTitle);
        battLevel = findViewById(R.id.currentBatt);
        ringtoneSpinner = findViewById(R.id.spinRingtone);
        battInput = findViewById(R.id.batteryInput);
        btnStart = findViewById(R.id.btnStartAlarm);
        btnStop = findViewById(R.id.btnStopAlarm);

        //For getting the battery level
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        BroadcastReceiver batteryBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int battPerc = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                battLevel.setText(String.valueOf(battPerc) + "%");
                currentBatt = battPerc;
            }
        };

        registerReceiver(batteryBroadcast, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        BroadcastReceiver checkBatteryLevelBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                myProgress = (int)(level * 100 / (float)scale);

                if(myProgress == currentBattInput){
                    ringtone.play();
                    ringtone.setLooping(true);
                    Handler handler = new Handler();
                    handler.postDelayed(stopPlayerTask, 30000);
                    btnStart.setEnabled(true);
                }
            }
        };


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
        ringtoneSpinner.bringToFront();
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

        //For Spinner
        adapterItems = new ArrayAdapter<String>(this, R.layout.ringtone_list, items);
        adapterItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ringtoneSpinner.setPrompt("Select Ringtone");
        ringtoneSpinner.setAdapter(adapterItems);

        /*Toast.makeText(getApplicationContext(), " " + getNotifications().hashCode(), Toast.LENGTH_LONG).show();*/

        battInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnStart.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnStart.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(battInput.equals("")){
                    btnStart.setEnabled(false);
                }
            }
        });

        btnStart.setEnabled(false);
        btnStop.setEnabled(false);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = battInput.getText().toString();
                currentBattInput = input.isEmpty()? 0:Integer.parseInt(input.toString());
                if(currentBattInput < currentBatt){
                    Toast.makeText(getApplicationContext(), "You entered battery level lower than your current battery level!", Toast.LENGTH_LONG).show();
                }else{
                    registerReceiver(checkBatteryLevelBroadcast, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    btnStart.setEnabled(false);
                    btnStop.setEnabled(true);
                    battInput.setEnabled(false);
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentBattInput = 0;
                Handler handler = new Handler();
                handler.postDelayed(stopPlayerTask, 100);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                battInput.setEnabled(true);
            }
        });

    }

    /*public Map<String, String> getNotifications() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();

        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(RingtoneManager.ID_COLUMN_INDEX);

            list.put(notificationTitle, notificationUri);
        }

        return list;
    }*/

}