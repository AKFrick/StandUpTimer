package com.akf.standuptimer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    ZingZing mService;
    boolean mBound = false;

    private TextView textView;
    private TextView showPeriodText;
    private ImageButton button;
    private ImageView increase;
    private ImageView decrease;
    private long period = 60_000;
    private boolean started = false;

    private long periodStep = 300_000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showPeriodText = (TextView) findViewById(R.id.showPeriod);
        button = (ImageButton) findViewById(R.id.button);
        increase = (ImageView) findViewById(R.id.increase);
        decrease = (ImageView) findViewById(R.id.decrease);

        showPeriodText.setText(ShowPeriod(period));



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!started && isPeriodValid(period)) {
                    button.setImageResource(R.drawable.stop);
                    started = true;
                    mService.startSchedule(period);
                } else {
                    started = false;
                    button.setImageResource(R.drawable.go);
                    mService.stopSchedule();
                }
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!started) {
                    IncreasePeriod();
                    showPeriodText.setText(ShowPeriod(period));
                }
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!started) {
                    DecreasePeriod();
                    showPeriodText.setText(ShowPeriod(period));
                }
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, ZingZing.class);
        //startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ZingZing.LocalBinder binder = (ZingZing.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    private void IncreasePeriod(){
        period += periodStep;

    }
    private void DecreasePeriod(){
        if (period > periodStep)
            period -= periodStep;
        else
            period = 0;
    }
    private String ShowPeriod(long period){
        return String.valueOf(period / 1000 / 60) + " минут";
    }
    private boolean isPeriodValid(long period){
        if (period <= 0)
            return false;
        else
            return true;
    }

}