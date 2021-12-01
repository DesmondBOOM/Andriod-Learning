package com.example.hello.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.R;
import com.example.hello.model.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView headImage;
        public TextView senderName;
        public TextView contentText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headImage = itemView.findViewById(R.id.tweet_head_image);
            senderName = itemView.findViewById(R.id.tweet_sender);
            contentText = itemView.findViewById(R.id.tweet_content);
        }
    }

    List<Tweet> tweetList;

    public TweetsAdapter(List<Tweet> tweetList) {
        this.tweetList = tweetList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.recycler_custom_row, parent, false);

        return new ViewHolder(tweetView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Tweet tweet = tweetList.get(position);

        // Set item views based on your views and data model
        holder.headImage.setImageURI(Uri.parse(tweet.getSender().getAvatar()));
        holder.senderName.setText(tweet.getSender().getUserName());
        holder.contentText.setText(tweet.getContent());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return tweetList.size();
    }
}
