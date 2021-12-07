package com.example.hello.activity;

import android.app.Application;

import com.example.hello.utils.Dependency;


public class HelloApp extends Application {

    private Dependency dependency = null;

    @Override
    public void onCreate() {
        super.onCreate();
        dependency = Dependency.getInstance(this);
    }

    public Dependency getDependency() {
        return dependency;
    }
}
