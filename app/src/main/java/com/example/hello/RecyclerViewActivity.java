package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hello.adapter.TweetsAdapter;
import com.example.hello.model.Tweet;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    List<Tweet> tweetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);

        // Lookup the recyclerview in activity layout
        RecyclerView tweetRecyclerView = findViewById(R.id.tweet_recycler);

        TweetsAdapter adapter = new TweetsAdapter(tweetList);
        tweetRecyclerView.setAdapter(adapter);
        tweetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}