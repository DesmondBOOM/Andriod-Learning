package com.example.hello.data.source.local.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hello.data.source.local.room.model.SenderEntity;
import com.example.hello.data.source.local.room.model.TweetEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface SenderDao {
    @Query("SELECT * FROM sender")
    Flowable<List<SenderEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(SenderEntity senderEntity);
}
