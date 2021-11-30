package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.hello.fragments.FragmentAndroid;
import com.example.hello.fragments.FragmentJava;
import com.example.hello.utils.UIUtils;

public class MyFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_fragment_layout);

        initUI();
    }

    private void initUI() {
        Button button_android = findViewById(R.id.btn_android);
        button_android.setOnClickListener(v -> {
            UIUtils.replaceFragmentWithoutBack(getSupportFragmentManager(), new FragmentAndroid(), R.id.fragment_content, null);
        });

        Button button_java = findViewById(R.id.btn_java);
        button_java.setOnClickListener(v -> {
            UIUtils.replaceFragmentWithoutBack(getSupportFragmentManager(), new FragmentJava(), R.id.fragment_content, null);
        });

    }
}