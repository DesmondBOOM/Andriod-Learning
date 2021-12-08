package com.example.hello.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hello.R;
import com.example.hello.adapter.MomentsAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

public class BlogActivity extends AppCompatActivity {

    RefreshLayout refreshLayout;
    MomentsAdapter momentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_layout);
        initUI();
    }

    private void initUI() {
        refreshLayout = findViewById(R.id.blogRefreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        momentsAdapter = new MomentsAdapter(getApplicationContext());

    }
}