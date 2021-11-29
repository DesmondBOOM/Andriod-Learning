package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button_1;
    Button button_2;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "this is main activity");

        button_1 = (Button) findViewById(R.id.button_1);
        button_1.setOnClickListener(v -> openConstraintActivity());

        button_2 = (Button) findViewById(R.id.button_2);
        button_2.setOnClickListener(v -> openLoginActivity());
    }

    private void openConstraintActivity() {
        Intent intent = new Intent(this, ConstraintActivity.class);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}