package com.example.hello.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.R;
import com.example.hello.data.model.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int sITEM_TYPE_NORMAL = 0;
    private static final int sITEM_TYPE_HEADER = 1;
    private static final int sITEM_TYPE_FOOTER = 2;
    private static final int sITEM_TYPE_EMPTY = 3;

    List<Tweet> tweetList;

    public TweetsAdapter(List<Tweet> tweetList) {
        this.tweetList = tweetList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return sITEM_TYPE_FOOTER;
        }
        return sITEM_TYPE_NORMAL;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.recycler_custom_row, parent, false);
        View footerBarView = inflater.inflate(R.layout.recycler_footer_bar_layout, parent, false);

        if (viewType == sITEM_TYPE_FOOTER) {
            return new FooterViewHolder(footerBarView);
        } else {
            return new TweetViewHolder(tweetView);
        }
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);

        if (type == sITEM_TYPE_HEADER || type == sITEM_TYPE_FOOTER) {
            return;
        }

        // Get the data model based on position
        Tweet tweet = tweetList.get(position);

        String name = tweet.getSender() == null ? "" : tweet.getSender().getNick();
        // Set item views based on your views and data model
        ((TweetViewHolder) holder).senderName.setText(name);
        String content = tweet.getContent() == null ? "" : tweet.getContent();
        ((TweetViewHolder) holder).contentText.setText(content);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return tweetList.size();
    }


    public static class TweetViewHolder extends RecyclerView.ViewHolder {
        public TextView senderName;
        public TextView contentText;

        public TweetViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.tweet_sender);
            contentText = itemView.findViewById(R.id.tweet_content);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
