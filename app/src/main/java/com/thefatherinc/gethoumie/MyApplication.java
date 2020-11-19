package com.thefatherinc.gethoumie;

import android.app.Application;

public class MyApplication extends Application {

    public DemoClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new DemoClient(getApplicationContext());
        client.init();
    }
}
