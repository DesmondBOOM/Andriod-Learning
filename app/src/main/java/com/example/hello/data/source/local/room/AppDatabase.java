package com.example.hello.data.source.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.hello.data.source.local.room.dao.CommentDao;
import com.example.hello.data.source.local.room.dao.ImageDao;
import com.example.hello.data.source.local.room.dao.SenderDao;
import com.example.hello.data.source.local.room.dao.TweetDao;
import com.example.hello.data.source.local.room.model.CommentEntity;
import com.example.hello.data.source.local.room.model.ImageEntity;
import com.example.hello.data.source.local.room.model.SenderEntity;
import com.example.hello.data.source.local.room.model.TweetEntity;

@Database(entities = {TweetEntity.class, ImageEntity.class, SenderEntity.class, CommentEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TweetDao tweetDao();

    public abstract ImageDao imageDao();

    public abstract SenderDao senderDao();

    public abstract CommentDao commentDao();

}
