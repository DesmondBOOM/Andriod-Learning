package com.example.hello.data.source.remote;

import com.example.hello.data.model.Tweet;
import com.example.hello.data.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface Network {

    Single<List<Tweet>> getTweetsFromRemote(String url);

    Single<User> getUserFromRemote(String url);
}
