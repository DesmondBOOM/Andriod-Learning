package com.example.hello;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class PickUpContactActivity extends AppCompatActivity {

    private static final String TAG = "[PickUpContactActivity]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_contact);
        Log.d(TAG, "on create");
    }



}