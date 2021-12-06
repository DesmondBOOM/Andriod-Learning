package com.example.hello.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.hello.R;

public class SharedPreferencesUtils {

    public static void writeSpInt(Activity activity, String key, int value) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void writeSpBoolean(Activity activity, String key, Boolean value) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static int readSpInt(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = activity.getResources().getInteger(R.integer.read_sp_default_int);
        return sharedPref.getInt(key, defaultValue);
    }

    public static boolean readSpBoolean(Activity activity, String key, boolean defaultValue) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, defaultValue);
    }
}
