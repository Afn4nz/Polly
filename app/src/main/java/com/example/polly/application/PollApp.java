package com.example.polly.application;

import android.app.Application;

import com.example.polly.database.DBHelper;

public class PollApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DBHelper.init(this);
    }
}
