package com.example.hello.data.source.network;

import com.example.hello.data.model.Tweet;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface Network {

    Single<List<Tweet>> getTweetsFromNetwork(String url);
}
