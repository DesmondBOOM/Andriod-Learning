package com.example.hello.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.hello.R;

public class SharedPreferencesUtils {

    public void writeSpInt(Activity activity, String key, int value) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void writeSpBoolean(Activity activity, String key, Boolean value) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public int readSpInt(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = activity.getResources().getInteger(R.integer.read_sp_default_int);
        return sharedPref.getInt(key, defaultValue);
    }

    public boolean readSpBoolean(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        boolean defaultValue = activity.getResources().getBoolean(R.bool.read_sp_default_bool);
        return sharedPref.getBoolean(key, defaultValue);
    }
}
