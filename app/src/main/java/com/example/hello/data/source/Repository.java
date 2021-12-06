package com.example.hello.data.source;

import android.content.Context;

import com.example.hello.data.model.Tweet;
import com.example.hello.data.source.local.LocalStorage;
import com.example.hello.data.source.local.LocalStorageImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository implements DataSource{

    private final LocalStorage localStorage;

    public Repository(Context context) {
        this.localStorage = new LocalStorageImpl(context);
    }

    @Override
    public Flowable<List<Tweet>> fetchTweets() {
        localStorage.updateTweets(localStorage.getTweetsFromAssets())
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> {}, throwable -> {});
        return localStorage.getTweets();
    }
}
