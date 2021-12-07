package com.example.hello.data.source;

import android.content.Context;
import android.widget.Toast;

import com.example.hello.data.model.Tweet;
import com.example.hello.data.source.local.LocalStorage;
import com.example.hello.data.source.local.LocalStorageImpl;
import com.example.hello.data.source.remote.Network;
import com.example.hello.data.source.remote.NetworkImpl;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository implements DataSource {

    public static final String TWEETS_SOURCE = "https://thoughtworks-mobile-2018.herokuapp.com/user/jsmith/tweets";
    private final LocalStorage localStorage;
    private final Network network = new NetworkImpl();
    private final Context context;

    public Repository(Context context) {
        this.context = context;
        this.localStorage = new LocalStorageImpl(context);
    }

    @Override
    public Flowable<List<Tweet>> fetchTweets() {
//        List<Tweet> tweets = localStorage.getTweetsFromAssets();
        network.getTweetsFromNetwork(TWEETS_SOURCE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tweetList -> localStorage.updateTweets(tweetList).subscribeOn(Schedulers.io()),
                        throwable -> Toast.makeText(context, throwable.toString(), Toast.LENGTH_SHORT).show()
                );
        return localStorage.getTweets();
    }
}
