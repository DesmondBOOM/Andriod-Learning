package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.hello.utils.SharedPreferencesUtils;

public class SpActivity extends AppCompatActivity {

    public static final String IS_KNOWN = "isKnown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isKnown = SharedPreferencesUtils.readSpBoolean(this, IS_KNOWN, false);
        if (isKnown) {
            setContentView(R.layout.sp_layout_2);
        } else {
            setContentView(R.layout.sp_layout_1);
            initButton();
        }
    }

    private void initButton() {
        Button btnKnow = findViewById(R.id.btn_know);
        btnKnow.setOnClickListener(v -> {
            SharedPreferencesUtils.writeSpBoolean(this, IS_KNOWN, true);
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}