package com.example.hello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncQueryHandler;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class HandlerActivity extends AppCompatActivity {

    Button button_first;
    Button button_second;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_layout);
        initUI();


        new LooperThread().start();
    }

    private void initUI() {
        button_first = findViewById(R.id.btn_handler_first);
        button_second = findViewById(R.id.btn_handler_second);
        button_first.setOnClickListener(v -> {
            Log.d("[HandlerActivity]", "click on button 1");
            sendMessage(111);
        });
        button_second.setOnClickListener(v -> {
            Log.d("[HandlerActivity]", "click on button 2");
            sendMessage(222);
        });
    }

    private void sendMessage(int msgIndex) {
        new Thread(() -> {
            Log.d("[HandlerActivity]", "sendMessage, thread : " + Thread.currentThread().getName());
            Message message = new Message();
            message.what = msgIndex;
            handler.sendMessage(message);
        }).start();
    }

    private class LooperThread extends Thread {
        public Handler mHandler;

//        {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                runOnUiThread(() -> {
//                    Toast.makeText(HandlerActivity.this, "Text: " + msg.what, Toast.LENGTH_SHORT).show();
//                });
//            }
//        };

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mHandler = new Handler();
            Log.d("[HandlerActivity]", "looperThread, thread : " + Thread.currentThread().getName());
            Message message = mHandler.obtainMessage();
            Toast.makeText(HandlerActivity.this, "Text: " + message.what, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}