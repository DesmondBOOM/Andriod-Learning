package com.example.hello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hello.adapter.TweetsAdapter;
import com.example.hello.data.model.Tweet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("tweet_info.json"),"UTF-8");
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
        TweetsAdapter adapter = new TweetsAdapter(tweetList);
        tweetRecyclerView.setAdapter(adapter);
        tweetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tweetRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!tweetRecyclerView.canScrollVertically(1)) {

                }
            }
        });

    }
}