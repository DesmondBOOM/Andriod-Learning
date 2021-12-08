package com.example.hello.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hello.R;
import com.example.hello.data.model.Image;
import com.example.hello.data.model.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MomentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int sITEM_TYPE_NORMAL = 0;
    private static final int sITEM_TYPE_HEADER = 1;
    private static final int sITEM_TYPE_FOOTER = 2;
    private static final int sITEM_TYPE_EMPTY = 3;

    List<Tweet> mTweetList;
    Context mContext;

    public MomentsAdapter(Context mContext) {
        this.mTweetList = new ArrayList<>();
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTweets(List<Tweet> tweets) {
        this.mTweetList.clear();
        this.mTweetList.addAll(tweets);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sITEM_TYPE_NORMAL;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.blog_custom_row, parent, false);

        return new MomentViewHolder(tweetView);
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);

        if (type == sITEM_TYPE_HEADER || type == sITEM_TYPE_FOOTER) {
            return;
        }

        // Get the data model based on position
        Tweet tweet = mTweetList.get(position);

        // Set item views based on your views and data model
        ((MomentViewHolder) holder).senderName.setText(tweet.getSender() == null ? "" : tweet.getSender().getNick());
        ((MomentViewHolder) holder).contentText.setText(tweet.getContent() == null ? "" : tweet.getContent());
        Glide.with(mContext).load(tweet.getSender().getAvatar()).into(((MomentViewHolder) holder).senderAvatar);

        ImageAdapter imageAdapter = new ImageAdapter(mContext);
        imageAdapter.setImageUrls(tweet.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTweetList.size();
    }


    public static class MomentViewHolder extends RecyclerView.ViewHolder {
        public TextView senderName;
        public TextView contentText;
        public ImageView senderAvatar;
        public RecyclerView imagesGrid;
        public RecyclerView commentsList;

        public MomentViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.blog_tweet_sender);
            contentText = itemView.findViewById(R.id.blog_tweet_content);
            senderAvatar = itemView.findViewById(R.id.blog_tweet_avatar);
            imagesGrid = itemView.findViewById(R.id.blog_tweet_images_recycler_view);
            commentsList = itemView.findViewById(R.id.blog_tweet_comments_recycler_view);
        }
    }
}
