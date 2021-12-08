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

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<String> mImageUrls;
    Context mContext;

    public ImageAdapter(Context mContext) {
        this.mImageUrls = new ArrayList<>();
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setImageUrls(List<String> urls) {
        this.mImageUrls.clear();
        this.mImageUrls.addAll(urls);
        notifyDataSetChanged();
    }


    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.blog_custom_row, parent, false);

        return new MomentImageViewHolder(tweetView);
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // Get the data model based on position
        String imageUrl = mImageUrls.get(position);

        // Set item views based on your views and data model
        Glide.with(mContext).load(imageUrl).into(((MomentImageViewHolder) holder).image);


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }


    public static class MomentImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public MomentImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.blog_tweet_image);
        }
    }
}
