package com.example.hello.data.source.local;

import android.content.Context;

import androidx.room.Room;

import com.example.hello.data.model.Tweet;
import com.example.hello.data.source.local.room.AppDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        List<Tweet> tweets = new ArrayList<>();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("tweet_info.json"), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            String jsonString = stringBuilder.toString();

            Gson gson = new GsonBuilder().create();
            tweets = gson.fromJson(jsonString, new TypeToken<List<Tweet>>() {}.getType());
            return tweets;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tweets;
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
