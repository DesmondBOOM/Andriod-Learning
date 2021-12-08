package com.example.hello.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hello.R;
import com.example.hello.data.model.Comment;
import com.example.hello.data.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Comment> mCommentList;
    Context mContext;

    public CommentAdapter(Context mContext) {
        this.mCommentList = new ArrayList<>();
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setComment(List<Comment> comments) {
        this.mCommentList.clear();
        this.mCommentList.addAll(comments);
        notifyDataSetChanged();
    }


    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.blog_comment_row, parent, false);

        return new MomentCommentViewHolder(tweetView);
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // Get the data model based on position
        Comment comment = mCommentList.get(position);
        String senderName = comment.getSender().getNick();
        String commentContent = comment.getContent();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(senderName).append(" : ");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(mContext.getColor(R.color.blue));
        builder.setSpan(colorSpan, 0, senderName.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(commentContent);


        // Set item views based on your views and data model
        ((MomentCommentViewHolder) holder).comment.setText(builder);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public static class MomentCommentViewHolder extends RecyclerView.ViewHolder {
        public TextView comment;

        public MomentCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.blog_tweet_comment);
        }
    }
}
