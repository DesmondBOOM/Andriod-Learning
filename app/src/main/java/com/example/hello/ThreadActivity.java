package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

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
        btnThread.setOnClickListener(v -> startCounter());
    }

    private void startCounter() {
        btnThread.setEnabled(false);
        btnThread.setClickable(false);
        Log.d("[startCounter]", "init " + Thread.currentThread().getName());
        Log.d("[startCounter]", "Disable button");
        Log.d("[startCounter]", "btn enabled: " + btnThread.isEnabled());
        Log.d("[startCounter]", "btn clickable: " + btnThread.isClickable());

        counter();
    }

    private void counter() {
        Thread thread = new Thread(() -> {
            Log.d("[counter]", "new thread: " + Thread.currentThread().getName());

            for (int index = 0; index <= 10; ++index) {
                final Integer finalIndex = index;
                runOnUiThread(() -> {
                    btnThread.setText(String.valueOf(finalIndex));
                    Log.d("[counter]", "runOnUiThread: " + Thread.currentThread().getName());
                });
                if (index != 10) {
                    SystemClock.sleep(1000);
                }
            }

            runOnUiThread(() -> {
                btnThread.setEnabled(true);
                btnThread.setClickable(true);
                Log.d("[counter]", "Enable button");
                Log.d("[counter]", "btn enabled: " + btnThread.isEnabled());
                Log.d("[counter]", "btn clickable: " + btnThread.isClickable());
            });
        });
        thread.start();
    }
}