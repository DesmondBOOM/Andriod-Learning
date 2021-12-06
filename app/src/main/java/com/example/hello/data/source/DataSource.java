package com.example.hello.data.source;

import com.example.hello.data.model.Tweet;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface DataSource {
    Flowable<List<Tweet>> fetchTweets();
}
