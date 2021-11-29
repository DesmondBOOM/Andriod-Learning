package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "this is main activity");

        button = (Button) findViewById(R.id.button_1);
        button.setOnClickListener(v -> openConstraintLayout());
    }

    private void openConstraintLayout() {
        Intent intent = new Intent(this, ConstraintActivity.class);
        startActivity(intent);
    }

}