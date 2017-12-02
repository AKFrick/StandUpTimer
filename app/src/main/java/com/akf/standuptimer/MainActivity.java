package com.akf.standuptimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private Timer timer;
    private MyTimerTask myTimerTask;

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer = new Timer();
                myTimerTask = new MyTimerTask(textView);
                timer.schedule(myTimerTask, 5000);
            }
        });
    }
    class MyTimerTask extends TimerTask {
        private TextView textView;
        MyTimerTask(TextView textView){
            this.textView = textView;
        }
        @Override
        public void run() {

            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    textView.setText("Работает!");
                }
            });
        }
    }
}