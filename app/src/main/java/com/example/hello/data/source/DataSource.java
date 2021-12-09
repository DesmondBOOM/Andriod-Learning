package com.example.hello.data.source;

import com.example.hello.data.model.Tweet;
import com.example.hello.data.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface DataSource {
    Flowable<List<Tweet>> fetchTweets();

    Single<User> fetchUser();
}
