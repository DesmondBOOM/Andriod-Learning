package com.example.hello.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.R;
import com.example.hello.adapter.TweetsAdapter;
import com.example.hello.utils.Dependency;
import com.example.hello.viewmodel.TweetsViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecyclerViewActivity extends AppCompatActivity {

    private TweetsAdapter tweetsAdapter;
    private Dependency dependency;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        dependency = ((HelloApp) getApplication()).getDependency();
        initUI();
        initModel();
    }

    private void initUI() {
        // Lookup the recyclerview in activity layout
        RecyclerView tweetRecyclerView = findViewById(R.id.tweet_recycler);
        tweetsAdapter = new TweetsAdapter(getApplicationContext());
        tweetRecyclerView.setAdapter(tweetsAdapter);
        tweetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initModel() {
        TweetsViewModel model = new ViewModelProvider(RecyclerViewActivity.this).get(TweetsViewModel.class);
        model.setDependency(dependency);
        model.tweetListLiveData.observe(this, tweets -> tweetsAdapter.setTweets(tweets));

        model.setTweetData(throwable -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void setData() {
        Disposable disposable = dependency.getDataSource()
                .fetchTweets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tweets -> tweetsAdapter.setTweets(tweets),
                        throwable -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show()
                );
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}