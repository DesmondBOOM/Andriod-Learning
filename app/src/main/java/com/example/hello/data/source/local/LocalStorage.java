package com.example.hello.data.source.local;

import com.example.hello.data.model.Tweet;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface LocalStorage {

    List<Tweet> getTweetsFromAssets();

    Single<Boolean> updateTweets(List<Tweet> tweets);

    Flowable<List<Tweet>> getTweets();
}
