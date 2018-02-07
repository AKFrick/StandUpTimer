package com.akf.standuptimer;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by akfri on 02/07/18.
 */

public class ZingZing extends IntentService {
    MyTimerTask myTimerTask;
    Timer timer;
    public ZingZing(){
        super("Hello");
    }
    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();

    }
    private final IBinder mBinder = new LocalBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public class LocalBinder extends Binder {
        ZingZing getService() {
            // Return this instance of LocalService so clients can call public methods
            return ZingZing.this;
        }
    }
    class MyTimerTask extends TimerTask {
        Context context;
        MyTimerTask(Context context){
            this.context = context;
        }
        @Override
        public void run() {

            MediaPlayer mp = MediaPlayer.create(context, R.raw.svist);
            mp.start();
            /*runOnUiThread(new Runnable(){
                @Override
                public void run(){

                }
            }); */
        }
    }
    public void startSchedule(long period){
        timer = new Timer();
        myTimerTask = new MyTimerTask(ZingZing.this);
        timer.schedule(myTimerTask, 2000, period);
    }
    public void stopSchedule(){
        timer.cancel();
    }
}
