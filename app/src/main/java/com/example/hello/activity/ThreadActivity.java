package com.example.hello.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

import com.example.hello.R;

public class ThreadActivity extends AppCompatActivity {

    private Button btnThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_layout);
        initUI();
    }

    private void initUI() {
        btnThread = findViewById(R.id.btn_thread);
        btnThread.setOnClickListener(v -> initCounter());
    }

    private void initCounter() {
        disableButton();
        startCounter();
    }

    private void startCounter() {
        Thread thread = new Thread(() -> {
            Log.d("[counter]", "new thread: " + Thread.currentThread().getName());
            for (int index = 0; index <= 10; ++index) {
                setButtonText(index);
                if (index != 10) {
                    SystemClock.sleep(1000);
                }
            }
            enableButton();
        });
        thread.start();
    }

    private void setButtonText(int index) {
        runOnUiThread(() -> {
            btnThread.setText(String.valueOf(index));
            Log.d("[setButtonText]", "runOnUiThread: " + Thread.currentThread().getName());
        });
    }

    private void disableButton() {
        btnThread.setEnabled(false);
        btnThread.setClickable(false);
        Log.d("[startCounter]", "init " + Thread.currentThread().getName());
        Log.d("[startCounter]", "Disable button");
        Log.d("[startCounter]", "btn enabled: " + btnThread.isEnabled());
        Log.d("[startCounter]", "btn clickable: " + btnThread.isClickable());
    }

    private void enableButton() {
        runOnUiThread(() -> {
            btnThread.setEnabled(true);
            btnThread.setClickable(true);
            Log.d("[counter]", "Enable button");
            Log.d("[counter]", "btn enabled: " + btnThread.isEnabled());
            Log.d("[counter]", "btn clickable: " + btnThread.isClickable());
        });
    }
}
