package com.akf.standuptimer;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
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

    private Timer timer;
    private MyTimerTask myTimerTask;

    private TextView textView;
    private TextView showPeriodText;
    private Button button;
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
        button = (Button) findViewById(R.id.button);
        increase = (ImageView) findViewById(R.id.increase);
        decrease = (ImageView) findViewById(R.id.decrease);

        showPeriodText.setText(ShowPeriod(period));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!started && isPeriodValid(period)) {
                    button.setText("СТОП");
                    started = true;
                    timer = new Timer();
                    myTimerTask = new MyTimerTask(textView, MainActivity.this);
                    timer.schedule(myTimerTask, 2000,period);
                } else {
                    started = false;
                    timer.cancel();
                    button.setText("СТАРТ");
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
    class MyTimerTask extends TimerTask {
        private TextView textView;
        Activity activity;
        MyTimerTask(TextView textView, Activity activity){
            this.activity = activity;
            this.textView = textView;
        }
        @Override
        public void run() {

            MediaPlayer mp = MediaPlayer.create(activity, R.raw.svist);
            mp.start();
            /*runOnUiThread(new Runnable(){
                @Override
                public void run(){

                }
            }); */
        }
    }
}