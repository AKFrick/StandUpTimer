package com.akf.standuptimer;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by akfri on 02/07/18.
 */

public class ZingZing extends IntentService {

    public ZingZing(){
        super("Hello");
    }
    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();

    }
}
