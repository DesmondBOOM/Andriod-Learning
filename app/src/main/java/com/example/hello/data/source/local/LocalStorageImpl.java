package com.example.hello.data.source.local;

import android.content.Context;

import androidx.room.Room;

import com.example.hello.data.model.Tweet;
import com.example.hello.data.source.local.room.AppDatabase;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class LocalStorageImpl implements LocalStorage{
    private final Context context;
    private final AppDatabase appDatabase;

    public LocalStorageImpl(Context context) {
        this.context = context;
        this.appDatabase = Room.databaseBuilder(context, AppDatabase.class, "hello-db").build();
    }

    @Override
    public List<Tweet> getTweetsFromAssets() {
        return null;
    }

    @Override
    public Single<Boolean> updateTweets(List<Tweet> tweets) {
        return null;
    }

    @Override
    public Flowable<List<Tweet>> getTweets() {
        return null;
    }
}
