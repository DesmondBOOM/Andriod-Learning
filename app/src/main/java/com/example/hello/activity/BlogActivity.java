package com.example.hello.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.R;
import com.example.hello.adapter.MomentsAdapter;
import com.example.hello.utils.Dependency;
import com.example.hello.utils.StatusBarUtils;
import com.example.hello.viewmodel.TweetsViewModel;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

public class BlogActivity extends AppCompatActivity {

    private Dependency dependency;
    private MomentsAdapter momentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_layout);
        dependency = ((HelloApp) getApplication()).getDependency();
        initUI();
        initModel();
    }

    private void initUI() {
        StatusBarUtils.setTransparent(this);

        RefreshLayout refreshLayout = findViewById(R.id.blog_refresh_layout);
        refreshLayout.setRefreshHeader(new MomentsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        RecyclerView momentRecyclerView = findViewById(R.id.blog_recycler_view);
        momentsAdapter = new MomentsAdapter(getApplicationContext());
        momentRecyclerView.setAdapter(momentsAdapter);
        momentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initModel() {
        TweetsViewModel model = new ViewModelProvider(this).get(TweetsViewModel.class);
        model.setDependency(dependency);
        model.tweetListLiveData.observe(this, tweets -> momentsAdapter.setTweets(tweets));
        model.userLiveData.observe(this, user -> momentsAdapter.setUser(user));

        model.setTweetData(throwable -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
        model.setUserData(throwable -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private class MomentsHeader extends ClassicsHeader {


        public MomentsHeader(Context context) {
            super(context);
        }

        public MomentsHeader(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }
}