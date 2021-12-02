package com.example.hello;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.adapter.TweetsAdapter;
import com.example.hello.data.model.Tweet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecyclerViewActivity extends AppCompatActivity {

    List<Tweet> tweetList = new ArrayList<>();
    Gson gson = new GsonBuilder().create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);

        // Lookup the recyclerview in activity layout
        RecyclerView tweetRecyclerView = findViewById(R.id.tweet_recycler);

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("tweet_info.json"), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            String jsonString = stringBuilder.toString();

            tweetList = gson.fromJson(jsonString, new TypeToken<List<Tweet>>() {}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }

        tweetList = tweetList.stream().filter(tweet -> tweet.getError() == null && tweet.getUnknownError() == null).collect(Collectors.toList());
        TweetsAdapter adapter = new TweetsAdapter(tweetList, getApplicationContext());
        tweetRecyclerView.setAdapter(adapter);
        tweetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}