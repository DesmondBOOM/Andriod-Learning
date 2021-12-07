package com.example.hello.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.hello.R;

public class HandlerActivity extends AppCompatActivity {

    Button button_first;
    Button button_second;

    HandlerThread handlerThread = new HandlerThread("handler_thread");
    ExampleHandler exampleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_layout);

        handlerThread.start();
        exampleHandler = new ExampleHandler(handlerThread.getLooper());
        Log.d("[HandlerActivity]", "handlerThread id = " + handlerThread.getId());
        initUI();
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
            Log.d("[HandlerActivity]", "sendMessage, megIndex : " + msgIndex);
            Log.d("[HandlerActivity]", "sendMessage, thread id : " + Thread.currentThread().getId());
            Message message = exampleHandler.obtainMessage();
            message.what = msgIndex;
            exampleHandler.sendMessage(message);
        }).start();
    }

    private class ExampleHandler extends Handler {
        public ExampleHandler() {
        }

        public ExampleHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.d("[ExampleHandler] handleMessage", "Thread : " + Thread.currentThread().getId());
            Log.d("[ExampleHandler] handleMessage", "message : " + msg.toString());
            ShowMessage showMessage = new ShowMessage(msg);
            runOnUiThread(showMessage);
        }

        private class ShowMessage implements Runnable {
            private final int index;

            public ShowMessage(Message message) {
                Log.d("[ShowMessage] cons", "message : " + message.toString());
                index = message.what;
            }

            @Override
            public void run() {
                Log.d("[ShowMessage] run", "Thread : " + Thread.currentThread().getId());
                Toast.makeText(HandlerActivity.this, String.valueOf(index), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
