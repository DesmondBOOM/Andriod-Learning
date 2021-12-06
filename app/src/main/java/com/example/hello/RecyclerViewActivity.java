package com.example.hello;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.adapter.TweetsAdapter;
import com.example.hello.data.model.Tweet;
import com.example.hello.utils.Dependency;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecyclerViewActivity extends AppCompatActivity {

    private final Gson gson = new GsonBuilder().create();
    private TweetsAdapter tweetsAdapter;
    private Dependency dependency;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        dependency = ((HelloApp) getApplication()).getDependency();
        initUI();
        setData();

    }

    private void initUI() {
        // Lookup the recyclerview in activity layout
        RecyclerView tweetRecyclerView = findViewById(R.id.tweet_recycler);
        tweetsAdapter = new TweetsAdapter(getApplicationContext());
        tweetRecyclerView.setAdapter(tweetsAdapter);
        tweetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setData() {
        Disposable disposable = dependency.getDataSource()
                .fetchTweets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tweets -> tweetsAdapter.setTweets(tweets),
                        throwable -> Toast.makeText(RecyclerViewActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show()
                );
        compositeDisposable.add(disposable);

//        List<Tweet> tweetList = getTweetsFromAssets();
//        tweetList = tweetList.stream().filter(tweet -> tweet.getError() == null && tweet.getUnknownError() == null).collect(Collectors.toList());
//        tweetsAdapter.setTweets(tweetList);
    }

    private List<Tweet> getTweetsFromAssets() {
        List<Tweet> tweets = new ArrayList<>();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("tweet_info.json"), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            String jsonString = stringBuilder.toString();

            tweets = gson.fromJson(jsonString, new TypeToken<List<Tweet>>() {}.getType());
            return tweets;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tweets;
    }
}