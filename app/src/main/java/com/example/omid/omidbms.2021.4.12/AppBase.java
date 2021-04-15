package com.example.omid.omidbms;

import android.app.Application;
import android.content.SharedPreferences;

public class AppBase extends Application {
    SharedPreferences pref;

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
