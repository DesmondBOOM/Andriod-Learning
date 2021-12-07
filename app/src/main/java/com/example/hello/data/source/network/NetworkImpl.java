package com.example.hello.data.source.network;

import com.example.hello.data.model.Tweet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkImpl implements Network {
    @Override
    public Single<List<Tweet>> getTweetsFromNetwork(String url) {
        return Single.create((SingleOnSubscribe<String>) emitter -> {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            OkHttpClient client = new OkHttpClient();

            try (Response response = client.newCall(request).execute()) {
                emitter.onSuccess(Objects.requireNonNull(response.body()).string());
            } catch (Throwable throwable) {
                emitter.onError(throwable);
            }
        }).map(jsonString -> {
            Gson gson = new GsonBuilder().create();
            List<Tweet> tweetList = gson.fromJson(jsonString, new TypeToken<List<Tweet>>() {}.getType());
            return tweetList.stream().filter(tweet -> tweet.getError() == null && tweet.getUnknownError() == null).collect(Collectors.toList());
        });
    }
}
