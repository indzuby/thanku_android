package com.yellowfuture.thanku.common;

import android.app.Application;
import android.content.Context;

import com.tsengvn.typekit.Typekit;

/**
 * Created by zuby on 2016. 6. 15..
 */
public class MainApplication extends Application{


    @Override
    public void onCreate() {
        init();
        super.onCreate();
    }
    /**
     * Application 최초 구동시 수행해야 할 작업을 처리
     */
    private void init() {
        Context context = getApplicationContext();
        // The following line triggers the initialization of ACRA
    }
}
